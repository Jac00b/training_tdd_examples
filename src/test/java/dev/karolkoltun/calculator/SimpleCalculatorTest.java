package dev.karolkoltun.calculator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCalculatorTest {

  SimpleCalculator simpleCalculator;

  @BeforeEach
  void setup() {
    simpleCalculator = new SimpleCalculator();
  }


  @AfterEach
  void afterOne(){
    System.out.println("I am after One");
  }

  @AfterAll
  static void afterAll(){
    System.out.println("I am after all");
  }


  @Test
  void simplestTest() {
    // Given
    boolean expectedValue = true;

    // When
    boolean actualValue = true;

    // Then
    assertEquals(expectedValue, actualValue);
  }

  @Test
  void multiplyTest(){
    //given
    int result = simpleCalculator.multiply(3, 5);

    assertEquals(15, result);

  }

  @ParameterizedTest
  @CsvSource({"6,6, 0", "5,5, 0", "10,9, 1"})
  void substractTest(int firstNumber, int secondNumber, int expectedResult){
//GIVEN

//WHEN
    int result = simpleCalculator.subtract(firstNumber, secondNumber);

    //THEN
    assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData.csv", numLinesToSkip = 1)
  void addTest (int firstNumber, int secondNumber, int expectedResult){


    int result = simpleCalculator.add(firstNumber, secondNumber);

    assertEquals(expectedResult, result);
  }
@Test
  void divideTest(){
    int firstNumber = 10;
    int secondNumber = 0;
    int expectedResult = 0;

    int result= simpleCalculator.divide(firstNumber, secondNumber);

    assertEquals(expectedResult, result);
  }
 @ParameterizedTest
  @CsvSource({"0,0", "3,2", "5,5"})
  void shouldReturnFibonacciNumber(int index, int expectedResult) {
    //WHEN

    int actualResult = simpleCalculator.calculateFibonacci(index);

    //THEN

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void divisorsTest(){
    //given

    int number = 10;
    Integer [] expectedArray = {1,2,5,10};

    //When
    List<Integer> actual = simpleCalculator.getDivisors(number);

    //Then
    assertEquals(4, actual.size());
   assertTrue(actual.contains(1));
   assertTrue(actual.contains(2));
   assertTrue(actual.contains(5));
   assertTrue(actual.contains(10));

  }

  @Test
  void shouldCheckIfIsNumber(){
    String string = "20";
    assertTrue(simpleCalculator.isNumber(string));
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 5, 9, 3})
  void shouldCheckIfOdd(int number){
    assertTrue(simpleCalculator.isOdd(number));
  }


}
