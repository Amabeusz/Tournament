package view.home.controller;

import database.Model.Match;
import database.Model.Phase;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import view.home.Main;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MatchesTabController implements Initializable {
    @FXML
    TableView matchesTable;
    static TableView matchesTable_;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matchesTable_ = matchesTable;
        initTable();
        setTableItems();
    }

    private void initTable() {
        addColumns();
        setTableItems();
    }

    private void addColumns() {
        matchesTable.getColumns().addAll(
                createTeam1Column(),
                createTeam2Column(),
                createResultColumn(),
                createPhaseColumn());
    }

    private TableColumn<Match, String> createResultColumn() {
        TableColumn<Match, String> resultColumn = new TableColumn("Result");
        setResultColumnContent(resultColumn);
        resultColumn.setPrefWidth(200);

        return resultColumn;
    }

    private void setResultColumnContent(TableColumn<Match, String> resultColumn) {
        resultColumn.setCellValueFactory(cellDataFeatures -> {
            Match match = cellDataFeatures.getValue();
            String result = "Not played yet";

            if (match.isPlayed())
                result = match.getGoalsScoredTeam1() + " : " + match.getGoalsScoredTeam2();

            return new ReadOnlyStringWrapper(result);
        });
    }

    private TableColumn<Match, String> createTeam1Column() {
        TableColumn<Match, String> team1Column = new TableColumn("Team 1");
        setTeam1ColumnContent(team1Column);
        team1Column.setPrefWidth(300);

        return team1Column;
    }

    private void setTeam1ColumnContent(TableColumn<Match, String> team1Column) {
        team1Column.setCellValueFactory(cellDataFeatures -> {
            Match match = cellDataFeatures.getValue();
            String team1 = "Not set yet";

            if (match.getTeam1() != null)
                team1 = match.getTeam1().getName();

            return new ReadOnlyStringWrapper(team1);
        });
    }

    private TableColumn<Match, String> createTeam2Column() {
        TableColumn<Match, String> team2Column = new TableColumn("Team 2");
        setTeam2ColumnContent(team2Column);
        team2Column.setPrefWidth(300);

        return team2Column;
    }

    private void setTeam2ColumnContent(TableColumn<Match, String> team2Column) {
        team2Column.setCellValueFactory(cellDataFeatures -> {
            Match match = cellDataFeatures.getValue();
            String team2 = "Not set yet";

            if (match.getTeam2() != null)
                team2 = match.getTeam2().getName();

            return new ReadOnlyStringWrapper(team2);
        });
    }

    private TableColumn<Match, String> createPhaseColumn() {
        TableColumn<Match, String> phaseColumn = new TableColumn("Phase");
        setPhaseColumnContent(phaseColumn);
        phaseColumn.setPrefWidth(200);

        return phaseColumn;
    }

    private void setPhaseColumnContent(TableColumn<Match, String> phaseColumn) {
        phaseColumn.setCellValueFactory(cellDataFeatures -> {
            Match match = cellDataFeatures.getValue();
            Phase phase = match.getPhase();
            String phaseColumnContent = phase.getType() + " " + phase.getName();

            return new ReadOnlyStringWrapper(phaseColumnContent);
        });
    }

    public static void setTableItems() {
        ObservableList<Match> observableArrayList = FXCollections.observableArrayList(
                Main.matchService.getMatches().stream()
                        .sorted(Comparator.comparing(Match::isPlayed)
                                .thenComparing(m -> m.getPhase().getName()))
                        .collect(Collectors.toList())
        );

        matchesTable_.setItems(observableArrayList);
    }
}
