package com.cgvsu.camera;

import com.cgvsu.math.vectors.Vector3f;

import static com.cgvsu.math.vectors.Vector3f.subtract;
import static com.cgvsu.math.vectors.Vector3f.vectorProduct;

public class CameraController {
    private final Camera camera;
    private final float translationSpeed;
    private final float rotationSpeed;
    private final float zoomSpeed;

    public CameraController(Camera camera, float translationSpeed, float rotationSpeed, float zoomSpeed) {
        this.camera = camera;
        this.translationSpeed = translationSpeed;
        this.rotationSpeed = rotationSpeed;
        this.zoomSpeed = zoomSpeed;
    }

    public void moveCamera(String direction, boolean isCtrlPressed) {
        Vector3f moveVector = new Vector3f();

        switch (direction) {
            case "forward" -> {
                Vector3f forward = subtract(camera.getTarget(), camera.getPosition());
                forward.normalize();
                forward.multiply(translationSpeed);
                moveVector = forward;
            }
            case "backward" -> {
                Vector3f backward = subtract(camera.getPosition(), camera.getTarget());
                backward.normalize();
                backward.multiply(translationSpeed);
                moveVector = backward;
            }
            case "left" -> {
                Vector3f forward = subtract(camera.getTarget(), camera.getPosition());
                forward.normalize();
                Vector3f up = new Vector3f(0, 1, 0);
                Vector3f left = vectorProduct(up, forward);
                left.normalize();
                left.multiply(-translationSpeed);
                moveVector = left;
            }
            case "right" -> {
                Vector3f forward = subtract(camera.getTarget(), camera.getPosition());
                forward.normalize();
                Vector3f up = new Vector3f(0, 1, 0);
                Vector3f right = vectorProduct(up, forward);
                right.normalize();
                right.multiply(translationSpeed);
                moveVector = right;
            }
            case "up" -> {
                Vector3f up = new Vector3f(0, 1, 0);
                up.multiply(translationSpeed);
                moveVector = up;
            }
            case "down" -> {
                Vector3f down = new Vector3f(0, 1, 0);
                down.multiply(-translationSpeed);
                moveVector = down;
            }
        }

        camera.movePosition(moveVector);
        if (!isCtrlPressed) {
            camera.moveTarget(moveVector);
        }
    }

    public void handleMouseDrag(double deltaX, double deltaY) {
        float deltaTheta = (float) (deltaX * rotationSpeed);
        float deltaPhi = (float) (deltaY * rotationSpeed);
        camera.rotateAroundTarget(deltaPhi, deltaTheta);
    }

    public void handleMouseScroll(double deltaY) {
        Vector3f direction = subtract(camera.getTarget(), camera.getPosition());
        float distance = direction.getLength();

        if (distance - deltaY * zoomSpeed > 0.5F) {
            direction.normalize();
            direction.multiply((float) deltaY * zoomSpeed);
            camera.movePosition(direction);
        }
    }
}
