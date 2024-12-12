package com.cgvsu;

import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    public Pane canvasPane;
    @FXML
    public VBox modelsContainer;


    //TODO вот в эти поля добавить координаты камеры
    public TextField xCameraPositin;
    public TextField yCameraPositin;
    public TextField zCameraPositin;

    //TODO вот в эти поля добавить координаты таргета
    public TextField xTargetPositin;
    public TextField yTargetPositin;
    public TextField zTargetPosition;

    //TODO это кнопочка для добавления новых камер, это к уважаемому Win122333)
    public Button addCamera;
    //TODO К нему же модели освещения
    public Button addLightingModel;

    //Поля для афинных
    public TextField xScale;
    public TextField yScale;
    public TextField zScale;
    public TextField xRotation;
    public TextField yRotation;
    public TextField zRotation;
    public TextField xTrans;
    public TextField yTrans;
    public TextField zTrans;
    //Кнопка для афинных
    public Button affineTransorm;

    public Button calcNormals;
    public Button triangulation;


    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private int modelCounter = 1;

    private Model mesh = null;
    private Model meshTriangulated = null;

    private Camera camera = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        canvasPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        canvasPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.015), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (height / width));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
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

        //Вот тут пример как добавлять список кнопок под каждую модель
        Label modelNameLabel = new Label("Модель: " + modelCounter);
        modelNameLabel.setStyle("-fx-text-fill: white;");
        Button deleteButton = new Button("Удалить");
        Button addTextureButton = new Button("Добавить текстуру ");
        Button removeTextureButton = new Button("Удалить текстуру ");

        deleteButton.setOnAction(event -> {
            modelsContainer.getChildren().remove(deleteButton.getParent());
        });

        HBox modelBox = new HBox(5, modelNameLabel, deleteButton, addTextureButton, removeTextureButton);
        modelsContainer.getChildren().add(modelBox);

        modelCounter++;

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.moveTarget(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.moveTarget(new Vector3f(0, -TRANSLATION, 0));
    }
}