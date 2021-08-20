package view.home.controller;

import database.Model.Match;
import database.Model.Phase;
import database.Model.Team;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import view.home.Main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class MatchResultController implements Initializable {
    @FXML
    Button submitMatchButton;
    @FXML
    Label teamsPlayingLabel;
    @FXML
    TextField goalsTeam1input;
    @FXML
    TextField goalsTeam2input;
    @FXML
    HBox inputsHBox;

    static Button submitMatchButton_;
    static Label teamsPlayingLabel_;
    static HBox inputsHBox_;
    static Match match;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputsHBox_ = inputsHBox;
        teamsPlayingLabel_ = teamsPlayingLabel;

        initSubmitButton();
        setGoalsInputsFormatter();
        getNextMatch();
    }

    private void initSubmitButton() {
        submitMatchButton_ = submitMatchButton;
        submitMatchButton_.setDefaultButton(true);
        submitMatchButton_.setOnAction(event -> {
            try {
                submitMatch();
            } catch (NumberFormatException e) {
                emptyInputAlert();
            }
        });
    }

    private void emptyInputAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Wrong input");
        alert.setContentText("Goals input can't be empty");

        alert.showAndWait();
    }

    private void submitMatch() {
        Phase phase = match.getPhase();

        updateMatchWithResult();
        updateTablesAndFinalStageTree();

        switch (phase.getType()) {
            case "group":
                updateTeamsResults(match.getTeam1(), match.getTeam2(), match.getGoalsScoredTeam1(), match.getGoalsScoredTeam2());
                checkIfGroupEnded(phase.getName());
                break;
            case "octofinal":
                updateQuarterfinalMatch(phase.getName());
                break;
            case "quarterfinal":
                updateSemifinalMatch(phase.getName());
                break;
            case "semifinal":
                updateFinalMatch(phase.getName());
                break;
            case "final":
                Team tournamentWinner = getWinnerTeam();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(tournamentWinner.getName() + " won the tournament");
                alert.showAndWait();
                break;
            default:
        }

        getNextMatch();
    }

    public static void updateTablesAndFinalStageTree() {
        FinalsTreeController.setFinalsTreeLabelsAndButtons();
        MatchesTabController.setTableItems();
        GroupsTabController.setGroupTablesItems();
        TeamsTabController.setTeamsTableItems();
    }

    private void updateTeamsResults(Team team1, Team team2, int goalsScoredTeam1, int goalsScoredTeam2) {
        if (goalsScoredTeam1 > goalsScoredTeam2) {
            updateResultsFirstTeamWon(team1, team2, goalsScoredTeam1, goalsScoredTeam2);
        } else if (goalsScoredTeam1 < goalsScoredTeam2) {
            updateResultsFirstTeamWon(team2, team1, goalsScoredTeam2, goalsScoredTeam1);
        } else {
            updateResultsDraw(team1, team2, goalsScoredTeam1, goalsScoredTeam2);
        }
    }

    private void checkIfGroupEnded(String groupName) {
        List<Match> matches = Main.matchService.getMatches().stream()
                .filter(m -> m.getPhase().getName().equals(groupName)).collect(Collectors.toList());

        if (matches.stream().filter(Match::isPlayed).count() == 6) {
            updateOctofinalMatch(groupName);
        }
    }

    private void updateOctofinalMatch(Team team1, Team team2, String octofinalName1, String octofinalName2) {
        Match octofinal1 = getMatchByPhaseName(octofinalName1);
        Match octofinal2 = getMatchByPhaseName(octofinalName2);

        octofinal1.setTeam1(team1);
        octofinal2.setTeam2(team2);

        Main.matchService.updateMatch(octofinal1);
        Main.matchService.updateMatch(octofinal2);
    }

    private static Match getMatchByPhaseName(String name) {
        return Main.matchService.getMatches()
                .stream().filter(match -> match.getPhase().getName().equals(name))
                .findAny().orElse(null);
    }

    private void updateOctofinalMatch(String groupName) {
        Team[] promotionTeams;

        switch (groupName) {
            case "A":
                promotionTeams = getTeamsForPromotion(GroupsTabController.getAGroupTeams());
                updateOctofinalMatch(promotionTeams[0], promotionTeams[1], "o1", "o8");
                break;
            case "B":
                promotionTeams = getTeamsForPromotion(GroupsTabController.getBGroupTeams());
                updateOctofinalMatch(promotionTeams[0], promotionTeams[1], "o2", "o7");
                break;
            case "C":
                promotionTeams = getTeamsForPromotion(GroupsTabController.getCGroupTeams());
                updateOctofinalMatch(promotionTeams[0], promotionTeams[1], "o3", "o6");
                break;
            case "D":
                promotionTeams = getTeamsForPromotion(GroupsTabController.getDGroupTeams());
                updateOctofinalMatch(promotionTeams[0], promotionTeams[1], "o4", "o5");
                break;
            case "E":
                promotionTeams = getTeamsForPromotion(GroupsTabController.getEGroupTeams());
                updateOctofinalMatch(promotionTeams[0], promotionTeams[1], "o5", "o4");
                break;
            case "F":
                promotionTeams = getTeamsForPromotion(GroupsTabController.getFGroupTeams());
                updateOctofinalMatch(promotionTeams[0], promotionTeams[1], "o6", "o3");
                break;
            case "G":
                promotionTeams = getTeamsForPromotion(GroupsTabController.getGGroupTeams());
                updateOctofinalMatch(promotionTeams[0], promotionTeams[1], "o7", "o2");
                break;
            case "H":
                promotionTeams = getTeamsForPromotion(GroupsTabController.getHGroupTeams());
                updateOctofinalMatch(promotionTeams[0], promotionTeams[1], "o8", "o1");
            default:
        }
    }

    private void updateQuarterfinalMatch(String octofinalName) {
        switch (octofinalName) {
            case "o1":
                updateFinalStageMatch(getWinnerTeam(), "q1", 1);
                break;
            case "o8":
                updateFinalStageMatch(getWinnerTeam(), "q1", 2);
                break;
            case "o2":
                updateFinalStageMatch(getWinnerTeam(), "q2", 1);
                break;
            case "o7":
                updateFinalStageMatch(getWinnerTeam(), "q2", 2);
                break;
            case "o3":
                updateFinalStageMatch(getWinnerTeam(), "q3", 1);
                break;
            case "o6":
                updateFinalStageMatch(getWinnerTeam(), "q3", 2);
                break;
            case "o4":
                updateFinalStageMatch(getWinnerTeam(), "q4", 1);
                break;
            case "o5":
                updateFinalStageMatch(getWinnerTeam(), "q4", 2);
                break;
        }
    }

    private void updateSemifinalMatch(String quarterfinalName) {
        switch (quarterfinalName) {
            case "q1":
                updateFinalStageMatch(getWinnerTeam(), "s1", 1);
                break;
            case "q4":
                updateFinalStageMatch(getWinnerTeam(), "s1", 2);
                break;
            case "q2":
                updateFinalStageMatch(getWinnerTeam(), "s2", 1);
                break;
            case "q3":
                updateFinalStageMatch(getWinnerTeam(), "s2", 2);
                break;
        }
    }

    private void updateFinalMatch(String semifinalName) {
        if (semifinalName.equals("s1")) {
            updateFinalStageMatch(getWinnerTeam(), "f", 1);
        } else {
            updateFinalStageMatch(getWinnerTeam(), "f", 2);
        }
    }

    private void updateFinalStageMatch(Team team, String finalStageName, int teamPosition) {
        Match finalStageMatch = getMatchByPhaseName(finalStageName);

        if (teamPosition == 1) {
            finalStageMatch.setTeam1(team);
        } else {
            finalStageMatch.setTeam2(team);
        }

        Main.matchService.updateMatch(finalStageMatch);
        FinalsTreeController.setFinalsTreeLabelsAndButtons();
    }

    private static Team getWinnerTeam() {
        return match.getGoalsScoredTeam1() > match.getGoalsScoredTeam2() ? match.getTeam1() : match.getTeam2();
    }

    private Team[] getTeamsForPromotion(List<Team> groupTeams) {
        return new Team[]{groupTeams.get(0), groupTeams.get(1)};
    }

    public static void setTeamsPlayingLabel() {
        if (match != null) {
            inputsHBox_.setVisible(true);

            String team1 = match.getTeam1().getName();
            String team2 = match.getTeam2().getName();

            teamsPlayingLabel_.setText(team1 + " vs " + team2);
        } else {
            inputsHBox_.setVisible(false);
        }
    }

    private static void endTournament() {
        match = getMatchByPhaseName("f");

        teamsPlayingLabel_.setText("tournament has ended, " + getWinnerTeam().getName() + " won the tournament!");
        teamsPlayingLabel_.setTranslateX(130);
        inputsHBox_.setVisible(false);
    }

    private TextFormatter<TextFormatter.Change> goalsInputFormatter() {
        UnaryOperator<TextFormatter.Change> lengthFilter = change -> (change.getControlNewText().length() <= 2
                && change.getText().matches("[0-9\b]*")) ? change : null;
        return new TextFormatter<>(lengthFilter);
    }

    private void setGoalsInputsFormatter() {
        goalsTeam1input.setTextFormatter(goalsInputFormatter());
        goalsTeam2input.setTextFormatter(goalsInputFormatter());
    }

    private int readGoalInput(TextField input) {
        return Integer.parseInt(input.getText());
    }

    private void updateMatchWithResult() {
        match.setGoalsScoredTeam1(readGoalInput(goalsTeam1input));
        match.setGoalsScoredTeam2(readGoalInput(goalsTeam2input));
        match.setPlayed(true);

        Main.matchService.updateMatch(match);
    }

    private void updateResultsFirstTeamWon(Team winnerTeam, Team lostTeam, int goalsScoredWinnerTeam, int goalsScoredLostTeam) {
        updateWinnerTeam(winnerTeam, goalsScoredWinnerTeam, goalsScoredLostTeam);
        updateLostTeam(lostTeam, goalsScoredLostTeam, goalsScoredWinnerTeam);
    }

    private void updateResultsDraw(Team team1, Team team2, int goalsScoredTeam1, int goalsScoredTeam2) {
        updateDrawTeam(team1, goalsScoredTeam1, goalsScoredTeam2);
        updateDrawTeam(team2, goalsScoredTeam2, goalsScoredTeam1);
    }

    private void updateWinnerTeam(Team team, int goalsScored, int goalsLost) {
        setPointsWinnerTeam(team);
        setGoals(team, goalsScored, goalsLost);

        Main.teamService.updateTeam(team);
    }

    private void setPointsWinnerTeam(Team team) {
        int currentPointsTeam = team.getPoints();
        team.setPoints(currentPointsTeam + 3);
    }

    private void updateDrawTeam(Team team, int goalsScored, int goalsLost) {
        setPointsDrawTeam(team);
        setGoals(team, goalsScored, goalsLost);

        Main.teamService.updateTeam(team);
    }

    private void setPointsDrawTeam(Team team) {
        int currentPointsTeam = team.getPoints();
        team.setPoints(currentPointsTeam + 2);
    }

    private void updateLostTeam(Team team, int goalsScored, int goalsLost) {
        setGoals(team, goalsScored, goalsLost);

        Main.teamService.updateTeam(team);
    }

    private void setGoals(Team team, int goalsScored, int goalsLost) {
        setGoalsScored(team, goalsScored);
        setGoalsLost(team, goalsLost);
    }

    private void setGoalsScored(Team team, int goalsScored) {
        int currentGoalsScoredTeam = team.getGoalsScored();
        team.setGoalsScored(currentGoalsScoredTeam + goalsScored);
    }

    private void setGoalsLost(Team team, int goalsLost) {
        int currentGoalsLostTeam = team.getGoalsLost();
        team.setGoalsScored(currentGoalsLostTeam + goalsLost);
    }

    public static void getNextMatch() {
        match = Main.matchService.getMatchNotPlayed();

        if (Main.matchService.getMatches().size() > 0 && match == null) {
            endTournament();
        } else {
            setTeamsPlayingLabel();
        }
    }
}
