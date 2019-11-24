package dev.karolkoltun.expenses;

import dev.karolkoltun.currency.CurrencyService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static dev.karolkoltun.currency.Currency.PLN;
import static java.math.BigDecimal.ZERO;
import static java.time.LocalDate.now;

/** Prosty serwis do zbierania wydatków. */
class HomeFinancesService {
  private List<Expense> expenses = new ArrayList<>();

  private CurrencyService currencyService;

  HomeFinancesService(CurrencyService currencyService) {
    this.currencyService = currencyService;
  }

  void addExpense(Expense expense) throws InvalidDateException, NegativeExpenseException {
    if (!expense.getCurrency().equals(PLN)) {
      BigDecimal amountInPln =
          currencyService.convert(expense.getAmount(), expense.getCurrency(), PLN);

      expense.setAmount(amountInPln);
      expense.setCurrency(PLN);
    }

    if (expense.getDate().isAfter(now())) {
     throw new InvalidDateException();
    }

    if (expense.getAmount().compareTo(ZERO) < 0) {
      throw new NegativeExpenseException();
    }

    expenses.add(expense);
  }

  List<Expense> getAllExpenses() {
    return new ArrayList<>(expenses);
  }

  List<Expense> getCategory(Category category){
    List<Expense> list = getAllExpenses();
    List<Expense> categoryList = new ArrayList<>();
    for (int i =0; i<list.size(); i++){
      if (list.get(i).getCategory().equals(category)){
       categoryList.add(list.get(i));
      }
    }
    return categoryList;
  }

  List<Expense> getDateTimeExpense(LocalDate beginDate, LocalDate endDate){
    List<Expense> list = getAllExpenses();
    List<Expense> dateTime = new ArrayList<>();

    for (int i = 0; i<list.size(); i++){
      if (list.get(i).getDate().isAfter(beginDate)&&list.get(i).getDate().isBefore(endDate)){
        dateTime.add(list.get(i));
      }
    }
    return dateTime;
  }

  List<Expense> getAllExpansesWithAvergeExpansesInGivenTime(LocalDate beginDate, LocalDate endDate){
    int counter=0;
   BigDecimal expenseSum = BigDecimal.valueOf(0);
    List<Expense> list = getAllExpenses();
    List<Expense> avergeListWithDate = getDateTimeExpense(beginDate, endDate);

    for (int i =0; i<avergeListWithDate.size(); i++){
      list.get(i).getAmount();
      counter++;

    }
    return null;
    ///DO POPRAWY///

  }
}
