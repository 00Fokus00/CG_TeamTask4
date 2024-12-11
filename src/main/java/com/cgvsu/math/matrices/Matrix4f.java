package com.cgvsu.math.matrices;

import com.cgvsu.math.vectors.Vector3f;
import com.cgvsu.math.vectors.Vector4f;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Matrix4f {
    private Vector4f v1;
    private Vector4f v2;
    private Vector4f v3;
    private Vector4f v4;

    public Matrix4f() {
        this.v1 = new Vector4f();
        this.v2 = new Vector4f();
        this.v3 = new Vector4f();
        this.v4 = new Vector4f();
    }
    public Matrix4f(Vector4f v1, Vector4f v2, Vector4f v3, Vector4f v4) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
    }

    public static Matrix4f unitMatrix() {
        return new Matrix4f(new Vector4f(1, 0, 0, 0),
                new Vector4f(0, 1, 0, 0),
                new Vector4f(0, 0, 1, 0),
                new Vector4f(0, 0, 0, 1));
    }

    public static Matrix4f zeroMatrix() {
        return new Matrix4f(new Vector4f(),
                new Vector4f(),
                new Vector4f(),
                new Vector4f());
    }

    public Matrix4f(float[] matrix) throws IllegalArgumentException {
        if (matrix.length != 16) {
            throw new IllegalArgumentException("Length must be 16");
        }
        this.v1 = new Vector4f(matrix[0], matrix[1], matrix[2], matrix[3]);
        this.v2 = new Vector4f(matrix[4], matrix[5], matrix[6], matrix[7]);
        this.v3 = new Vector4f(matrix[8], matrix[9], matrix[10], matrix[11]);
        this.v4 = new Vector4f(matrix[12], matrix[13], matrix[14], matrix[15]);
    }

    public Matrix4f(float m0, float m1, float m2, float m3, float m4, float m5, float m6, float m7, float m8, float m9, float m10, float m11, float m12, float m13, float m14, float m15) {
        this.v1 = new Vector4f(m0, m1, m2, m3);
        this.v2 = new Vector4f(m4, m5, m6, m7);
        this.v3 = new Vector4f(m8, m9, m10, m11);
        this.v4 = new Vector4f(m12, m13, m14, m15);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Matrix4f matrix4f = (Matrix4f) o;
        return this.v1.equals(matrix4f.getV1())
                && this.v2.equals(matrix4f.getV2())
                && this.v3.equals(matrix4f.getV3())
                && this.v4.equals(matrix4f.getV4());
    }

    public Matrix4f add(Matrix4f matrix4f) {
        this.v1.add(matrix4f.getV1());
        this.v2.add(matrix4f.getV2());
        this.v3.add(matrix4f.getV3());
        this.v4.add(matrix4f.getV4());
        return this;
    }

    public Matrix4f subtract(Matrix4f matrix4f) {
        this.v1.subtract(matrix4f.getV1());
        this.v2.subtract(matrix4f.getV2());
        this.v3.subtract(matrix4f.getV3());
        this.v4.subtract(matrix4f.getV4());
        return this;
    }

    public Vector4f multiplyByVector(Vector4f vector4f) {
        Vector4f result = new Vector4f();
        result.setX(this.v1.scalarProduct(vector4f));
        result.setY(this.v2.scalarProduct(vector4f));
        result.setZ(this.v3.scalarProduct(vector4f));
        result.setW(this.v4.scalarProduct(vector4f));
        return result;
    }

    public Matrix4f multiplyByMatrix(Matrix4f matrix4f) {
        Vector4f v1Result = new Vector4f();
        v1Result.setX(this.v1.scalarProduct(new Vector4f(matrix4f.getV1().getX(), matrix4f.getV2().getX(), matrix4f.getV3().getX(), matrix4f.getV4().getX())));
        v1Result.setY(this.v1.scalarProduct(new Vector4f(matrix4f.getV1().getY(), matrix4f.getV2().getY(), matrix4f.getV3().getY(), matrix4f.getV4().getY())));
        v1Result.setZ(this.v1.scalarProduct(new Vector4f(matrix4f.getV1().getZ(), matrix4f.getV2().getZ(), matrix4f.getV3().getZ(), matrix4f.getV4().getZ())));
        v1Result.setW(this.v1.scalarProduct(new Vector4f(matrix4f.getV1().getW(), matrix4f.getV2().getW(), matrix4f.getV3().getW(), matrix4f.getV4().getW())));

        Vector4f v2Result = new Vector4f();
        v2Result.setX(this.v2.scalarProduct(new Vector4f(matrix4f.getV1().getX(), matrix4f.getV2().getX(), matrix4f.getV3().getX(), matrix4f.getV4().getX())));
        v2Result.setY(this.v2.scalarProduct(new Vector4f(matrix4f.getV1().getY(), matrix4f.getV2().getY(), matrix4f.getV3().getY(), matrix4f.getV4().getY())));
        v2Result.setZ(this.v2.scalarProduct(new Vector4f(matrix4f.getV1().getZ(), matrix4f.getV2().getZ(), matrix4f.getV3().getZ(), matrix4f.getV4().getZ())));
        v2Result.setW(this.v2.scalarProduct(new Vector4f(matrix4f.getV1().getW(), matrix4f.getV2().getW(), matrix4f.getV3().getW(), matrix4f.getV4().getW())));

        Vector4f v3Result = new Vector4f();
        v3Result.setX(this.v3.scalarProduct(new Vector4f(matrix4f.getV1().getX(), matrix4f.getV2().getX(), matrix4f.getV3().getX(), matrix4f.getV4().getX())));
        v3Result.setY(this.v3.scalarProduct(new Vector4f(matrix4f.getV1().getY(), matrix4f.getV2().getY(), matrix4f.getV3().getY(), matrix4f.getV4().getY())));
        v3Result.setZ(this.v3.scalarProduct(new Vector4f(matrix4f.getV1().getZ(), matrix4f.getV2().getZ(), matrix4f.getV3().getZ(), matrix4f.getV4().getZ())));
        v3Result.setW(this.v3.scalarProduct(new Vector4f(matrix4f.getV1().getW(), matrix4f.getV2().getW(), matrix4f.getV3().getW(), matrix4f.getV4().getW())));

        Vector4f v4Result = new Vector4f();
        v4Result.setX(this.v4.scalarProduct(new Vector4f(matrix4f.getV1().getX(), matrix4f.getV2().getX(), matrix4f.getV3().getX(), matrix4f.getV4().getX())));
        v4Result.setY(this.v4.scalarProduct(new Vector4f(matrix4f.getV1().getY(), matrix4f.getV2().getY(), matrix4f.getV3().getY(), matrix4f.getV4().getY())));
        v4Result.setZ(this.v4.scalarProduct(new Vector4f(matrix4f.getV1().getZ(), matrix4f.getV2().getZ(), matrix4f.getV3().getZ(), matrix4f.getV4().getZ())));
        v4Result.setW(this.v4.scalarProduct(new Vector4f(matrix4f.getV1().getW(), matrix4f.getV2().getW(), matrix4f.getV3().getW(), matrix4f.getV4().getW())));

        return new Matrix4f(v1Result, v2Result, v3Result, v4Result);
    }

    public Matrix4f transpose() {
        Vector4f v1Result = new Vector4f();
        v1Result.setX(this.v1.getX());
        v1Result.setY(this.v2.getX());
        v1Result.setZ(this.v3.getX());
        v1Result.setW(this.v4.getX());

        Vector4f v2Result = new Vector4f();
        v2Result.setX(this.v1.getY());
        v2Result.setY(this.v2.getY());
        v2Result.setZ(this.v3.getY());
        v2Result.setW(this.v4.getY());

        Vector4f v3Result = new Vector4f();
        v3Result.setX(this.v1.getZ());
        v3Result.setY(this.v2.getZ());
        v3Result.setZ(this.v3.getZ());
        v3Result.setW(this.v4.getZ());

        Vector4f v4Result = new Vector4f();
        v4Result.setX(this.v1.getW());
        v4Result.setY(this.v2.getW());
        v4Result.setZ(this.v3.getW());
        v4Result.setW(this.v4.getW());

        return new Matrix4f(v1Result, v2Result, v3Result, v4Result);
    }

    public float determinant() {
        Matrix3f m1 = new Matrix3f(new Vector3f(this.v2.getY(), this.v2.getZ(), this.v2.getW()), new Vector3f(this.v3.getY(), this.v3.getZ(), this.v3.getW()), new Vector3f(this.v4.getY(), this.v4.getZ(), this.v4.getW()));
        Matrix3f m2 = new Matrix3f(new Vector3f(this.v2.getX(), this.v2.getZ(), this.v2.getW()), new Vector3f(this.v3.getX(), this.v3.getZ(), this.v3.getW()), new Vector3f(this.v4.getX(), this.v4.getZ(), this.v4.getW()));
        Matrix3f m3 = new Matrix3f(new Vector3f(this.v2.getX(), this.v2.getY(), this.v2.getW()), new Vector3f(this.v3.getX(), this.v3.getY(), this.v3.getW()), new Vector3f(this.v4.getX(), this.v4.getY(), this.v4.getW()));
        Matrix3f m4 = new Matrix3f(new Vector3f(this.v2.getX(), this.v2.getZ(), this.v2.getZ()), new Vector3f(this.v3.getX(), this.v3.getZ(), this.v3.getZ()), new Vector3f(this.v4.getX(), this.v4.getZ(), this.v4.getZ()));
        return this.v1.getX() * m1.determinant() - this.v1.getY() * m2.determinant() + this.v1.getZ() * m3.determinant() + this.v1.getW() * m4.determinant();
    }
}
