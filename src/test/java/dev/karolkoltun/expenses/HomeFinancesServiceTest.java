package dev.karolkoltun.expenses;

import dev.karolkoltun.currency.Currency;
import dev.karolkoltun.currency.CurrencyService;
import dev.karolkoltun.currency.DynamicCurrencyService;
import dev.karolkoltun.currency.PlainCurrencyService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HomeFinancesServiceTest {
    HomeFinancesService homeFinancesService;
    @BeforeEach
    void setup(){
      homeFinancesService = new HomeFinancesService(new PlainCurrencyService());
    }

    @Test
    void shouldAddExpense() throws NegativeExpenseException, InvalidDateException {
        //GIVEN

        Expense expense = new Expense(LocalDate.now(),BigDecimal.valueOf(100), "ORLEN", Category.CARS);

        //WHEN
        homeFinancesService.addExpense(expense);
        List<Expense> expenses = homeFinancesService.getAllExpenses();
        //THEN

        assertFalse(homeFinancesService.getAllExpenses().isEmpty());

        Expense addedExpense = expenses.get(0);

        Predicate<Expense> hasCategoryCars = expenseInList -> expenseInList.getCategory().equals(Category.CARS);

        assertThat(expenses)
                .as("List should contain added expense")
                .contains(expense).allMatch(hasCategoryCars);

        assertThat(addedExpense)
                .extracting("where", "amount")
                .containsExactly("ORLEN", BigDecimal.valueOf(100));

        assertThat(addedExpense).isNotNull();
    }
@Test
    void shouldAddWithDateYesterdayAndAmountAboveZero() throws NegativeExpenseException, InvalidDateException {
        //GIVEN

    Expense expense = new Expense(LocalDate.now().minusDays(1), BigDecimal.valueOf(150), "STOKROTKA", Category.FOOD);

    //WHEN

    homeFinancesService.addExpense(expense);

    assertTrue(expense.getDate().isBefore(LocalDate.now()));
    assertTrue(expense.getAmount().shortValueExact()>0);

    }

    @Test
    void shouldAddWithDateTommorowAndAmountAboveZero() throws NegativeExpenseException, InvalidDateException {
        Expense expense = new Expense(LocalDate.now().plusDays(1), BigDecimal.valueOf(120), "ZOO", Category.OTHERS);

        homeFinancesService.addExpense(expense);

        assertTrue(expense.getDate().isAfter(LocalDate.now()));
        assertTrue(expense.getAmount().shortValueExact()>0);
        assertEquals(0, homeFinancesService.getAllExpenses().size());
    }

    @Test

    void shouldAddWithDateYesterdayAndAmountBelowZero() throws NegativeExpenseException, InvalidDateException {
        Expense expense = new Expense(LocalDate.now().minusDays(1), BigDecimal.valueOf(-100), "Orlen", Category.CARS);

        homeFinancesService.addExpense(expense);

        assertTrue(expense.getDate().isBefore(LocalDate.now()));
        assertEquals(0, homeFinancesService.getAllExpenses().size());
    }

@Test
    void shouldGetCategory() throws NegativeExpenseException, InvalidDateException {
        Expense expense = new Expense(LocalDate.now(), BigDecimal.valueOf(100), "Orlen", Category.CARS);
        Expense expense2 = new Expense(LocalDate.now(), BigDecimal.valueOf(100), "Stokrotka", Category.FOOD);
        Expense expense3 = new Expense(LocalDate.now(), BigDecimal.valueOf(100), "Żabka", Category.FOOD);
        List<Expense> categoryList;
        homeFinancesService.addExpense(expense);
        homeFinancesService.addExpense(expense2);
        homeFinancesService.addExpense(expense3);

        categoryList=homeFinancesService.getCategory(Category.FOOD);

        assertEquals(2, categoryList.size());
}

@Test
void shouldGetExpansesInExactTime() throws NegativeExpenseException, InvalidDateException {
    Expense expense = new Expense(LocalDate.now(), BigDecimal.valueOf(100), "Orlen", Category.CARS);
    Expense expense2 = new Expense(LocalDate.now().minusDays(2), BigDecimal.valueOf(100), "Stokrotka", Category.FOOD);
    Expense expense3 = new Expense(LocalDate.now().minusYears(2), BigDecimal.valueOf(100), "Żabka", Category.FOOD);

    List<Expense> dateList;

    homeFinancesService.addExpense(expense);
    homeFinancesService.addExpense(expense2);
    homeFinancesService.addExpense(expense3);

    dateList=homeFinancesService.getDateTimeExpense(LocalDate.of(2016, 11, 22),LocalDate.of(2018, 11,11));

    assertEquals(1, dateList.size());
}

@Test
void shouldAddAndGetAvergeExpensesInGivenTime(){
    Expense expense = new Expense(LocalDate.now(), BigDecimal.valueOf(150), "Orlen", Category.CARS);
    Expense expense2 = new Expense(LocalDate.now().minusDays(2), BigDecimal.valueOf(50), "Stokrotka", Category.FOOD);
    Expense expense3 = new Expense(LocalDate.now().minusYears(2), BigDecimal.valueOf(50), "Żabka", Category.FOOD);
    Expense expense4 = new Expense(LocalDate.now().minusYears(2), BigDecimal.valueOf(150), "Żabka", Category.FOOD);

    List<Expense> dateListWithAverge;

    dateListWithAverge = homeFinancesService.getAllExpansesWithAvergeExpansesInGivenTime(LocalDate.of(2016, 11, 22),LocalDate.of(2018, 11,11));
}

@Test
    public void shouldThrowException(){
        Expense expense = new Expense(LocalDate.now().plusDays(1), BigDecimal.valueOf(100), "Orlen", Category.CARS);
        Expense expense2 = new Expense(LocalDate.now(), BigDecimal.valueOf(-100), "Orlen", Category.CARS);

    Executable dateInvalid = () -> homeFinancesService.addExpense(expense);
    Executable negativeExpense = () -> homeFinancesService.addExpense(expense2);

    assertThrows(InvalidDateException.class, dateInvalid);
    assertThrows(NegativeExpenseException.class, negativeExpense);

}



@Test
    void shouldAddExpanseInForeignCurrency() throws NegativeExpenseException, InvalidDateException {
        //given
    CurrencyService currencyServiceMock = Mockito.mock(CurrencyService.class);

        HomeFinancesService homeFinancesService = new HomeFinancesService(currencyServiceMock);
        Expense expenseInEur = new Expense(LocalDate.now().minusYears(1),
                BigDecimal.valueOf(100),"Pizzeria Mario",Category.FOOD, Currency.EUR);

        when(currencyServiceMock.convert(BigDecimal.valueOf(100), Currency.EUR, Currency.PLN))
                .thenReturn(BigDecimal.valueOf(200));

        //when
    homeFinancesService.addExpense(expenseInEur);

    //then
    List<Expense> expenses = homeFinancesService.getAllExpenses();

    assertThat(expenses).containsExactly(expenseInEur);

    Expense addedExpense = expenses.get(0);

    assertThat(addedExpense).hasFieldOrPropertyWithValue("currency", Currency.PLN);

    verify(currencyServiceMock, times(1)).
            convert(BigDecimal.valueOf(100), Currency.EUR, Currency.PLN);
}



}