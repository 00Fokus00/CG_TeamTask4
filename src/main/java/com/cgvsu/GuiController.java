package com.cgvsu;

import com.cgvsu.camera.CameraController;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.camera.Camera;
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


public class GuiController {

    final private float TRANSLATION = 0.5F;
    final private float ROTATION = 0.01F;
    final private float ZOOM = 0.3F;
    private double lastMouseX;
    private double lastMouseY;
    private boolean isMousePressed;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 0, 20),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private CameraController cameraController = new CameraController(camera, TRANSLATION, ROTATION, ZOOM);

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
                cameraController.moveCamera(direction, isCtrlPressed);
            }
        });

        canvas.setOnMousePressed(mouseEvent -> {
            canvas.requestFocus();
            lastMouseX = mouseEvent.getX();
            lastMouseY = mouseEvent.getY();
            isMousePressed = true;
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            if (isMousePressed) {
                double deltaX = mouseEvent.getX() - lastMouseX;
                double deltaY = mouseEvent.getY() - lastMouseY;
                cameraController.handleMouseDrag(deltaX, deltaY);
                lastMouseX = mouseEvent.getX();
                lastMouseY = mouseEvent.getY();
            }
        });

        canvas.setOnMouseReleased(mouseEvent -> isMousePressed = false);
        canvas.setOnScroll(scrollEvent -> cameraController.handleMouseScroll(scrollEvent.getDeltaY()));
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
        cameraController.moveCamera("forward", false);
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        cameraController.moveCamera("backward", false);
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        cameraController.moveCamera("left", false);
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        cameraController.moveCamera("right", false);
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        cameraController.moveCamera("up", false);
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        cameraController.moveCamera("down", false);
    }

}