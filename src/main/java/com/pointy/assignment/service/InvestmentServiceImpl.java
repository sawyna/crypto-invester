package com.pointy.assignment.service;

import com.pointy.assignment.bar.OptimalInvestmentCalculator;
import com.pointy.assignment.bar.strategy.ICalculationStrategy;
import com.pointy.assignment.common.constants.Constants.Currency;
import com.pointy.assignment.common.constants.Constants.PrecisionConstants;
import com.pointy.assignment.common.dtos.Investment;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import com.pointy.assignment.common.exceptions.CryptoInvalidInputException;
import com.pointy.assignment.common.util.CommonUtils;
import com.pointy.assignment.common.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Implmentation of InvestmentService
 */
@Component("investmentService")
public class InvestmentServiceImpl implements InvestmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvestmentServiceImpl.class);

    private OptimalInvestmentCalculator optimalInvestmentCalculator;

    private ICalculationStrategy minimumLossStrategy;

    @Autowired
    public InvestmentServiceImpl(
        OptimalInvestmentCalculator optimalInvestmentCalculator,
        @Qualifier("minLossStrategy") ICalculationStrategy calculationStrategy) {
        this.optimalInvestmentCalculator = optimalInvestmentCalculator;
        this.minimumLossStrategy = calculationStrategy;
    }

    @Override
    public Investment getTransactionsForOptimalInvestmentInEuros(String dateString, String currency,
        Double quantity) throws CryptoInternalException, CryptoInvalidInputException {

        return getTransactionsForOptimalInvestment(dateString, currency, Currency.EUR.getLabel(),
            quantity);
    }

    @Override
    public Investment getTransactionsForOptimalInvestment(String dateString,
        String fromCurrency, String toCurrency, Double quantity)
        throws CryptoInternalException, CryptoInvalidInputException {

        LOGGER.info("Getting optimal transactions for an investment of {} {} currency on {}",
            quantity, fromCurrency, dateString);

        quantity = CommonUtils.withPrecision(quantity, PrecisionConstants.CRYPTO_PRECISION);

        if (!DateUtils.isValidDateString(dateString)) {
            throw new CryptoInvalidInputException(
                String.format("DateString %s is invalid. It has to be in the format yyyy-MM-dd",
                    dateString));
        }

        if (quantity <= 0) {
            LOGGER.info("Invalid quantity {}", quantity);
            throw new CryptoInvalidInputException(
                String.format(
                    "Input quantity %f is less than or equal to zero. quantity should be strictly greater than zero",
                    quantity));
        }

        if (dateString.compareTo(DateUtils.currentDate()) >= 0) {
            LOGGER.info("The investment cannot be done in the future");
            throw new CryptoInvalidInputException("Input date has to be in the past");
        }

        return optimalInvestmentCalculator
            .invest(dateString, fromCurrency, toCurrency, quantity, this.minimumLossStrategy);
    }
}
