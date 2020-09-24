package yushi;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;

/**
 * Canvas Controller
 */
public class CanvasController {

    private static final String MODEL_PATH = Paths.get("").toAbsolutePath().toString() + "/src/yushi/mnist_optimized.pb";
    private static final String INPUT_NAME = "x";
    private static final String OUTPUT_NAME = "output";

    private Stage stage;

    private Scene welcome;

    @FXML
    Label result;

    @FXML
    Button pencilButton;

    @FXML
    Button deleteButton;

    @FXML
    Button exitButton;

    @FXML
    Button guessButton;

    @FXML
    Canvas cvs;

    GraphicsContext gc;


    /**
     * Initialize stage and scene
     * @param stage
     * @param welcome
     */
    public void initStage(Stage stage, Scene welcome) {
        this.stage = stage;
        this.welcome = welcome;
    }

    /**
     * Switch scene to welcome
     */
    @FXML
    public void gotoWelcome() {
        stage.setScene(welcome);
    }

    /**
     * Initialize canvas graphics context
     * and define event handler
     */
    @FXML
    public void initCvs() {
        gc = cvs.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        cvs.setOnMousePressed(e -> {
            gc.beginPath();
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });
        cvs.setOnMouseDragged(e -> {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });
    }

    /**
     * Clear canvas
     */
    @FXML
    public void resetCvs() {
        if(gc == null) {
            return;
        }
        gc.clearRect(0, 0, cvs.getWidth(), cvs.getHeight());
        result.setText("?");
    }


    /**
     * Save image as png file in savedImage folder
     * Recognize digit with trained mnist model and
     * show predicted digit on screen
     * @throws IOException if mnist model file not found
     */
    @FXML
    public void saveAndGuess() throws IOException {
        String pwd = Paths.get("").toAbsolutePath().toString() + "/src/savedImage/";
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        File file = new File(pwd + "NumImage" + ts.getTime() + ".png");

        WritableImage img = new WritableImage((int)cvs.getWidth(), (int)cvs.getHeight());

        cvs.snapshot(null, img);

        BufferedImage rImg = SwingFXUtils.fromFXImage(img, null);

        // convert image to grayscale
        BufferedImage buffImg = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);

        // resize image to 28 * 28
        buffImg.getGraphics().drawImage(rImg.getScaledInstance(28, 28, Image.SCALE_SMOOTH), 0, 0, null);

        ImageIO.write(buffImg, "png", file);
        // END OF SAVING 28 * 28 GRAYSCALE PNG

        // get 4-d array from image
        float[][][][] fourDArray = convertImgToArray(buffImg);

        // get predicted result
        int res = predict(fourDArray);

        // show result on screen
        result.setText(String.valueOf(res));
    }

    /**
     * Convert a buffered image to 4-d array
     * @param buffImg
     * @return normalized 4-d array
     */
    private float[][][][] convertImgToArray(BufferedImage buffImg) {
        float[][][] inputData = new float[28][28][1];
        byte[] pixels = ((DataBufferByte) buffImg.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                inputData[i][j][0] = (float) (pixels[i * 28 + j] & 0xFF);
                inputData[i][j][0] = inputData[i][j][0] / 255.0f;
                inputData[i][j][0] = 1.0f - inputData[i][j][0];
            }
        }
        // END OF GETTING 3D ARRAY

        float[][][][] fourDArray = new float[1][][][];
        fourDArray[0] = inputData;
        return fourDArray;
        // END OF GETTING 4D ARRAY
    }

    /**
     * Load mnist model and run a session to
     * predict the digit represented by 4-d array
     * @param array
     * @return the predicted digit
     */
    private int predict(float[][][][] array) {
        File modelFile = new File(MODEL_PATH);
        byte[] graph = null;
        try {
            graph = Files.readAllBytes(modelFile.toPath());
        } catch (Exception e) {
            System.out.println("File not found!");
        }
        if(graph == null) {
            System.out.println("Import Graph Failure!");
        }
        try(Graph g = new Graph()) {
            // import graph
            g.importGraphDef(graph);

            try(Session sess = new Session(g)) {
                // create an input Tensor with 4-d array
                Tensor inputTensor = Tensor.create(array, Float.class);
                // fetch output Tensor
                Tensor result = sess.runner()
                        .feed(INPUT_NAME, inputTensor)
                        .fetch(OUTPUT_NAME).run().get(0);
                float[][] output = new float[1][10];
                result.copyTo(output);

                // extract the index with max probability
                float res = Float.MIN_VALUE;
                int index = -1;
                for(int i = 0; i < output[0].length; i++) {
                    if(output[0][i] > res) {
                        res = output[0][i];
                        index = i;
                    }
                }
                return index;
            }
        }
    }
}

