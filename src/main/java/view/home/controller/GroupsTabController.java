package view.home.controller;

import database.Model.Phase;
import database.Model.Team;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import view.home.Main;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class GroupsTabController implements Initializable {
    @FXML
    TableView aGroupTable;
    @FXML
    TableView bGroupTable;
    @FXML
    TableView cGroupTable;
    @FXML
    TableView dGroupTable;
    @FXML
    TableView eGroupTable;
    @FXML
    TableView fGroupTable;
    @FXML
    TableView gGroupTable;
    @FXML
    TableView hGroupTable;

    static TableView aGroupTable_;
    static TableView bGroupTable_;
    static TableView cGroupTable_;
    static TableView dGroupTable_;
    static TableView eGroupTable_;
    static TableView fGroupTable_;
    static TableView gGroupTable_;
    static TableView hGroupTable_;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aGroupTable_ = aGroupTable;
        bGroupTable_ = bGroupTable;
        cGroupTable_ = cGroupTable;
        dGroupTable_ = dGroupTable;
        eGroupTable_ = eGroupTable;
        fGroupTable_ = fGroupTable;
        gGroupTable_ = gGroupTable;
        hGroupTable_ = hGroupTable;

        setGroupTablesItems();
        initTables();
    }

    private void initTables() {
        initGroupTable(aGroupTable);
        initGroupTable(bGroupTable);
        initGroupTable(cGroupTable);
        initGroupTable(dGroupTable);
        initGroupTable(eGroupTable);
        initGroupTable(fGroupTable);
        initGroupTable(gGroupTable);
        initGroupTable(hGroupTable);
    }

    private void initGroupTable(TableView table) {
        addColumns(table);
        setOnClickTeamPreview(table);
    }

    private void setOnClickTeamPreview(TableView table) {
        table.setRowFactory(tableView -> {
            TableRow<Team> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    new TeamPreviewController().showPreview(row.getItem());
                }
            });
            return row;
        });
    }

    private void addColumns(TableView table) {
        TableColumn placeColumn = createPlaceColumn();
        TableColumn nameColumn = createNameColumn();
        TableColumn pointsColumn = createPointsColumn();

        table.getColumns().addAll(placeColumn, nameColumn, pointsColumn);
    }

    private TableColumn createPlaceColumn() {
        TableColumn<String, Integer> placeColumn = new TableColumn("Place");
        setPlaceColumnContent(placeColumn);
        placeColumn.setPrefWidth(43);

        return placeColumn;
    }

    private void setPlaceColumnContent(TableColumn placeColumn) {
        placeColumn.setCellFactory(col -> {
            TableCell<String, Integer> indexCell = new TableCell<>();
            ReadOnlyObjectProperty<TableRow<String>> rowProperty = indexCell.tableRowProperty();
            ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
                TableRow<String> row = rowProperty.get();
                if (row != null) {
                    int rowIndex = row.getIndex();
                    if (rowIndex < row.getTableView().getItems().size()) {
                        return Integer.toString(rowIndex + 1);
                    }
                }
                return null;
            }, rowProperty);
            indexCell.textProperty().bind(rowBinding);
            return indexCell;
        });
    }

    private TableColumn createNameColumn() {
        TableColumn<Team, String> nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(260);

        return nameColumn;
    }

    private TableColumn createPointsColumn() {
        TableColumn<Team, String> pointsColumn = new TableColumn("Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        pointsColumn.setPrefWidth(65);

        return pointsColumn;
    }

    public static void setTableItems(TableView table, List<Team> items) {
        items.sort(Comparator.comparing(Team::getPoints).thenComparing(Team::getGoalsScored).reversed().thenComparing(Team::getGoalsLost));
        ObservableList<Team> observableArrayList = FXCollections.observableArrayList(items);
        table.setItems(observableArrayList);
    }

    public static void setGroupTablesItems() {
        List<Team> teams = Main.teamService.getTeams();

        setTableItems(aGroupTable_, filterGroupName(teams, "A"));
        setTableItems(bGroupTable_, filterGroupName(teams, "B"));
        setTableItems(cGroupTable_, filterGroupName(teams, "C"));
        setTableItems(dGroupTable_, filterGroupName(teams, "D"));
        setTableItems(eGroupTable_, filterGroupName(teams, "E"));
        setTableItems(fGroupTable_, filterGroupName(teams, "F"));
        setTableItems(gGroupTable_, filterGroupName(teams, "G"));
        setTableItems(hGroupTable_, filterGroupName(teams, "H"));
    }

    private static List<Team> filterGroupName(List<Team> teams, String groupName) {
        return teams.stream().filter(team -> {
            if(team.getPhase() != null)
                return team.getPhase().getName().equals(groupName);
            return false;
        }).collect(Collectors.toList());
    }

    public static void assignTeamsToGroups() {
        List<Team> teams = Main.teamService.getTeams();
        List<Phase> groups = Main.phaseService.getPhases().stream().filter(phase -> phase.getType().equals("group")).collect(Collectors.toList());

        Collections.shuffle(teams);

        int j = 0;
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).setPhase(groups.get(j));
            if ((i + 1) % 4 == 0) ++j;
        }

        teams.forEach(team -> Main.teamService.updateTeam(team));
    }

    public static List<Team> getAGroupTeams() {
        return aGroupTable_.getItems();
    }

    public static List<Team> getBGroupTeams() {
        return bGroupTable_.getItems();
    }

    public static List<Team> getCGroupTeams() {
        return cGroupTable_.getItems();
    }

    public static List<Team> getDGroupTeams() {
        return dGroupTable_.getItems();
    }

    public static List<Team> getEGroupTeams() {
        return eGroupTable_.getItems();
    }

    public static List<Team> getFGroupTeams() {
        return fGroupTable_.getItems();
    }

    public static List<Team> getGGroupTeams() {
        return gGroupTable_.getItems();
    }

    public static List<Team> getHGroupTeams() {
        return hGroupTable_.getItems();
    }
}
