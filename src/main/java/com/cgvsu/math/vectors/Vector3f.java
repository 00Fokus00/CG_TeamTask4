package com.cgvsu.math.vectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vector3f {
    private float x;
    private float y;
    private float z;
    private final float EPS = 1e-7f;

    public Vector3f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Vector3f vector3f = (Vector3f) o;
        return Math.abs(this.x - vector3f.getX()) < EPS
                && Math.abs(this.y - vector3f.getY()) < EPS
                && Math.abs(this.z - vector3f.getZ()) < EPS;
    }

    public Vector3f add(Vector3f vector3f) {
        this.x += vector3f.getX();
        this.y += vector3f.getY();
        this.z += vector3f.getZ();
        return this;
    }

    public Vector3f subtract(Vector3f vector3f) {
        this.x -= vector3f.getX();
        this.y -= vector3f.getY();
        this.z -= vector3f.getZ();
        return this;
    }

    public Vector3f multiplyByScalar(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public Vector3f divideByScalar(float scalar) throws ArithmeticException {
        if (Math.abs(scalar) < EPS) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        return this;
    }

    public float getLength() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector3f normalize() throws ArithmeticException {
        float length = getLength();
        if (Math.abs(length) < EPS) {
            throw new ArithmeticException("Cannot normalize a vector with zero length.");
        }
        return this.divideByScalar(length);
    }

    public float scalarProduct(Vector3f vector3f) {
        return this.x * vector3f.getX() + this.y * vector3f.getY() + this.z * vector3f.getZ();
    }

    public Vector3f vectorProduct(Vector3f vector3f) {
        return new Vector3f(this.y * vector3f.getZ() - this.z * vector3f.getY(), this.z * vector3f.getX() - this.x * vector3f.getZ(), this.x * vector3f.getY() - this.y * vector3f.getX());
    }
}
