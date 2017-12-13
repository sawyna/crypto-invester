package com.pointy.assignment.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pointy.assignment.bar.OptimalInvestmentCalculator;
import com.pointy.assignment.bar.strategy.ICalculationStrategy;
import com.pointy.assignment.common.dtos.Investment;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import com.pointy.assignment.common.exceptions.CryptoInvalidInputException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InvestmentServiceTest {

    @Mock
    private OptimalInvestmentCalculator optimalInvestmentCalculator;

    @Mock
    private ICalculationStrategy calculationStrategy;

    @InjectMocks
    private InvestmentServiceImpl investmentService;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.initMocks(this);
        //investmentService = new InvestmentServiceImpl(optimalInvestmentCalculator, calculationStrategy);
    }

    @AfterMethod
    public void after() {

    }

    @Test(expectedExceptions = CryptoInvalidInputException.class)
    public void testInvalidDateStringForGetTransactionsForOptimalInvestment()
        throws CryptoInvalidInputException, CryptoInternalException {
        String ds = "2017-2-03";
        String fromCurrency = "ABC";
        String toCurrency = "DEF";
        Double quantity = 1.0;

        investmentService
            .getTransactionsForOptimalInvestment(ds, fromCurrency, toCurrency, quantity);
    }

    @Test(expectedExceptions = CryptoInvalidInputException.class)
    public void testNonPositiveQuantityForGetTransactionsForOptimalInvestment()
        throws CryptoInvalidInputException, CryptoInternalException {
        String ds = "2017-02-03";
        String fromCurrency = "ABC";
        String toCurrency = "DEF";
        Double quantity = -1.0;

        investmentService
            .getTransactionsForOptimalInvestment(ds, fromCurrency, toCurrency, quantity);
    }

    @Test(expectedExceptions = CryptoInvalidInputException.class)
    public void testFutureDateStringForGetTransactionsForOptimalInvestment()
        throws CryptoInvalidInputException, CryptoInternalException {

        //sufficiently future date so that the test won't fail for the forseeable future
        String ds = "2999-02-03";
        String fromCurrency = "ABC";
        String toCurrency = "DEF";
        Double quantity = 1.0;

        investmentService
            .getTransactionsForOptimalInvestment(ds, fromCurrency, toCurrency, quantity);
    }

    @Test
    public void testSuccessCaseForGetTransactionsForOptimalInvestment()
        throws CryptoInternalException, CryptoInvalidInputException {

        String ds = "2017-01-01";
        String fromCurrency = "ABC";
        String toCurrency = "DEF";
        Double quantity = 1.0;

        Investment investment = new Investment(null, 0.0, "ABC");

        when(optimalInvestmentCalculator
            .invest(ds, fromCurrency, toCurrency, quantity, calculationStrategy))
            .thenReturn(investment);

        investmentService
            .getTransactionsForOptimalInvestment(ds, fromCurrency, toCurrency, quantity);

        verify(optimalInvestmentCalculator)
            .invest(ds, fromCurrency, toCurrency, quantity, calculationStrategy);
    }

}
