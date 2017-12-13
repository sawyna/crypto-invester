package com.pointy.assignment.bar.strategy;

import com.pointy.assignment.common.constants.Constants.PrecisionConstants;
import com.pointy.assignment.common.dtos.Action;
import com.pointy.assignment.common.dtos.Attempt;
import com.pointy.assignment.common.dtos.HistoricalExchangeData;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import com.pointy.assignment.common.util.CommonUtils;
import com.pointy.assignment.common.util.DateUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import org.springframework.stereotype.Component;

/**
 * This implementation gives the list of rational attempts
 * to attain minimum loss for a historical exchange data set
 */
@Component("minLossStrategy")
public class MinimumLossStrategy implements ICalculationStrategy {

    /**
     * @throws CryptoInternalException if the exchangeRates are zero or 1
     */
    @Override
    public List<Attempt> calculate(HistoricalExchangeData historicalExchangeData)
        throws CryptoInternalException {
        List<Double> exchangeRates = historicalExchangeData.getExchangeRates();

        if (exchangeRates.size() <= 1) {
            throw new CryptoInternalException("Exchange rates should have at least two entries");
        }

        if (DateUtils.nextDate(historicalExchangeData.getStartDate(), exchangeRates.size() - 1)
            .compareTo(historicalExchangeData.getEndDate()) != 0) {
            throw new CryptoInternalException("Illegal input data set");
        }

        if (historicalExchangeData.getInitialQuantity() <= 0) {
            throw new CryptoInternalException("Initial quantity has to be positive");
        }

        Stack<Attempt> attempts = new Stack<>();
        attempts.push(
            new Attempt(0, exchangeRates.get(0), historicalExchangeData.getInitialQuantity(),
                Action.BUY));
        Attempt lastAttempt = new Attempt(1, exchangeRates.get(1),
            historicalExchangeData.getInitialQuantity(), Action.SELL);
        attempts.push(lastAttempt);

        Double cashReserve = exchangeRates.get(1) * historicalExchangeData.getInitialQuantity();
        Double transactableQuantity;

        Double currentRate, previousRate;
        int currentIndex, previousIndex;
        for (int i = 2; i < exchangeRates.size(); i++) {
            previousIndex = i - 1;
            currentIndex = i;
            previousRate = exchangeRates.get(previousIndex);
            currentRate = exchangeRates.get(currentIndex);

            if (currentRate > previousRate) {
                transactableQuantity = CommonUtils.divisionWithPrecision(cashReserve, previousRate,
                    PrecisionConstants.CRYPTO_PRECISION);
                lastAttempt = attempts.peek();
                if (lastAttempt.getIndex() == previousIndex && lastAttempt.getAction()
                    .equals(Action.SELL)) {
                    attempts.pop();
                    attempts.push(
                        new Attempt(currentIndex, currentRate, transactableQuantity, Action.SELL));
                } else {
                    attempts.push(
                        new Attempt(previousIndex, previousRate, transactableQuantity, Action.BUY));
                    attempts.push(
                        new Attempt(currentIndex, currentRate, transactableQuantity, Action.SELL));
                }
                cashReserve = transactableQuantity * currentRate;
            }
        }

        List<Attempt> optimalAttempts = new ArrayList<>();
        while (!attempts.empty()) {
            optimalAttempts.add(attempts.pop());
        }

        Collections.reverse(optimalAttempts);
        return optimalAttempts;
    }
}
