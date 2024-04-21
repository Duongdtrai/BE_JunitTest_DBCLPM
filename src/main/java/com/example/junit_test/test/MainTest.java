package com.example.junit_test.test;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class MainTest {

    @Test
    void testNoEvenPositiveNumbers() {
        int[] array = {1, 3, 5};
        assertEquals("Không có số chẵn dương nào trong dãy.", Main.calculateEvenPositiveSum(array));
    }

    @Test //
    void testSumOfEvenPositiveNumbers() {
        int[] array = {1, 2, 3, 4, 5, 6};
        assertEquals("Tổng các số chẵn dương trong dãy là: 12", Main.calculateEvenPositiveSum(array));
    }

    @Test
    void testEmptyArray() {
        int[] array = {};
        assertEquals("Dãy không có số nào.", Main.calculateEvenPositiveSum(array));
    }
}
