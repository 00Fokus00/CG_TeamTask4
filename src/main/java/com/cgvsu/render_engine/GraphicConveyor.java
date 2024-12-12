package com.cgvsu.render_engine;

import com.cgvsu.math.matrices.Matrix4f;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector2f;
import static com.cgvsu.math.vectors.Vector3f.*;

public class GraphicConveyor {

    public static Matrix4f rotateScaleTranslate() {
        float[] matrix = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {

        Vector3f resultZ = subtract(target, eye);
        Vector3f resultX = vectorProduct(up, resultZ);
        Vector3f resultY = vectorProduct(resultZ, resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        float[] matrix = new float[]{
                resultX.getX(), resultX.getY(), resultX.getZ(), -scalarProduct(resultX, eye),
                resultY.getX(), resultY.getY(), resultY.getZ(), -scalarProduct(resultY, eye),
                resultZ.getX(), resultZ.getY(), resultZ.getZ(), -scalarProduct(resultZ, eye),
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        float[] matrix = new float[]{
                tangentMinusOnDegree, 0, 0, 0,
                0, tangentMinusOnDegree / aspectRatio, 0, 0,
                0, 0, (farPlane + nearPlane) / (farPlane - nearPlane), (2 * (nearPlane * farPlane)) / (nearPlane - farPlane),
                0, 0, 1, 0};
        return new Matrix4f(matrix);
    }

    public static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Vector2f(((float) (width - 1) / 2) * vertex.getX() + ((float) (width - 1) / 2), (((float) (1 - height) / 2) * vertex.getY()) + ((float) (height - 1) / 2));
      }
}
