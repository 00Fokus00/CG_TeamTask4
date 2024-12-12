package com.cgvsu.render_engine;

import com.cgvsu.math.matrices.Matrix4f;
import com.cgvsu.math.vectors.Vector3f;
import lombok.Data;
import static com.cgvsu.math.vectors.Vector3f.add;
import static com.cgvsu.math.vectors.Vector3f.subtract;

@Data
public class Camera {

    private Vector3f position;
    private Vector3f target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void movePosition(final Vector3f translation) {
        this.position = add(this.position, translation);
    }

    public void moveTarget(final Vector3f translation) {
        this.target = add(this.target, translation);
    }

    Matrix4f getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    Matrix4f getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public void rotateAroundTarget(float deltaPhi, float deltaTheta) {
        Vector3f direction = subtract(position, target);
        float radius = direction.getLength();

        float phi = (float) Math.acos(direction.getY() / radius);
        float theta = (float) Math.atan2(direction.getZ(), direction.getX());

        phi = Math.max(0.1F, Math.min((float) Math.PI - 0.1F, phi + deltaPhi));
        theta += deltaTheta;

        float x = (float) (radius * Math.sin(phi) * Math.cos(theta));
        float y = (float) (radius * Math.cos(phi));
        float z = (float) (radius * Math.sin(phi) * Math.sin(theta));

        this.position = add(target, new Vector3f(x, y, z));
    }
}