package com.cgvsu;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.render_engine.Triangulation;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
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
import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    public Pane canvasPane;
    @FXML
    public VBox modelsContainer;
    @FXML
    public VBox camerasContainer;


    //TODO вот в эти поля добавить координаты камеры
    public TextField xCameraPosition;
    public TextField yCameraPosition;
    public TextField zCameraPosition;
    public Vector3f cameraPosition;

    //TODO вот в эти поля добавить координаты таргета
    public TextField xTargetPosition;
    public TextField yTargetPosition;
    public TextField zTargetPosition;
    public Vector3f targetPosition;

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
    public Button affineTransform;

    public Button calcNormals;
    public Button triangulation;


    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private int modelCounter = 1;
    private int cameraCounter = 2;
    private int globalModelIndex = 0;
    private int globalCameraIndex = 1;

    private int currCameraIndex = 0;

    private final Model mesh = new Model();

    private final Camera nullCamera = new Camera(
            new Vector3f(0, 0, 50),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Camera currCamera = new Camera(
            new Vector3f(0, 0, 50),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private ArrayList<Camera> cameraArrayList = new ArrayList<>();
    private ArrayList<Model> modelArrayList = new ArrayList<>();


    private Timeline timeline;

    @FXML
    private void initialize() {
        //НЕ ЗНАЮ ГДЕ ДОЛЖЕН БЫТЬ ЭТОТ БЛОК
        cameraArrayList.add(currCamera);
        Label modelNameLabel = new Label("Камера main");
        modelNameLabel.setStyle("-fx-text-fill: white;");
        Button moveToButton = new Button("Переместиться");




        moveToButton.setOnAction(event -> {
           currCamera = cameraArrayList.get(0);
        });

        HBox modelBox = new HBox(5, modelNameLabel, moveToButton);
        camerasContainer.getChildren().add(modelBox);




        canvasPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        canvasPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));



        timeline = new Timeline(new KeyFrame(Duration.seconds(0.015), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            currCamera.setAspectRatio((float) (height / width));
            for (Model model : modelArrayList) {
                if (model != null && model.enable) {
                    RenderEngine.render(canvas.getGraphicsContext2D(), currCamera, model, (int) width, (int) height);
                }
            }

            if (!currCamera.getPosition().equals(cameraPosition)) {
                cameraPosition = new Vector3f(
                        currCamera.getPosition().getX(),
                        currCamera.getPosition().getY(),
                        currCamera.getPosition().getZ()
                );
                xCameraPosition.setText(String.valueOf(cameraPosition.getX()));
                yCameraPosition.setText(String.valueOf(cameraPosition.getY()));
                zCameraPosition.setText(String.valueOf(cameraPosition.getZ()));
            }

            if (!currCamera.getTarget().equals(targetPosition)) {
                targetPosition = new Vector3f(
                        currCamera.getTarget().getX(),
                        currCamera.getTarget().getY(),
                        currCamera.getTarget().getZ()
                );
                xTargetPosition.setText(String.valueOf(targetPosition.getX()));
                yTargetPosition.setText(String.valueOf(targetPosition.getY()));
                zTargetPosition.setText(String.valueOf(targetPosition.getZ()));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void positionChanged(KeyEvent keyEvent) {
        cameraPosition = new Vector3f(
                Float.parseFloat(xCameraPosition.getText()),
                Float.parseFloat(yCameraPosition.getText()),
                Float.parseFloat(zCameraPosition.getText())
        );
        currCamera.setPosition(cameraPosition);
    }

    public void targetChanged(KeyEvent keyEvent) {
        targetPosition = new Vector3f(
                Float.parseFloat(xTargetPosition.getText()),
                Float.parseFloat(yTargetPosition.getText()),
                Float.parseFloat(zTargetPosition.getText())
        );
        currCamera.setTarget(targetPosition);
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
        CheckBox enable = new CheckBox();
        enable.setSelected(true);

        int currIndex = globalModelIndex;
        globalModelIndex++;

        deleteButton.setOnAction(event -> {
            modelsContainer.getChildren().remove(deleteButton.getParent());
            modelCounter--;
            modelArrayList.set(currIndex, mesh);
        });

        enable.setOnAction(actionEvent -> {
            modelArrayList.get(currIndex).enable = enable.isSelected();
        });

        HBox modelBox = new HBox(5, modelNameLabel, deleteButton, addTextureButton, removeTextureButton, enable);
        modelsContainer.getChildren().add(modelBox);



        modelCounter++;



        try {
            String fileContent = Files.readString(fileName);
            Model currModel = ObjReader.read(fileContent);
            modelArrayList.add(currModel);
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        currCamera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        currCamera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        currCamera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        currCamera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        currCamera.moveTarget(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        currCamera.moveTarget(new Vector3f(0, -TRANSLATION, 0));
    }

    public void triangulate(ActionEvent actionEvent) {

    }

    public void addCamera(ActionEvent actionEvent) {
        Label cameraNameLabel = new Label("Камера: " + cameraCounter);
        cameraNameLabel.setStyle("-fx-text-fill: white;");
        Button deleteButton = new Button("Удалить");
        Button moveToButton = new Button("Переместиться");


        int currIndex = globalCameraIndex;
        globalCameraIndex++;

        moveToButton.setOnAction(event -> {
            currCamera = cameraArrayList.get(currIndex);
            currCameraIndex = currIndex;
        });



        deleteButton.setOnAction(event -> {
            camerasContainer.getChildren().remove(deleteButton.getParent());
            cameraCounter--;
            if (currCameraIndex == currIndex) {
                currCamera = cameraArrayList.get(0);
            }
            cameraArrayList.set(currIndex, nullCamera);
        });

        HBox cameraBox = new HBox(5, cameraNameLabel, deleteButton, moveToButton);
        camerasContainer.getChildren().add(cameraBox);



        cameraCounter++;

        Camera newCam = new Camera(
                new Vector3f(0, 0, 50),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100);



        cameraArrayList.add(newCam);

    }
}