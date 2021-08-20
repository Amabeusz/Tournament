package view.home.addTeams.controller;

import database.Model.Team;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.ImagePicker;
import view.home.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import view.home.controller.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class AddTeamController {
    @FXML
    HBox teamInputs;

    @FXML
    ImageView teamImagePreview;
    @FXML
    TextField teamNameInput;
    @FXML
    TextArea teamDescriptionInput;
    @FXML
    Button deleteInputButton;

    @FXML
    public void addTeam() {
        if (checkIfTheresLessThan32Teams()) {
            Main.teamService.addTeam(createTeam());
            resetInputs();


            if (Main.teamService.getTeamsNumber() == 32) {
                setGroups();
                setMatches();
                MatchResultController.getNextMatch();

                tournamentReadyAlert();
            }

            TeamsTabController.setTeamsTableItems();
        } else {
            teamsNumberAlert();
        }
    }

    private void resetInputs() {
        teamImagePreview.setImage(new Image(String.valueOf(getClass().getResource("/images/default_avatar.png"))));
        teamNameInput.setText("");
        teamDescriptionInput.setText("");
    }

    private void setGroups() {
        GroupsTabController.assignTeamsToGroups();
        GroupsTabController.setGroupTablesItems();
    }

    private void setMatches() {
        MatchesGenerator.generateMatches();
        MatchesTabController.setTableItems();
    }

    private void tournamentReadyAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tournament ready");
        alert.setHeaderText("");
        alert.setContentText("teams added random to groups \ngroup matches generated");
        alert.showAndWait();
    }

    private void teamsNumberAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Can't add more teams");
        alert.setContentText("There is 32 teams already");
        alert.showAndWait();
    }

    public static boolean checkIfTheresLessThan32Teams() {
        return (Main.teamService.getTeamsNumber() < 32);
    }

    private Team createTeam() {
        String name = teamNameInput.getText();
        String description = teamDescriptionInput.getText();
        Image teamImage = teamImagePreview.getImage();

        return new Team(name, description, imageToByteArray(teamImage));
    }

    @FXML
    private void selectImage() {
        File image = ImagePicker.pickImage();

        if (image != null) {
            teamImagePreview.setImage(new Image(image.toURI().toString()));
        }
    }

    private static byte[] imageToByteArray(Image image) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(bImage, "png", bos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    @FXML
    private void deleteInput() {
        ((VBox) teamInputs.getParent()).getChildren().remove(teamInputs);
    }
}
