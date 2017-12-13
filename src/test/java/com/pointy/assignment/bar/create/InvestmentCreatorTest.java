package com.pointy.assignment.bar.create;

import com.pointy.assignment.common.dtos.Action;
import com.pointy.assignment.common.dtos.Attempt;
import com.pointy.assignment.common.dtos.HistoricalExchangeData;
import com.pointy.assignment.common.dtos.Investment;
import com.pointy.assignment.common.dtos.Transaction;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import java.util.ArrayList;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InvestmentCreatorTest {

    @InjectMocks
    private InvestmentCreator investmentCreator;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void exactlyTwoDaysWithIncreasingExchangeRates() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(50.0);
        exchangeRates.add(60.0);
        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-02", "ABC", "DEF", 10);

        List<Attempt> attempts = new ArrayList<>();
        attempts.add(new Attempt(0, 50.0, 10.0, Action.BUY));
        attempts.add(new Attempt(1, 60.0, 10.0, Action.SELL));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("2017-01-01", "DEF", "ABC", 10.0, Action.BUY));
        transactions.add(new Transaction("2017-01-02", "ABC", "DEF", 10.0, Action.SELL));

        double profit = 100.0;
        String currency = "DEF";

        Investment expectedInvestment = new Investment(transactions, profit, currency);

        Investment actualInvestment = investmentCreator.create(attempts, historicalExchangeData);

        Assert.assertEquals(actualInvestment, expectedInvestment);
    }

    @Test
    public void exactlyTwoDaysWithDecreasingExchangeRates() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(50.0);
        exchangeRates.add(40.0);
        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-02", "ABC", "DEF", 10);

        List<Attempt> attempts = new ArrayList<>();
        attempts.add(new Attempt(0, 50.0, 10.0, Action.BUY));
        attempts.add(new Attempt(1, 40.0, 10.0, Action.SELL));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("2017-01-01", "DEF", "ABC", 10.0, Action.BUY));
        transactions.add(new Transaction("2017-01-02", "ABC", "DEF", 10.0, Action.SELL));

        double profit = -100.0;
        String currency = "DEF";

        Investment expectedInvestment = new Investment(transactions, profit, currency);

        Investment actualInvestment = investmentCreator.create(attempts, historicalExchangeData);

        Assert.assertEquals(actualInvestment, expectedInvestment);
    }

    @Test
    public void strictlyIncreasingExchangeRates() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(40.0);
        exchangeRates.add(45.0);
        exchangeRates.add(50.0);
        exchangeRates.add(51.0);

        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-04", "ABC", "DEF", 10);

        List<Attempt> attempts = new ArrayList<>();
        attempts.add(new Attempt(0, 40.0, 10.0, Action.BUY));
        attempts.add(new Attempt(3, 51.0, 10.0, Action.SELL));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("2017-01-01", "DEF", "ABC", 10.0, Action.BUY));
        transactions.add(new Transaction("2017-01-04", "ABC", "DEF", 10.0, Action.SELL));

        double profit = 110.0;
        String currency = "DEF";

        Investment expectedInvestment = new Investment(transactions, profit, currency);
        Investment actualInvestment = investmentCreator.create(attempts, historicalExchangeData);
        Assert.assertEquals(actualInvestment, expectedInvestment);

    }

    @Test
    public void strictlyDecreasingExchangeRates() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(51.0);
        exchangeRates.add(50.0);
        exchangeRates.add(45.0);
        exchangeRates.add(40.0);

        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-04", "ABC", "DEF", 10);

        List<Attempt> attempts = new ArrayList<>();
        attempts.add(new Attempt(0, 51.0, 10.0, Action.BUY));
        attempts.add(new Attempt(1, 50.0, 10.0, Action.SELL));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("2017-01-01", "DEF", "ABC", 10.0, Action.BUY));
        transactions.add(new Transaction("2017-01-02", "ABC", "DEF", 10.0, Action.SELL));

        double profit = -10.0;
        String currency = "DEF";

        Investment expectedInvestment = new Investment(transactions, profit, currency);
        Investment actualInvestment = investmentCreator.create(attempts, historicalExchangeData);
        Assert.assertEquals(actualInvestment, expectedInvestment);
    }

    @Test
    public void decreasingRateOnSecondDayAndMonotonicallyIncreasingExchangeRates()
        throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(51.0);
        exchangeRates.add(50.0);
        exchangeRates.add(55.0);
        exchangeRates.add(60.0);

        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-04", "ABC", "DEF", 10);

        List<Attempt> attempts = new ArrayList<>();
        attempts.add(new Attempt(0, 51.0, 10.0, Action.BUY));
        attempts.add(new Attempt(3, 60.0, 10.0, Action.SELL));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("2017-01-01", "DEF", "ABC", 10.0, Action.BUY));
        transactions.add(new Transaction("2017-01-04", "ABC", "DEF", 10.0, Action.SELL));

        double profit = 90.0;
        String currency = "DEF";

        Investment expectedInvestment = new Investment(transactions, profit, currency);
        Investment actualInvestment = investmentCreator.create(attempts, historicalExchangeData);
        Assert.assertEquals(actualInvestment, expectedInvestment);

    }

    @Test
    public void decreasingRateOnAllDaysAndIncreasingRateOnLastDay() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(51.0);
        exchangeRates.add(50.0);
        exchangeRates.add(40.0);
        exchangeRates.add(45.0);

        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-04", "ABC", "DEF", 10);

        List<Attempt> attempts = new ArrayList<>();
        attempts.add(new Attempt(0, 51.0, 10.0, Action.BUY));
        attempts.add(new Attempt(1, 50.0, 10.0, Action.SELL));
        attempts.add(new Attempt(2, 40.0, 12.5, Action.BUY));
        attempts.add(new Attempt(3, 45.0, 12.5, Action.SELL));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("2017-01-01", "DEF", "ABC", 10.0, Action.BUY));
        transactions.add(new Transaction("2017-01-02", "ABC", "DEF", 10.0, Action.SELL));
        transactions.add(new Transaction("2017-01-03", "DEF", "ABC", 12.5, Action.BUY));
        transactions.add(new Transaction("2017-01-04", "ABC", "DEF", 12.5, Action.SELL));

        double profit = 52.5;
        String currency = "DEF";

        Investment expectedInvestment = new Investment(transactions, profit, currency);
        Investment actualInvestment = investmentCreator.create(attempts, historicalExchangeData);
        Assert.assertEquals(actualInvestment, expectedInvestment);

    }

    @Test
    public void twoConsecutiveMaximaAndMinimaExchangeRates() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(40.0);
        exchangeRates.add(43.0);
        exchangeRates.add(50.0);
        exchangeRates.add(47.0);
        exchangeRates.add(30.0);
        exchangeRates.add(45.0);

        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-06", "ABC", "DEF", 10);

        List<Attempt> attempts = new ArrayList<>();
        attempts.add(new Attempt(0, 40.0, 10.0, Action.BUY));
        attempts.add(new Attempt(2, 50.0, 10.0, Action.SELL));
        attempts.add(new Attempt(4, 30.0, 16.6667, Action.BUY));
        attempts.add(new Attempt(5, 45.0, 16.6667, Action.SELL));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("2017-01-01", "DEF", "ABC", 10.0, Action.BUY));
        transactions.add(new Transaction("2017-01-03", "ABC", "DEF", 10.0, Action.SELL));
        transactions.add(new Transaction("2017-01-05", "DEF", "ABC", 16.6667, Action.BUY));
        transactions.add(new Transaction("2017-01-06", "ABC", "DEF", 16.6667, Action.SELL));

        double profit = 350.0;
        String currency = "DEF";

        Investment expectedInvestment = new Investment(transactions, profit, currency);
        Investment actualInvestment = investmentCreator.create(attempts, historicalExchangeData);
        Assert.assertEquals(actualInvestment, expectedInvestment);
    }
}
