package com.pointy.assignment.bar;

import static org.mockito.Mockito.doThrow;

import com.pointy.assignment.bar.strategy.ICalculationStrategy;
import com.pointy.assignment.bar.strategy.MinimumLossStrategy;
import com.pointy.assignment.client.CryptoClient;
import com.pointy.assignment.common.exceptions.CryptoClientException;
import com.pointy.assignment.common.exceptions.CryptoClientFailedException;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import com.pointy.assignment.common.util.DateUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OptimalInvestmentCalculatorTest {

    @InjectMocks
    private OptimalInvestmentCalculator optimalInvestmentCalculator;

    @Mock
    private CryptoClient cryptoClient;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = CryptoInternalException.class)
    public void cryptoClientFailureTest() throws CryptoInternalException, CryptoClientException {
        String pastDate = "2017-01-01";
        String fromCurrency = "ABC";
        String toCurrency = "DEF";
        double quantity = 1.0;
        ICalculationStrategy calculationStrategy = new MinimumLossStrategy();

        doThrow(CryptoClientFailedException.class)
            .when(cryptoClient)
            .getConversionRates(fromCurrency, toCurrency, pastDate, DateUtils.currentDate());

        optimalInvestmentCalculator
            .invest(pastDate, fromCurrency, toCurrency, quantity, calculationStrategy);
    }
}
