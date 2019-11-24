package dev.karolkoltun.calculator;


import org.apache.commons.lang3.math.NumberUtils;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

/** Wyjatkowo prosty kalkulator. */
class SimpleCalculator {
  int add(int x, int y) {
    return x + y;
  }

  int subtract(int x, int y) {
    return x - y;
  }

  int multiply(int x, int y) {
    return x * y;
  }

  int divide(int x, int y) {

  try {
    return x/y;
  } catch (ArithmeticException e){
    System.err.println("You can't divide by 0!!!");
  } return 0;
  }


  int calculateFibonacci(int n) {
    if (n <= 1) {
      return n;
    }
    return calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
  }


  List<Integer> getDivisors(int number){
    List<Integer> divisors = new ArrayList<>();

    for (int i = 1; i <=number; i++){
      if (number % i ==0){
        divisors.add(i);
      }
    }
    return divisors;
  }

  boolean isOdd(int x){
    return x % 2 != 0;
  }

  boolean isNumber(String text){
   return NumberUtils.isCreatable(text);
  }


}
