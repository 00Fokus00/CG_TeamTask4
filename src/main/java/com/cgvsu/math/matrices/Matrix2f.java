package com.cgvsu.math.matrices;

import com.cgvsu.math.vectors.Vector2f;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Matrix2f {
    private Vector2f v1;
    private Vector2f v2;

    public Matrix2f() {
        this.v1 = new Vector2f();
        this.v2 = new Vector2f();
    }
    public Matrix2f(Vector2f v1, Vector2f v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Matrix2f(float[] matrix) throws IllegalArgumentException {
        if (matrix.length != 4) {
            throw new IllegalArgumentException("Length must be 4");
        }
        this.v1 = new Vector2f(matrix[0], matrix[1]);
        this.v2 = new Vector2f(matrix[2], matrix[3]);
    }

    public Matrix2f(float m0, float m1, float m2, float m3) throws IllegalArgumentException {
        this.v1 = new Vector2f(m0, m1);
        this.v2 = new Vector2f(m2, m3);
    }

    public static Matrix2f unitMatrix() {
        return new Matrix2f(new Vector2f(1, 0),
                new Vector2f(0, 1));
    }

    public static Matrix2f zeroMatrix() {
        return new Matrix2f(new Vector2f(),
                new Vector2f());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Matrix2f matrix2f = (Matrix2f) o;
        return this.v1.equals(matrix2f.getV1())
                && this.v2.equals(matrix2f.getV2());
    }

    public Matrix2f add(Matrix2f matrix2f) {
        this.v1.add(matrix2f.getV1());
        this.v2.add(matrix2f.getV2());
        return this;
    }

    public Matrix2f subtract(Matrix2f matrix2f) {
        this.v1.subtract(matrix2f.getV1());
        this.v2.subtract(matrix2f.getV2());
        return this;
    }

    public Vector2f multiplyByVector(Vector2f vector2f) {
        Vector2f result = new Vector2f();
        result.setX(this.v1.scalarProduct(vector2f));
        result.setY(this.v2.scalarProduct(vector2f));
        return result;
    }

    public Matrix2f multiplyByMatrix(Matrix2f matrix2f) {
        Vector2f v1Result = new Vector2f();
        v1Result.setX(this.v1.scalarProduct(new Vector2f(matrix2f.getV1().getX(), matrix2f.getV2().getX())));
        v1Result.setY(this.v1.scalarProduct(new Vector2f(matrix2f.getV1().getY(), matrix2f.getV2().getY())));

        Vector2f v2Result = new Vector2f();
        v2Result.setX(this.v2.scalarProduct(new Vector2f(matrix2f.getV1().getX(), matrix2f.getV2().getX())));
        v2Result.setY(this.v2.scalarProduct(new Vector2f(matrix2f.getV1().getY(), matrix2f.getV2().getY())));

        return new Matrix2f(v1Result, v2Result);
    }

    public Matrix2f transpose() {
        Vector2f v1Result = new Vector2f();
        v1Result.setX(this.v1.getX());
        v1Result.setY(this.v2.getX());

        Vector2f v2Result = new Vector2f();
        v2Result.setX(this.v1.getY());
        v2Result.setY(this.v2.getY());

        return new Matrix2f(v1Result, v2Result);
    }

    public float determinant() {
        return this.v1.getX() * this.v2.getY() - this.v1.getY() * this.v2.getX();
    }
}
