package com.cgvsu.math.vectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Vector4f vector4f = (Vector4f) o;
        return Math.abs(this.x - vector4f.getX()) < EPS
                && Math.abs(this.y - vector4f.getY()) < EPS
                && Math.abs(this.z - vector4f.getZ()) < EPS
                && Math.abs(this.w - vector4f.getW()) < EPS;
    }

    public Vector4f add(Vector4f vector4f) {
        this.x += vector4f.getX();
        this.y += vector4f.getY();
        this.z += vector4f.getZ();
        this.w += vector4f.getW();
        return this;
    }

    public Vector4f subtract(Vector4f vector4f) {
        this.x -= vector4f.getX();
        this.y -= vector4f.getY();
        this.z -= vector4f.getZ();
        this.w -= vector4f.getW();
        return this;
    }

    public Vector4f multiplyByScalar(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        this.w *= scalar;
        return this;
    }

    public Vector4f divideByScalar(float scalar) throws ArithmeticException {
        if (Math.abs(scalar) < EPS) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        this.w /= scalar;
        return this;
    }

    public float getLength() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
    }

    public Vector4f normalize() throws ArithmeticException {
        float length = getLength();
        if (Math.abs(length) < EPS) {
            throw new ArithmeticException("Cannot normalize a vector with zero length.");
        }
        return this.divideByScalar(length);
    }

    public float scalarProduct(Vector4f vector4f) {
        return this.x * vector4f.getX() + this.y * vector4f.getY() + this.z * vector4f.getZ() + this.w * vector4f.getW();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
