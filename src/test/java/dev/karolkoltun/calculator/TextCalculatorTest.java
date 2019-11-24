package dev.karolkoltun.calculator;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TextCalculatorTest {
    private TextCalculator textCalculator;

    public static Stream<Arguments> provideNumbers() {
        return Stream.of(
                Arguments.of("1+5", 6),
                Arguments.of("8+2", 10),
                Arguments.of("1+3", 4),
                Arguments.of("3-3", 0),
                Arguments.of("4/2", 2),
                Arguments.of("1*3", 3),
                Arguments.of("9-3", 6),
                Arguments.of("9/3", 3));

    }

    @BeforeEach
    void setup() {
        textCalculator = new TextCalculator();
    }

    @ParameterizedTest(name = "{0} should be {1}")
    @MethodSource("provideNumbers")
    public void shouldAddNumbers(String numberStr, double expectedResult) throws DivisionByZeroException, NotANumberException {
        //WHEN
        double result = textCalculator.calculate(numberStr);
        //THEN
        assertEquals(expectedResult, result);
    }


    @Test
    public void shouldThrowException() {
        // Given
        String invalidPhrase = "1/0";
        String invalidPhrase2 = "1#1";

        // When
        Executable calculateInvalid = () -> textCalculator.calculate(invalidPhrase);
        Executable calculateInvalid2 = () -> textCalculator.calculate(invalidPhrase2);

        // Then
        assertThrows(DivisionByZeroException.class, calculateInvalid);
        assertThrows(RuntimeException.class, calculateInvalid2);



    }
}