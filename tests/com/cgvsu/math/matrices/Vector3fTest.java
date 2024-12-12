package com.cgvsu.math.matrices;

import com.cgvsu.math.vectors.Vector3f;
import org.junit.jupiter.api.Test;
import static com.cgvsu.math.vectors.Vector3f.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Vector3fTest {
    @Test
    public void testAddition() {
        Vector3f v1 = new Vector3f(1, 2, 3);
        Vector3f v2 = new Vector3f(4, 5, 6);
        Vector3f result = add(v1, v2);
        assertEquals(new Vector3f(5, 7, 9), result);
    }

    @Test
    public void testSubtraction() {
        Vector3f v1 = new Vector3f(1, 2, 3);
        Vector3f v2 = new Vector3f(4, 5, 6);
        Vector3f result = subtract(v1, v2);
        assertEquals(new Vector3f(-3, -3, -3), result);
    }

    @Test
    public void testMultiplication() {
        Vector3f v1 = new Vector3f(1, 2, 3);
        float s1 = 0.5f;
        v1.multiply(s1);
        Vector3f result = v1;
        assertEquals(new Vector3f(0.5f, 1, 1.5f), result);
    }

    @Test
    public void testDivision() {
        Vector3f v1 = new Vector3f(1, 2, 3);
        float s1 = 2;
        v1.divide(s1);
        Vector3f result = v1;
        assertEquals(new Vector3f(0.5f, 1, 1.5f), result);
    }

    @Test
    public void testDivision_Zero() {
        Vector3f v1 = new Vector3f(1, 2, 3);
        float s1 = 0;
        assertThrows(ArithmeticException.class, () -> v1.divide(s1));
    }

    @Test
    public void testGettingLength() {
        Vector3f v1 = new Vector3f(1, 2, 2);
        float result = v1.getLength();
        assertEquals(3f, result);
    }

    @Test
    public void testNormalization() {
        Vector3f v1 = new Vector3f(1, 2, 2);
        v1.normalize();
        Vector3f result = v1;
        assertEquals(new Vector3f((float) 1 /3, (float) 2/3, (float) 2/3), result);
    }

    @Test
    public void testNormalization_Zero() {
        Vector3f v1 = new Vector3f(0, 0, 0);
        assertThrows(ArithmeticException.class, v1::normalize);
    }

    @Test
    public void testScalarProduction() {
        Vector3f v1 = new Vector3f(1, 2, 3);
        Vector3f v2 = new Vector3f(4, 5, 6);
        float result = scalarProduct(v1, v2);
        assertEquals(32f, result);
    }

    @Test
    public void testVectorProduction() {
        Vector3f v1 = new Vector3f(1, 2, 3);
        Vector3f v2 = new Vector3f(4, 5, 6);
        Vector3f result = vectorProduct(v1, v2);
        assertEquals(new Vector3f(-3, 6, -3), result);
    }
}
