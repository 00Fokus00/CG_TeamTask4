package com.cgvsu.math.matrices;

import com.cgvsu.math.vectors.Vector3f;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public static Matrix3f unitMatrix3f() {
        return new Matrix3f(new Vector3f(1, 0, 0),
                new Vector3f(0, 1, 0),
                new Vector3f(0, 0, 1));
    }

    public static Matrix3f zeroMatrix3f() {
        return new Matrix3f(new Vector3f(),
                new Vector3f(),
                new Vector3f());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Matrix3f matrix3f = (Matrix3f) o;
        return this.v1.equals(matrix3f.getV1())
                && this.v2.equals(matrix3f.getV2())
                && this.v3.equals(matrix3f.getV3());
    }

    public Matrix3f add(Matrix3f matrix3f) {
        this.v1.add(matrix3f.getV1());
        this.v2.add(matrix3f.getV2());
        this.v3.add(matrix3f.getV3());
        return this;
    }

    public Matrix3f subtract(Matrix3f matrix3f) {
        this.v1.subtract(matrix3f.getV1());
        this.v2.subtract(matrix3f.getV2());
        this.v3.subtract(matrix3f.getV3());
        return this;
    }

    public Vector3f multiplyByVector(Vector3f vector3f) {
        Vector3f result = new Vector3f();
        result.setX(this.v1.scalarProduct(vector3f));
        result.setY(this.v2.scalarProduct(vector3f));
        result.setZ(this.v3.scalarProduct(vector3f));
        return result;
    }

    public Matrix3f multiplyByMatrix(Matrix3f matrix3f) {
        Vector3f v1Result = new Vector3f();
        v1Result.setX(this.v1.scalarProduct(new Vector3f(matrix3f.getV1().getX(), matrix3f.getV2().getX(), matrix3f.getV3().getX())));
        v1Result.setY(this.v1.scalarProduct(new Vector3f(matrix3f.getV1().getY(), matrix3f.getV2().getY(), matrix3f.getV3().getY())));
        v1Result.setZ(this.v1.scalarProduct(new Vector3f(matrix3f.getV1().getZ(), matrix3f.getV2().getZ(), matrix3f.getV3().getZ())));

        Vector3f v2Result = new Vector3f();
        v2Result.setX(this.v2.scalarProduct(new Vector3f(matrix3f.getV1().getX(), matrix3f.getV2().getX(), matrix3f.getV3().getX())));
        v2Result.setY(this.v2.scalarProduct(new Vector3f(matrix3f.getV1().getY(), matrix3f.getV2().getY(), matrix3f.getV3().getY())));
        v2Result.setZ(this.v2.scalarProduct(new Vector3f(matrix3f.getV1().getZ(), matrix3f.getV2().getZ(), matrix3f.getV3().getZ())));

        Vector3f v3Result = new Vector3f();
        v3Result.setX(this.v3.scalarProduct(new Vector3f(matrix3f.getV1().getX(), matrix3f.getV2().getX(), matrix3f.getV3().getX())));
        v3Result.setY(this.v3.scalarProduct(new Vector3f(matrix3f.getV1().getY(), matrix3f.getV2().getY(), matrix3f.getV3().getY())));
        v3Result.setZ(this.v3.scalarProduct(new Vector3f(matrix3f.getV1().getZ(), matrix3f.getV2().getZ(), matrix3f.getV3().getZ())));

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