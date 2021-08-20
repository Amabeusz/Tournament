package view;

import javafx.stage.FileChooser;

import java.io.File;

public class ImagePicker {

    public static File pickImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select team image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        return fileChooser.showOpenDialog(null);
    }
}
