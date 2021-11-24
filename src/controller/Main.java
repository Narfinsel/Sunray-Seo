package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../ui/UI_Dark_SunraySEO_BlogData.fxml"));
        primaryStage.setTitle("SunraySeo");
        primaryStage.getIcons().add( new Image ("graphics/SuncastSEO_1.png") );

        Scene scene = new Scene(root, 1600, 1060);
        primaryStage.setScene(scene);

        scene.getStylesheets().add("ui/Style.css");

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
