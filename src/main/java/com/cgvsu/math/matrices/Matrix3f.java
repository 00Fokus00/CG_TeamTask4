package com.cgvsu.math.matrices;

import com.cgvsu.math.vectors.Vector3f;
import static com.cgvsu.math.vectors.Vector3f.scalarProduct;

public class Matrix3f {
    private Vector3f v1;
    private Vector3f v2;
    private Vector3f v3;

    public Matrix3f() {
        this.v1 = new Vector3f();
        this.v2 = new Vector3f();
        this.v3 = new Vector3f();
    }
    public Matrix3f(Vector3f v1, Vector3f v2, Vector3f v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Matrix3f(float[] matrix) throws IllegalArgumentException {
        if (matrix.length != 9) {
            throw new IllegalArgumentException("Length must be 9");
        }
        this.v1 = new Vector3f(matrix[0], matrix[1], matrix[2]);
        this.v2 = new Vector3f(matrix[3], matrix[4], matrix[5]);
        this.v3 = new Vector3f(matrix[6], matrix[7], matrix[8]);
    }

    public static Matrix3f unitMatrix() {
        return new Matrix3f(new Vector3f(1, 0, 0),
                new Vector3f(0, 1, 0),
                new Vector3f(0, 0, 1));
    }

    public static Matrix3f zeroMatrix() {
        return new Matrix3f(new Vector3f(),
                new Vector3f(),
                new Vector3f());
    }

    public Vector3f getV1() {
        return v1;
    }

    public void setV1(Vector3f v1) {
        this.v1 = v1;
    }

    public Vector3f getV2() {
        return v2;
    }

    public void setV2(Vector3f v2) {
        this.v2 = v2;
    }

    public Vector3f getV3() {
        return v3;
    }

    public void setV3(Vector3f v3) {
        this.v3 = v3;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Matrix3f matrix3f = (Matrix3f) o;
        return this.v1.equals(matrix3f.v1)
                && this.v2.equals(matrix3f.v2)
                && this.v3.equals(matrix3f.v3);
    }

    @Override
    public String toString() {
        return "Matrix3f{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                ", v3=" + v3 +
                '}';
    }

    public static Matrix3f add(Matrix3f matrix1, Matrix3f matrix2) {
        Matrix3f result = new Matrix3f();
        result.setV1(Vector3f.add(matrix1.v1, matrix2.v1));
        result.setV2(Vector3f.add(matrix1.v2, matrix2.v2));
        result.setV3(Vector3f.add(matrix1.v3, matrix2.v3));
        return result;
    }

    public static Matrix3f subtract(Matrix3f matrix1, Matrix3f matrix2) {
        Matrix3f result = new Matrix3f();
        result.setV1(Vector3f.subtract(matrix1.v1, matrix2.v1));
        result.setV2(Vector3f.subtract(matrix1.v2, matrix2.v2));
        result.setV3(Vector3f.subtract(matrix1.v3, matrix2.v3));
        return result;
    }

    public static Vector3f multiply(Matrix3f matrix3f, Vector3f vector3f) {
        return new Vector3f(scalarProduct(matrix3f.v1, vector3f), scalarProduct(matrix3f.v2, vector3f), scalarProduct(matrix3f.v3, vector3f));
    }

    public static Matrix3f multiply(Matrix3f matrix1, Matrix3f matrix2) {
        Vector3f v1Result = new Vector3f();
        v1Result.setX(scalarProduct(matrix1.v1, new Vector3f(matrix2.v1.getX(), matrix2.v2.getX(), matrix2.v3.getX())));
        v1Result.setY(scalarProduct(matrix1.v1, new Vector3f(matrix2.v1.getY(), matrix2.v2.getY(), matrix2.v3.getY())));
        v1Result.setZ(scalarProduct(matrix1.v1, new Vector3f(matrix2.v1.getZ(), matrix2.v2.getZ(), matrix2.v3.getZ())));

        Vector3f v2Result = new Vector3f();
        v2Result.setX(scalarProduct(matrix1.v2, new Vector3f(matrix2.v1.getX(), matrix2.v2.getX(), matrix2.v3.getX())));
        v2Result.setY(scalarProduct(matrix1.v2, new Vector3f(matrix2.v1.getY(), matrix2.v2.getY(), matrix2.v3.getY())));
        v2Result.setZ(scalarProduct(matrix1.v2, new Vector3f(matrix2.v1.getZ(), matrix2.v2.getZ(), matrix2.v3.getZ())));

        Vector3f v3Result = new Vector3f();
        v3Result.setX(scalarProduct(matrix1.v3, new Vector3f(matrix2.v1.getX(), matrix2.v2.getX(), matrix2.v3.getX())));
        v3Result.setY(scalarProduct(matrix1.v3, new Vector3f(matrix2.v1.getY(), matrix2.v2.getY(), matrix2.v3.getY())));
        v3Result.setZ(scalarProduct(matrix1.v3, new Vector3f(matrix2.v1.getZ(), matrix2.v2.getZ(), matrix2.v3.getZ())));

        return new Matrix3f(v1Result, v2Result, v3Result);
    }

    public Matrix3f transpose() {
        Vector3f v1Result = new Vector3f();
        v1Result.setX(this.v1.getX());
        v1Result.setY(this.v2.getX());
        v1Result.setZ(this.v3.getX());

        Vector3f v2Result = new Vector3f();
        v2Result.setX(this.v1.getY());
        v2Result.setY(this.v2.getY());
        v2Result.setZ(this.v3.getY());

        Vector3f v3Result = new Vector3f();
        v3Result.setX(this.v1.getZ());
        v3Result.setY(this.v2.getZ());
        v3Result.setZ(this.v3.getZ());

        return new Matrix3f(v1Result, v2Result, v3Result);
    }

    public float determinant() {
        return this.v1.getX() * this.v2.getY() * this.v3.getZ() +
                this.v1.getY() * this.v2.getZ() * this.v3.getX() +
                this.v2.getX() * this.v3.getY() * this.v1.getZ() -
                this.v1.getZ() * this.v2.getY() * this.v3.getX() -
                this.v1.getY() * this.v2.getX() * this.v3.getZ() -
                this.v1.getX() * this.v2.getZ() * this.v3.getY();

    }
}