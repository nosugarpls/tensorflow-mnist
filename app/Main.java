package yushi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // load welcome scene
        FXMLLoader welcomeLoader = new FXMLLoader(getClass().getResource("welcome.fxml"));
        Parent root = welcomeLoader.load();
        WelcomeController welcomeController = welcomeLoader.getController();
        welcomeLoader.setController(welcomeController);
        Scene welcome = new Scene(root, 700, 500);

        // initialize primary stage
        primaryStage.setResizable(false);
        primaryStage.setTitle("Number Recognizer");
        primaryStage.setScene(welcome);

        // load canvas scene
        FXMLLoader canvasLoader = new FXMLLoader(getClass().getResource("canvas.fxml"));
        Parent canvasRoot = canvasLoader.load();
        CanvasController canvasController = canvasLoader.getController();
        canvasLoader.setController(canvasController);
        Scene canvas = new Scene(canvasRoot, 700, 500);

        // pass primaryStage and canvas to welcomeController
        welcomeController.initStage(primaryStage, canvas);

        // pass primaryStage and welcome to canvasController
        canvasController.initStage(primaryStage, welcome);

        primaryStage.show();

    }
}

