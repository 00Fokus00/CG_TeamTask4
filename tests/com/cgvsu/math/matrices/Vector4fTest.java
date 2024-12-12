package com.cgvsu.math.matrices;

import com.cgvsu.math.vectors.Vector4f;
import org.junit.jupiter.api.Test;
import static com.cgvsu.math.vectors.Vector4f.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Vector4fTest {
    @Test
    public void testAddition() {
        Vector4f v1 = new Vector4f(1, 2, 3, 4);
        Vector4f v2 = new Vector4f(5, 6, 7, 8);
        Vector4f result = add(v1, v2);
        assertEquals(new Vector4f(6, 8, 10, 12), result);
    }

    @Test
    public void testSubtraction() {
        Vector4f v1 = new Vector4f(1, 2, 3, 4);
        Vector4f v2 = new Vector4f(5, 6, 7, 8);
        Vector4f result = subtract(v1, v2);
        assertEquals(new Vector4f(-4, -4, -4, -4), result);
    }

    @Test
    public void testMultiplication() {
        Vector4f v1 = new Vector4f(1, 2, 3, 4);
        float s1 = 0.5f;
        v1.multiply(s1);
        Vector4f result = v1;
        assertEquals(new Vector4f(0.5f, 1, 1.5f, 2), result);
    }

    @Test
    public void testDivision() {
        Vector4f v1 = new Vector4f(1, 2, 3, 4);
        float s1 = 2;
        v1.divide(s1);
        Vector4f result = v1;
        assertEquals(new Vector4f(0.5f, 1, 1.5f, 2), result);
    }

    @Test
    public void testDivision_Zero() {
        Vector4f v1 = new Vector4f(1, 2, 3, 4);
        float s1 = 0;
        assertThrows(ArithmeticException.class, () -> v1.divide(s1));
    }

    @Test
    public void testGettingLength() {
        Vector4f v1 = new Vector4f(1, 2, 2, 3);
        float result = v1.getLength();
        assertEquals((float) (3 * Math.sqrt(2)), result);
    }

    @Test
    public void testNormalization() {
        Vector4f v1 = new Vector4f(1, 2, 2, 3);
        v1.normalize();
        Vector4f result = v1;
        assertEquals(new Vector4f((float) (1 / (3 * Math.sqrt(2))), (float) (2 / (3 * Math.sqrt(2))), (float) (2 / (3 * Math.sqrt(2))), (float) (1 / Math.sqrt(2))), result);
    }

    @Test
    public void testNormalization_Zero() {
        Vector4f v1 = new Vector4f(0, 0, 0, 0);
        assertThrows(ArithmeticException.class, v1::normalize);
    }

    @Test
    public void testScalarProduction() {
        Vector4f v1 = new Vector4f(1, 2, 3, 4);
        Vector4f v2 = new Vector4f(5, 6, 7, 8);
        float result = scalarProduct(v1, v2);
        assertEquals(70f, result);
    }
}
