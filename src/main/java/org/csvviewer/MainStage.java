package org.csvviewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStage extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainStage.class.getResource("CSV-viewer-main-stage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CSV viewer");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}