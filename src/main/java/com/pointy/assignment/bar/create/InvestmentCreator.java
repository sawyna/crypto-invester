package com.pointy.assignment.bar.create;

import com.pointy.assignment.common.constants.Constants.PrecisionConstants;
import com.pointy.assignment.common.dtos.Action;
import com.pointy.assignment.common.dtos.Attempt;
import com.pointy.assignment.common.dtos.HistoricalExchangeData;
import com.pointy.assignment.common.dtos.Investment;
import com.pointy.assignment.common.dtos.Transaction;
import com.pointy.assignment.common.util.CommonUtils;
import com.pointy.assignment.common.util.DateUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class InvestmentCreator {

    /**
     * Converts a list of attempts and HistoricalExchangeData
     * to an Investment Object with transactions and profit calculated
     */
    public Investment create(List<Attempt> attempts,
        HistoricalExchangeData historicalExchangeData) {
        String startDate = historicalExchangeData.getStartDate();
        String dateString;
        String fromCurrency, toCurrency;
        double profit = 0.0;

        List<Transaction> transactions = new ArrayList<>();
        for (Attempt attempt : attempts) {
            dateString = DateUtils.nextDate(startDate, attempt.getIndex());
            if (attempt.getAction().equals(Action.BUY)) {
                fromCurrency = historicalExchangeData.getToCurrency();
                toCurrency = historicalExchangeData.getFromCurrency();
                profit -= (attempt.getExchangeRate() * attempt.getQuantity());
            } else {
                fromCurrency = historicalExchangeData.getFromCurrency();
                toCurrency = historicalExchangeData.getToCurrency();
                profit += (attempt.getExchangeRate() * attempt.getQuantity());
            }
            transactions.add(new Transaction(dateString, fromCurrency, toCurrency,
                attempt.getQuantity(), attempt.getAction()));

        }

        profit = CommonUtils.withPrecision(profit, PrecisionConstants.CURRENCY_PRECISION);

        return new Investment(transactions, profit, historicalExchangeData.getToCurrency());
    }
}
