package com.cgvsu.math.vectors;

import com.cgvsu.math.matrices.Matrix4f;
import org.junit.jupiter.api.Test;
import static com.cgvsu.math.matrices.Matrix4f.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Matrix4fTest {
    @Test
    public void testAddition() {
        Matrix4f m1 = new Matrix4f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        Matrix4f m2 = unitMatrix();
        Matrix4f result = add(m1, m2);
        assertEquals(new Matrix4f(new float[]{2, 2, 3, 4, 5, 7, 7, 8, 9, 10, 12, 12, 13, 14, 15, 17}), result);
    }

    @Test
    public void testSubtraction() {
        Matrix4f m1 = new Matrix4f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        Matrix4f m2 = unitMatrix();
        Matrix4f result = subtract(m1, m2);
        assertEquals(new Matrix4f(new float[]{0, 2, 3, 4, 5, 5, 7, 8, 9, 10, 10, 12, 13, 14, 15, 15}), result);
    }

    @Test
    public void testMultiplicationByVector() {
        Matrix4f m1 = new Matrix4f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        Vector4f v1 = new Vector4f(1, 2, 3, 4);
        Vector4f result = multiply(m1, v1);
        assertEquals(new Vector4f(30, 70, 110, 150), result);
    }

    @Test
    public void testMultiplicationByMatrix() {
        Matrix4f m1 = new Matrix4f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        Matrix4f m2 = unitMatrix();
        Matrix4f result = multiply(m1, m2);
        assertEquals(new Matrix4f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}), result);
    }

    @Test
    public void testTransposition() {
        Matrix4f m1 = new Matrix4f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        Matrix4f result = m1.transpose();
        assertEquals(new Matrix4f(new float[]{1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 16}), result);
    }

    @Test
    public void testDeterminant() {
        Matrix4f m1 = new Matrix4f(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        float result = m1.determinant();
        assertEquals(0f, result);
    }
}
