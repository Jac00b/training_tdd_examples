package dev.karolkoltun.calculator;

import org.apache.commons.lang3.math.NumberUtils;
import org.w3c.dom.ls.LSOutput;

import java.sql.Struct;



/**
 * Proste, ryzykowne, lekko bledne i ograniczone podejscie do kalkulatora bazujacego na tekscie.
 */
class TextCalculator {

    /**
     * Oblicza podane wyrazenie. Zaklada ze wyrazenie sklada sie z jednej cyfry, znaku i drugiej
     * cyfry. Tylko znak "+" jest wspierany. Przykladowe wspierane wyrazenie: "5+4".
     *
     * @param phrase wyrazenie do obliczenia
     * @return wynik
     */
    double calculate(String phrase) throws DivisionByZeroException, NotANumberException {
        int firstNumber = Character.getNumericValue(phrase.charAt(0));
        int secondNumber = Character.getNumericValue(phrase.charAt(2));
        char symbol = phrase.charAt(1);
        if (symbol == '+') {
            return firstNumber + secondNumber;
        } else if (symbol == '-') {
            return firstNumber - secondNumber;
        } else if (symbol == '*') {
            return firstNumber * secondNumber;
        } else if (symbol == '/') {
          if (secondNumber==0) {
           throw new DivisionByZeroException();
           } else {
            return firstNumber / secondNumber;
          }
        } else {
            throw new RuntimeException("Sumbol " + symbol + " is not supported!");
        }

    }
}
