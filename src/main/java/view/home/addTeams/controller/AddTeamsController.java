package view.home.addTeams.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import view.home.controller.HomeController;

import java.io.IOException;

public class AddTeamsController {
    @FXML private VBox teamsList;

    @FXML
    private void addAnotherTeamInputs() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/home/addTeams/addTeamInputs.fxml"));
        teamsList.getChildren().add(fxmlLoader.load());
    }

    @FXML
    private void addAllTeams() {
        for(Node b : teamsList.getChildren()){
            ((Button) b.lookup("#addTeam")).fire();
        }
    }
}
