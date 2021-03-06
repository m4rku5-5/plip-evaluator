package de.bioforscher.plip.evaluator.InsertionInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InsertionInterface extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("insertionInterface.fxml"));
        primaryStage.setTitle("Insert Literature for PLIP-Evaluator");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

    }

}
