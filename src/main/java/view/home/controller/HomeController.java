package view.home.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.home.addTeams.controller.AddTeamController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{
    @FXML Button addTeamButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTeamButton.setOnMouseClicked(mouseEvent -> openAddTeamWindow());
    }

    private void openAddTeamWindow() {
        if (AddTeamController.checkIfTheresLessThan32Teams()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("/view/home/addTeams/addTeams.fxml"));
                Pane root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("Add teams");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No more team space");
            alert.setContentText("There's 32 teams already");
            alert.showAndWait();
        }
    }
}
