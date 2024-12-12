package com.cgvsu.math.vectors;

public class Vector2f {
    private float x;
    private float y;
    private final float EPS = 1e-7f;

    public Vector2f() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Vector2f vector2f = (Vector2f) o;
        return Math.abs(this.x - vector2f.x) < EPS
                && Math.abs(this.y - vector2f.y) < EPS;
    }

    @Override
    public String toString() {
        return "Vector2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static Vector2f add(Vector2f vector1, Vector2f vector2) {
        return new Vector2f(vector1.x + vector2.x, vector1.y + vector2.y);
    }

    public static Vector2f subtract(Vector2f vector1, Vector2f vector2) {
        return new Vector2f(vector1.x - vector2.x, vector1.y - vector2.y);
    }

    public void multiply(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public void divide(float scalar) throws ArithmeticException {
        if (Math.abs(scalar) < EPS) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        this.x /= scalar;
        this.y /= scalar;
    }

    public float getLength() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void normalize() throws ArithmeticException {
        float length = getLength();
        if (Math.abs(length) < EPS) {
            throw new ArithmeticException("Cannot normalize a vector with zero length.");
        }
        this.divide(length);
    }

    public static float scalarProduct(Vector2f vector1, Vector2f vector2) {
        return vector1.x * vector2.x + vector1.y * vector2.y;
    }
}
