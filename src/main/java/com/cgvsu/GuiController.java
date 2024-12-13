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


        canvas.setOnKeyPressed(keyEvent -> {
            boolean isCtrlPressed = keyEvent.isControlDown();
            String direction = switch (keyEvent.getCode()) {
                case W -> "forward";
                case S -> "backward";
                case A -> "left";
                case D -> "right";
                case Q -> "up";
                case E -> "down";
                default -> null;
            };

            if (direction != null) {
                moveCamera(direction, isCtrlPressed);
            }
        });
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

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        moveCamera("forward", false);
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        moveCamera("backward", false);
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        moveCamera("left", false);
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        moveCamera("right", false);
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        moveCamera("up", false);
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        moveCamera("down", false);
    }

    private void moveCamera(String direction, boolean isCtrlPressed) {
        Vector3f moveVector = new Vector3f();

        switch (direction) {
            case "forward" -> {
                Vector3f forward = subtract(camera.getTarget(), camera.getPosition());
                forward.normalize();
                forward.multiply(TRANSLATION);
                moveVector = forward;
            }
            case "backward" -> {
                Vector3f backward = subtract(camera.getPosition(), camera.getTarget());
                backward.normalize();
                backward.multiply(TRANSLATION);
                moveVector = backward;
            }
            case "left" -> {
                Vector3f forward = subtract(camera.getTarget(), camera.getPosition());
                forward.normalize();
                Vector3f up = new Vector3f(0, 1, 0);
                Vector3f left = vectorProduct(up, forward);
                left.normalize();
                left.multiply(-TRANSLATION);
                moveVector = left;
            }
            case "right" -> {
                Vector3f forward = subtract(camera.getTarget(), camera.getPosition());
                forward.normalize();
                Vector3f up = new Vector3f(0, 1, 0);
                Vector3f right = vectorProduct(up, forward);
                right.normalize();
                right.multiply(TRANSLATION);
                moveVector = right;
            }
            case "up" -> {
                Vector3f up = new Vector3f(0, 1, 0);
                up.multiply(TRANSLATION);
                moveVector = up;
            }
            case "down" -> {
                Vector3f down = new Vector3f(0, 1, 0);
                down.multiply(-TRANSLATION);
                moveVector = down;
            }
        }

        // Двигаем камеру
        camera.movePosition(moveVector);

        // Если Ctrl не зажат, двигаем также цель
        if (!isCtrlPressed) {
            camera.moveTarget(moveVector);
        }
    }

}