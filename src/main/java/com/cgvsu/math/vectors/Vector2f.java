package com.cgvsu.math.vectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Vector2f vector2f = (Vector2f) o;
        return Math.abs(this.x - vector2f.getX()) < EPS
                && Math.abs(this.y - vector2f.getY()) < EPS;
    }

    public Vector2f add(Vector2f vector2f) {
        this.x += vector2f.getX();
        this.y += vector2f.getY();
        return this;
    }

    public Vector2f subtract(Vector2f vector2f) {
        this.x -= vector2f.getX();
        this.y -= vector2f.getY();
        return this;
    }

    public Vector2f multiplyByScalar(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2f divideByScalar(float scalar) throws ArithmeticException {
        if (Math.abs(scalar) < EPS) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    public float getLength() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2f normalize() throws ArithmeticException {
        float length = getLength();
        if (Math.abs(length) < EPS) {
            throw new ArithmeticException("Cannot normalize a vector with zero length.");
        }
        return this.divideByScalar(length);
    }

    public float scalarProduct(Vector2f vector2f) {
        return this.x * vector2f.getX() + this.y * vector2f.getY();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
