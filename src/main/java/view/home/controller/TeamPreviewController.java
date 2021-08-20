package view.home.controller;

import database.Model.Team;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TeamPreviewController {
    @FXML
    ImageView teamImage;
    @FXML
    Label teamName;
    @FXML
    TextFlow teamDescription;

    public void showPreview(Team team) {
        createDialog();
        setDialogContent(team);
    }

    private void createDialog() {
        Dialog dialog = new Dialog();

        try {
            dialog.setDialogPane((DialogPane) loadFXML());
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.show();
        setCloseRequest(dialog);
    }

    private void setDialogContent(Team team) {
        teamName.textProperty().setValue(team.getName());

        setDialogDescription(team);
        setDialogImage(team);
    }

    private void setDialogDescription(Team team){
        Text text = new Text(team.getDescription());
        teamDescription.getChildren().add(text);
    }

    private void setDialogImage(Team team){
        Image img = new Image(new ByteArrayInputStream(team.getImage()));
        teamImage.setImage(img);
    }

    private void setCloseRequest(Dialog dialog) {
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest((e) -> {
            dialog.hide();
        });
    }

    public Parent loadFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader(TeamPreviewController.class.getResource("/view/home/teamPreview.fxml"));
        loader.setController(this);
        return loader.load();
    }

}
