package view.home.controller;

import database.Model.Match;
import database.Model.Team;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import view.home.Main;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FinalsTreeController implements Initializable {
    @FXML
    AnchorPane treePane;
    private static AnchorPane treePane_;

    private static List<Match> matches;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        treePane_ = treePane;
        setFinalsTreeLabelsAndButtons();
    }

    public static void setFinalsTreeLabelsAndButtons() {
        getMatchesFinalStage();
        initButtons();
        setLabels();
    }

    private static void getMatchesFinalStage() {
        matches = Main.matchService.getMatches()
                .stream()
                .filter(match -> !match.getPhase().getType().equals("group"))
                .sorted(Comparator.comparing(m -> m.getPhase().getName()))
                .collect(Collectors.toList());
    }

    private static void setLabels() {
        List<Label> labels = (List<Label>) (List<?>) getTeamTreeLabels();

        for (int i = 0; i < matches.size(); i++) {
            Team team1 = matches.get(i).getTeam1();
            Team team2 = matches.get(i).getTeam2();

            labels.get(i * 2).setText(team1 != null ? team1.getName() : "");
            labels.get(i * 2 + 1).setText(team2 != null ? team2.getName() : "");
        }
    }

    private static List<Node> getTeamTreeLabels() {
        return treePane_.getChildren().stream().filter(node -> node instanceof Label).collect(Collectors.toList());
    }

    private static void initButtons() {
        List<Button> treeButtons = (List<Button>) (List<?>) getTeamTreeButtons();

        for (int i = 0; i < matches.size(); i++) {
            initButton(treeButtons.get(i * 2), matches.get(i).getTeam1());
            initButton(treeButtons.get(i * 2 + 1), matches.get(i).getTeam2());
        }
    }

    private static List<Node> getTeamTreeButtons() {
        return treePane_.getChildren().stream().filter(node -> node instanceof Button).collect(Collectors.toList());
    }

    private static void initButton(Button button, Team team) {
        if (team != null) {
            setButtonOnClickAction(button, team);
            setButtonImage(button, team);
        }
    }

    private static void setButtonOnClickAction(Button button, Team team) {
        button.setOnMouseClicked(event -> new TeamPreviewController().showPreview(team));
    }

    private static void setButtonImage(Button button, Team team) {
        ImageView teamImage = new ImageView(new Image(new ByteArrayInputStream(team.getImage())));
        teamImage.fitHeightProperty().bind(button.heightProperty());
        teamImage.fitWidthProperty().bind(button.widthProperty());
        button.setGraphic(teamImage);
    }
}
