package com.cgvsu;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

import static com.cgvsu.math.vectors.Vector3f.subtract;
import static com.cgvsu.math.vectors.Vector3f.vectorProduct;

public class GuiController {

    final private float TRANSLATION = 0.5F;
    final private float ROTATION = 0.05F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 0, 20),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (height / width));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

//    @FXML
//    public void handleCameraForward(ActionEvent actionEvent) {
//        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
//    }
//
//    @FXML
//    public void handleCameraBackward(ActionEvent actionEvent) {
//        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
//    }

//    @FXML
//    public void handleCameraLeft(ActionEvent actionEvent) {
//        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
//        camera.moveTarget(new Vector3f(TRANSLATION, 0, 0));
//    }
//
//    @FXML
//    public void handleCameraRight(ActionEvent actionEvent) {
//        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
//        camera.moveTarget(new Vector3f(-TRANSLATION, 0, 0));
//    }
//
//    @FXML
//    public void handleCameraUp(ActionEvent actionEvent) {
//        //camera.movePosition(new Vector3f(0, TRANSLATION, 0));
//        //camera.moveTarget(new Vector3f(0, TRANSLATION, 0));
//        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
//        camera.moveTarget(new Vector3f(0, 0, -TRANSLATION));
//    }
//
//    @FXML
//    public void handleCameraDown(ActionEvent actionEvent) {
//        //camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
//        //camera.moveTarget(new Vector3f(0, -TRANSLATION, 0));
//        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
//        camera.moveTarget(new Vector3f(0, 0, TRANSLATION));
//    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        Vector3f direction = subtract(camera.getTarget(), camera.getPosition());
        direction.normalize();
        direction.multiply(TRANSLATION);
        camera.movePosition(direction);
        camera.moveTarget(direction);
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        Vector3f direction = subtract(camera.getTarget(), camera.getPosition());
        direction.normalize();
        direction.multiply(-TRANSLATION);
        camera.movePosition(direction);
        camera.moveTarget(direction);
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        Vector3f direction = subtract(camera.getTarget(), camera.getPosition());
        direction.normalize();
        Vector3f up = new Vector3f(0, 1, 0);
        Vector3f right = vectorProduct(direction, up);
        right.normalize();
        right.multiply(TRANSLATION);
        camera.movePosition(right);
        camera.moveTarget(right);
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        Vector3f direction = subtract(camera.getTarget(), camera.getPosition());
        direction.normalize();
        Vector3f up = new Vector3f(0, 1, 0);
        Vector3f right = vectorProduct(direction, up);
        right.normalize();
        right.multiply(-TRANSLATION);
        camera.movePosition(right);
        camera.moveTarget(right);
    }





    @FXML
    public void handleCameraRotateUp(ActionEvent actionEvent) {
        camera.rotateAroundTarget(-ROTATION, 0);
    }

    @FXML
    public void handleCameraRotateDown(ActionEvent actionEvent) {
        camera.rotateAroundTarget(ROTATION, 0);
    }

    @FXML
    public void handleCameraRotateLeft(ActionEvent actionEvent) {
        camera.rotateAroundTarget(0, -ROTATION);
    }

    @FXML
    public void handleCameraRotateRight(ActionEvent actionEvent) {
        camera.rotateAroundTarget(0, ROTATION);
    }
}