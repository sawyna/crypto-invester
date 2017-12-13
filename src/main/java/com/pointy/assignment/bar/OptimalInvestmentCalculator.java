package com.pointy.assignment.bar;

import com.pointy.assignment.bar.create.InvestmentCreator;
import com.pointy.assignment.bar.strategy.ICalculationStrategy;
import com.pointy.assignment.client.CryptoClient;
import com.pointy.assignment.common.dtos.Attempt;
import com.pointy.assignment.common.dtos.HistoricalExchangeData;
import com.pointy.assignment.common.dtos.Investment;
import com.pointy.assignment.common.exceptions.CryptoClientException;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import com.pointy.assignment.common.util.DateUtils;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OptimalInvestmentCalculator {

    private static Logger LOGGER = LoggerFactory.getLogger(OptimalInvestmentCalculator.class);

    private CryptoClient cryptoClient;

    private InvestmentCreator investmentCreator;

    @Autowired
    public OptimalInvestmentCalculator(CryptoClient cryptoClient,
        InvestmentCreator investmentCreator) {
        this.cryptoClient = cryptoClient;
        this.investmentCreator = investmentCreator;
    }

    /**
     * Gives an optimal investment when provided with a calculation strategy and
     * a past date investment
     */
    public Investment invest(String pastDate, String fromCurrency, String toCurrency,
        double quantity,
        ICalculationStrategy calculationStrategy) throws CryptoInternalException {

        try {
            List<Double> exchangeRates = cryptoClient
                .getConversionRates(fromCurrency, toCurrency, pastDate,
                    DateUtils.currentDate());

            LOGGER.info("Successfully fetched exchangeRates between {} and {} from {} to {}",
                fromCurrency, toCurrency, pastDate, DateUtils.currentDate());

            HistoricalExchangeData historicalExchangeData = new HistoricalExchangeData(
                exchangeRates,
                pastDate, DateUtils.currentDate(), fromCurrency, toCurrency, quantity);
            List<Attempt> optimalAttempts = calculationStrategy.calculate(historicalExchangeData);

            return investmentCreator.create(optimalAttempts, historicalExchangeData);
        } catch (CryptoClientException e) {
            LOGGER.error("CryptoClient failed to fetch the exchange rates", e);
            throw new CryptoInternalException("CryptoClient failed to fetch the exchange rates", e);
        }
    }
}
