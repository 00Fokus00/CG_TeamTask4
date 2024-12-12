package com.cgvsu.math.matrices;

import com.cgvsu.math.vectors.Vector2f;
import org.junit.jupiter.api.Test;
import static com.cgvsu.math.vectors.Vector2f.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Vector2fTest {
    @Test
    public void testAddition() {
        Vector2f v1 = new Vector2f(1, 2);
        Vector2f v2 = new Vector2f(3, 4);
        Vector2f result = add(v1, v2);
        assertEquals(new Vector2f(4, 6), result);
    }

    @Test
    public void testSubtraction() {
        Vector2f v1 = new Vector2f(1, 2);
        Vector2f v2 = new Vector2f(3, 4);
        Vector2f result = subtract(v1, v2);
        assertEquals(new Vector2f(-2, -2), result);
    }

    @Test
    public void testMultiplication() {
        Vector2f v1 = new Vector2f(1, 2);
        float s1 = 0.5f;
        v1.multiply(s1);
        Vector2f result = v1;
        assertEquals(new Vector2f(0.5f, 1), result);
    }

    @Test
    public void testDivision() {
        Vector2f v1 = new Vector2f(1, 2);
        float s1 = 2;
        v1.divide(s1);
        Vector2f result = v1;
        assertEquals(new Vector2f(0.5f, 1), result);
    }

    @Test
    public void testDivision_Zero() {
        Vector2f v1 = new Vector2f(1, 2);
        float s1 = 0;
        assertThrows(ArithmeticException.class, () -> v1.divide(s1));
    }

    @Test
    public void testGettingLength() {
        Vector2f v1 = new Vector2f(3, 4);
        float result = v1.getLength();
        assertEquals(5f, result);
    }

    @Test
    public void testNormalization() {
        Vector2f v1 = new Vector2f(3, 4);
        v1.normalize();
        Vector2f result = v1;
        assertEquals(new Vector2f(0.6f, 0.8f), result);
    }

    @Test
    public void testNormalization_Zero() {
        Vector2f v1 = new Vector2f(0, 0);
        assertThrows(ArithmeticException.class, v1::normalize);
    }

    @Test
    public void testScalarProduction() {
        Vector2f v1 = new Vector2f(1, 2);
        Vector2f v2 = new Vector2f(3, 4);
        float result = scalarProduct(v1, v2);
        assertEquals(11f, result);
    }
}
