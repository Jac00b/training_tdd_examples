package dev.karolkoltun.currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class CurrencyServiceMockTest {

    private CurrencyService currencyService;

    @BeforeEach
    void setupMock() {
        currencyService = Mockito.mock(CurrencyService.class);

    }

    @Test
    void testMock1() {
        //configure mock
        when(currencyService.convert(
                BigDecimal.valueOf(100),
                Currency.PLN,
                Currency.EUR)).thenReturn(BigDecimal.valueOf(1000));

        //test mock
        BigDecimal actualResult = currencyService.convert(BigDecimal.valueOf(100), Currency.PLN, Currency.EUR);

        assertEquals(BigDecimal.valueOf(1000), actualResult);
    }

    @Test
    void testMock2() {
        when(currencyService.convert(
                BigDecimal.valueOf(100),
                Currency.EUR,
                Currency.PLN)).thenReturn(BigDecimal.valueOf(140));

        BigDecimal actualResult = currencyService.convert(BigDecimal.valueOf(100), Currency.EUR, Currency.PLN);

        assertEquals(BigDecimal.valueOf(140), actualResult);
    }

    @Test
    void testMock3() {
        when(currencyService.convert(any(BigDecimal.class), any(Currency.class), any(Currency.class)))
                .thenReturn(BigDecimal.valueOf(150));

        BigDecimal actualResult = currencyService.convert(BigDecimal.valueOf(200), Currency.EUR, Currency.USD);

        assertEquals(BigDecimal.valueOf(150), actualResult);
    }

    @Test
    void testMock4(){
        when (currencyService.convert(any(BigDecimal.class), eq(Currency.PLN), any(Currency.class))).thenThrow(new RuntimeException("Exception"));



        Executable executable = () -> currencyService.convert(BigDecimal.valueOf(200), Currency.PLN, Currency.EUR);
        assertThrows(RuntimeException.class, executable);
    }
}

