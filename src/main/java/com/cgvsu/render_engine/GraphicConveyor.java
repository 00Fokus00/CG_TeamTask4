package com.cgvsu.render_engine;

import com.cgvsu.math.matrices.Matrix4f;
import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector2f;

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
        Vector3f resultX = new Vector3f();
        Vector3f resultY = new Vector3f();
        Vector3f resultZ = new Vector3f();

        resultZ = target.subtract(eye);
        resultX = up.vectorProduct(resultZ);
        resultY = resultZ.vectorProduct(resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        /*Vector3f resultX = up;
        Vector3f resultZ = target;
        resultZ.subtract(eye);
        resultX.vectorProduct(resultZ);
        Vector3f resultY = resultZ.vectorProduct(resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();*/

        float[] matrix = new float[]{
                resultX.getX(), resultY.getX(), resultZ.getX(), 0,
                resultX.getY(), resultY.getY(), resultZ.getY(), 0,
                resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0,
                -resultX.scalarProduct(eye), -resultY.scalarProduct(eye), -resultZ.scalarProduct(eye), 1};
        return new Matrix4f(matrix).transpose();
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        Matrix4f result = new Matrix4f(new float[]{tangentMinusOnDegree, 0, 0, 0,
                0, tangentMinusOnDegree / aspectRatio, 0, 0,
                0, 0, (farPlane + nearPlane) / (farPlane - nearPlane), (2 * (nearPlane * farPlane)) / (nearPlane - farPlane),
                0, 0, 1, 0});
        return result;
    }

    public static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Vector2f(((width - 1) / 2) * vertex.getX() + ((width - 1) / 2), (((1 - height) / 2) * vertex.getY()) + ((height - 1) / 2));
       /*return new Vector2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);*/
    }
}
