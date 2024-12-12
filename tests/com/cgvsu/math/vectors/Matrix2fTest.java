package com.cgvsu.math.vectors;

import com.cgvsu.math.matrices.Matrix2f;
import org.junit.jupiter.api.Test;
import static com.cgvsu.math.matrices.Matrix2f.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Matrix2fTest {
    @Test
    public void testAddition() {
        Matrix2f m1 = new Matrix2f(new float[]{1, 2, 3, 4});
        Matrix2f m2 = new Matrix2f(new float[]{5, 6, 7, 8});
        Matrix2f result = add(m1, m2);
        assertEquals(new Matrix2f(new float[]{6, 8, 10, 12}), result);
    }

    @Test
    public void testSubtraction() {
        Matrix2f m1 = new Matrix2f(new float[]{1, 2, 3, 4});
        Matrix2f m2 = new Matrix2f(new float[]{5, 6, 7, 8});
        Matrix2f result = subtract(m1, m2);
        assertEquals(new Matrix2f(new float[]{-4, -4, -4, -4}), result);
    }

    @Test
    public void testMultiplicationByVector() {
        Matrix2f m1 = new Matrix2f(new float[]{1, 2, 3, 4});
        Vector2f v1 = new Vector2f(9, 10);
        Vector2f result = multiply(m1, v1);
        assertEquals(new Vector2f(29, 67), result);
    }

    @Test
    public void testMultiplicationByMatrix() {
        Matrix2f m1 = new Matrix2f(new float[]{1, 2, 3, 4});
        Matrix2f m2 = new Matrix2f(new float[]{5, 6, 7, 8});
        Matrix2f result = multiply(m1, m2);
        assertEquals(new Matrix2f(new float[]{19, 22, 43, 50}), result);
    }

    @Test
    public void testTransposition() {
        Matrix2f m1 = new Matrix2f(new float[]{1, 2, 3, 4});
        Matrix2f result = m1.transpose();
        assertEquals(new Matrix2f(new float[]{1, 3, 2, 4}), result);
    }

    @Test
    public void testDeterminant() {
        Matrix2f m1 = new Matrix2f(new float[]{1, 2, 3, 4});
        float result = m1.determinant();
        assertEquals(-2f, result);
    }
}
