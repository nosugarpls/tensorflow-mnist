package yushi;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Welcome Controller
 */
public class WelcomeController {

    @FXML
    Button goButton;

    private Stage stage;
    private Scene canvas;

    /**
     * Initialize stage and scene
     * @param stage
     * @param canvas
     */
    public void initStage(Stage stage, Scene canvas) {
        this.stage = stage;
        this.canvas = canvas;
    }

    /**
     * Switch scene to canvas
     */
    @FXML
    public void gotoCanvas() {
        stage.setScene(canvas);
    }

}
