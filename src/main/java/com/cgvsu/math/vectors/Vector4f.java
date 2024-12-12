package com.cgvsu.math.vectors;

public class Vector4f {
    private float x;
    private float y;
    private float z;
    private float w;
    private final float EPS = 1e-7f;

    public Vector4f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Vector4f vector4f = (Vector4f) o;
        return Math.abs(this.x - vector4f.x) < EPS
                && Math.abs(this.y - vector4f.y) < EPS
                && Math.abs(this.z - vector4f.z) < EPS
                && Math.abs(this.w - vector4f.w) < EPS;
    }

    @Override
    public String toString() {
        return "Vector4f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }

    public Vector3f toVector3f() {
        return new Vector3f(this.x, this.y, this.w);
    }

    public static Vector4f add(Vector4f vector1, Vector4f vector2) {
        return new Vector4f(vector1.x + vector2.x, vector1.y + vector2.y, vector1.z + vector2.z, vector1.w + vector2.w);
    }

    public static Vector4f subtract(Vector4f vector1, Vector4f vector2) {
        return new Vector4f(vector1.x - vector2.x, vector1.y - vector2.y, vector1.z - vector2.z, vector1.w - vector2.w);
    }

    public void multiply(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        this.w *= scalar;
    }

    public void divide(float scalar) throws ArithmeticException {
        if (Math.abs(scalar) < EPS) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        this.w /= scalar;
    }

    public float getLength() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
    }

    public void normalize() throws ArithmeticException {
        float length = getLength();
        if (Math.abs(length) < EPS) {
            throw new ArithmeticException("Cannot normalize a vector with zero length.");
        }
        this.divide(length);
    }

    public static float scalarProduct(Vector4f vector1, Vector4f vector2) {
        return vector1.x * vector2.x + vector1.y * vector2.y + vector1.z * vector2.z + vector1.w * vector2.w;
    }
}
