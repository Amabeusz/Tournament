package view.home.controller;

import database.Model.Match;
import database.Model.Phase;
import database.Model.Team;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import view.home.Main;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TeamsTabController implements Initializable {
    @FXML
    TableView teamsTable;
    static TableView teamsTable_;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teamsTable_ = teamsTable;
        initTable();
    }

    private void initTable() {
        addColumns();
        setTeamsTableItems();
        setOnClickTeamPreview(teamsTable);
    }

    private void addColumns() {
        teamsTable_.getColumns().addAll(
                createNameColumn(),
                createGroupColumn(),
                createGoalsScoredColumn(),
                createGoalsLostColumn());
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

    private TableColumn createGoalsScoredColumn(){
        TableColumn<Team, String> goalsScoredColumn = new TableColumn<>("Goals scored");
        setGoalsScoredColumnContent(goalsScoredColumn);
        goalsScoredColumn.setPrefWidth(100);

        return goalsScoredColumn;
    }

    private void setGoalsScoredColumnContent(TableColumn goalsScoredColumn){
        goalsScoredColumn.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
    }

    private TableColumn createGoalsLostColumn(){
        TableColumn<Team, String> goalsLostColumn = new TableColumn<>("Goals lost");
        setGoalsScoredColumnContent(goalsLostColumn);
        goalsLostColumn.setPrefWidth(100);

        return goalsLostColumn;
    }


    private void setGoalsLostColumnContent(TableColumn goalsLostColumn){
        goalsLostColumn.setCellValueFactory(new PropertyValueFactory<>("goalsLost"));
    }

    private TableColumn createNameColumn() {
        TableColumn<Team, String> nameColumn = new TableColumn("Name");
        setNameColumnContent(nameColumn);
        nameColumn.setPrefWidth(260);

        return nameColumn;
    }

    private void setNameColumnContent(TableColumn nameColumn){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private TableColumn<Team, String> createGroupColumn() {
        TableColumn<Team, String> groupColumn = new TableColumn("Group");
        setGroupColumnContent(groupColumn);
        groupColumn.setPrefWidth(300);

        return groupColumn;
    }

    private void setGroupColumnContent(TableColumn<Team, String> team2Column) {
        team2Column.setCellValueFactory(cellDataFeatures -> {
            Team team = cellDataFeatures.getValue();
            String group = "Not set yet";

            if (team.getPhase() != null)
                group = team.getPhase().getName();

            return new ReadOnlyStringWrapper(group);
        });
    }

    public static void setTeamsTableItems() {
        ObservableList<Team> observableArrayList = FXCollections.observableArrayList(
                Main.teamService.getTeams()
        );

        teamsTable_.setItems(observableArrayList);
    }
}
