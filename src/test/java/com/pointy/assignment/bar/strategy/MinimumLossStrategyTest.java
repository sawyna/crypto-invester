package com.pointy.assignment.bar.strategy;

import com.pointy.assignment.common.dtos.Action;
import com.pointy.assignment.common.dtos.Attempt;
import com.pointy.assignment.common.dtos.HistoricalExchangeData;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MinimumLossStrategyTest {

    private ICalculationStrategy calculationStrategy;

    @BeforeMethod
    public void init() {
        calculationStrategy = new MinimumLossStrategy();
    }

    @Test(expectedExceptions = CryptoInternalException.class)
    public void zeroHistoricalDataSetThrowsException() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-10", "ABC", "DEF", 10);

        calculationStrategy.calculate(historicalExchangeData);
    }

    @Test(expectedExceptions = CryptoInternalException.class)
    public void oneHistoricalDataSetThrowsException() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(1.0);
        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-10", "ABC", "DEF", 10);

        calculationStrategy.calculate(historicalExchangeData);
    }

    @Test(expectedExceptions = CryptoInternalException.class)
    public void invalidStartDateWithExchangeRatesInHistoricalDataSetThrowsException()
        throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(1.0);
        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-10", "ABC", "DEF", 10);

        calculationStrategy.calculate(historicalExchangeData);
    }

    @Test(expectedExceptions = CryptoInternalException.class)
    public void nonPositiveInitialQuantityInHistoricalDataSetThrowsException()
        throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(1.0);
        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-10", "ABC", "DEF", 10);

        calculationStrategy.calculate(historicalExchangeData);
    }

    @Test
    public void exactlyTwoDaysWithIncreasingExchangeRates() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(50.0);
        exchangeRates.add(60.0);
        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-02", "ABC", "DEF", 10);

        List<Attempt> expectedAttempts = new ArrayList<>();
        expectedAttempts.add(new Attempt(0, 50.0, 10.0, Action.BUY));
        expectedAttempts.add(new Attempt(1, 60.0, 10.0, Action.SELL));

        List<Attempt> actualAttempts = calculationStrategy.calculate(historicalExchangeData);
        Assert.assertEquals(actualAttempts, expectedAttempts);
    }

    @Test
    public void exactlyTwoDaysWithDecreasingExchangeRates() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(50.0);
        exchangeRates.add(40.0);
        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-02", "ABC", "DEF", 10);

        List<Attempt> expectedAttempts = new ArrayList<>();
        expectedAttempts.add(new Attempt(0, 50.0, 10.0, Action.BUY));
        expectedAttempts.add(new Attempt(1, 40.0, 10.0, Action.SELL));

        List<Attempt> actualAttempts = calculationStrategy.calculate(historicalExchangeData);
        Assert.assertEquals(actualAttempts, expectedAttempts);
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

        List<Attempt> expectedAttempts = new ArrayList<>();
        expectedAttempts.add(new Attempt(0, 40.0, 10.0, Action.BUY));
        expectedAttempts.add(new Attempt(3, 51.0, 10.0, Action.SELL));

        List<Attempt> actualAttempts = calculationStrategy.calculate(historicalExchangeData);
        Assert.assertEquals(actualAttempts, expectedAttempts);
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

        List<Attempt> expectedAttempts = new ArrayList<>();
        expectedAttempts.add(new Attempt(0, 51.0, 10.0, Action.BUY));
        expectedAttempts.add(new Attempt(1, 50.0, 10.0, Action.SELL));

        List<Attempt> actualAttempts = calculationStrategy.calculate(historicalExchangeData);
        Assert.assertEquals(actualAttempts, expectedAttempts);
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

        List<Attempt> expectedAttempts = new ArrayList<>();
        expectedAttempts.add(new Attempt(0, 51.0, 10.0, Action.BUY));
        expectedAttempts.add(new Attempt(3, 60.0, 10.0, Action.SELL));

        List<Attempt> actualAttempts = calculationStrategy.calculate(historicalExchangeData);
        Assert.assertEquals(actualAttempts, expectedAttempts);
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

        List<Attempt> expectedAttempts = new ArrayList<>();
        expectedAttempts.add(new Attempt(0, 51.0, 10.0, Action.BUY));
        expectedAttempts.add(new Attempt(1, 50.0, 10.0, Action.SELL));
        expectedAttempts.add(new Attempt(2, 40.0, 12.5, Action.BUY));
        expectedAttempts.add(new Attempt(3, 45.0, 12.5, Action.SELL));

        List<Attempt> actualAttempts = calculationStrategy.calculate(historicalExchangeData);
        Assert.assertEquals(actualAttempts, expectedAttempts);
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

        List<Attempt> expectedAttempts = new ArrayList<>();
        expectedAttempts.add(new Attempt(0, 40.0, 10.0, Action.BUY));
        expectedAttempts.add(new Attempt(2, 50.0, 10.0, Action.SELL));
        expectedAttempts.add(new Attempt(4, 30.0, 16.6667, Action.BUY));
        expectedAttempts.add(new Attempt(5, 45.0, 16.6667, Action.SELL));

        List<Attempt> actualAttempts = calculationStrategy.calculate(historicalExchangeData);
        Assert.assertEquals(actualAttempts, expectedAttempts);
    }

    @Test
    public void twoConsecutiveMinimaAndMaximaExchangeRates() throws CryptoInternalException {
        List<Double> exchangeRates = new ArrayList<>();
        exchangeRates.add(60.0);
        exchangeRates.add(55.0);
        exchangeRates.add(50.0);
        exchangeRates.add(53.0);
        exchangeRates.add(55.0);
        exchangeRates.add(45.0);
        exchangeRates.add(40.0);
        exchangeRates.add(30.0);
        exchangeRates.add(35.0);
        exchangeRates.add(60.0);

        HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(exchangeRates,
            "2017-01-01",
            "2017-01-10", "ABC", "DEF", 10);

        List<Attempt> expectedAttempts = new ArrayList<>();
        expectedAttempts.add(new Attempt(0, 60.0, 10.0, Action.BUY));
        expectedAttempts.add(new Attempt(1, 55.0, 10.0, Action.SELL));
        expectedAttempts.add(new Attempt(2, 50.0, 11.0, Action.BUY));
        expectedAttempts.add(new Attempt(4, 55.0, 11.0, Action.SELL));
        expectedAttempts.add(new Attempt(7, 30.0, 20.1667, Action.BUY));
        expectedAttempts.add(new Attempt(9, 60.0, 20.1667, Action.SELL));

        List<Attempt> actualAttempts = calculationStrategy.calculate(historicalExchangeData);
        Assert.assertEquals(actualAttempts, expectedAttempts);
    }
}
