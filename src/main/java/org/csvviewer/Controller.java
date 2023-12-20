package org.csvviewer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ChoiceBox<Character> delimiterChoiceBox;
    @FXML
    private CheckBox headerCheckBox;
    @FXML
    private Button chooseFileButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delimiterChoiceBox.getItems().addAll(',', ';');
        chooseFileButton.disableProperty().bind(delimiterChoiceBox.valueProperty().isNull());
    }

    @FXML
    public void processChoosingFile() throws IOException {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("CSV files", "*.txt", "*.csv");
        fc.getExtensionFilters().add(ef);
        File CSVFile = fc.showOpenDialog(new Stage());
        if (CSVFile == null){
            return; // file chooser was closed
        }
        if (!CSVFile.canRead()){
            throw new IOException("Invalid file");
        }
        if (!isCSVFile(CSVFile)){
            throw new IOException("Only .txt and .csv files are accepted");
        }
        showTable(CSVFile);
    }

    private boolean isCSVFile(File csvFile) {
        String fileName = csvFile.getName();
        int index = fileName.lastIndexOf(".");
        String extension = index > 0 ? fileName.substring(index) : "";
        return extension.equals(".txt") || extension.equals(".csv");
    }

    private void showTable(File CSVFile) {
        new TableStage(CSVFile, delimiterChoiceBox.getValue(), headerCheckBox.isSelected()).init().show();
    }
}
