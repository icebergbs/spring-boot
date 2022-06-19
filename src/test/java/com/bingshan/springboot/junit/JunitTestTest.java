package com.bingshan.springboot.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class JunitTestTest {

    @Test
    @DisplayName("line coverage sample test")
    void testMethod() {
        JunitTest junitTest = new JunitTest();
        Assertions.assertTrue(junitTest.testMethod(1, 2, 0));
    }

    @ParameterizedTest
    @DisplayName("Condition Decision coverage sample test result true")
    @CsvSource({
            "0, 2, 3",
            "1, 0, 3"
    })
    void testConditonDecisionCoverageTrue(int a, int b, int c){
        JunitTest junitTest = new JunitTest();
        Assertions.assertTrue(junitTest.testMethod(a, b, c));
    }
    @Test
    @DisplayName("Condition Decision coverage sample test result false")
    void testConditonDecisionCoverageFalse(){
        JunitTest junitTest = new JunitTest();
        Assertions.assertFalse(junitTest.testMethod(0, 0, 0));
    }

    @ParameterizedTest
    @DisplayName("Multiple Condition coverage sample test result true")
    @CsvSource({
            "1, 2, 3",
            "1, 2, 0",
            "1, 0, 3",
            "0, 2, 3",
            "0, 0, 3"
    })
    void testMultipleConditionCoverageSampleTrue(int a, int b, int c) {
        JunitTest junitTest = new JunitTest();
        Assertions.assertTrue(junitTest.testMethod(a, b, c));
    }
    @ParameterizedTest
    @DisplayName("Multiple Condition coverage sample test result false")
    @CsvSource({
            "1, 0, 0",
            "0, 0, 0",
            "0, 2, 0"
    })
    void testMultipleConditionCoverageSampleFalse(int a, int b, int c) {
        JunitTest junitTest = new JunitTest();
        Assertions.assertFalse(junitTest.testMethod(a, b, c));
    }
}