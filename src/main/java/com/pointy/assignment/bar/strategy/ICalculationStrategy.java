package com.pointy.assignment.bar.strategy;

import com.pointy.assignment.common.dtos.Attempt;
import com.pointy.assignment.common.dtos.HistoricalExchangeData;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import java.util.List;

public interface ICalculationStrategy {

    /**
     * Gives a list of attempts when provided with the exchangeData
     */
    List<Attempt> calculate(HistoricalExchangeData historicalExchangeData)
        throws CryptoInternalException;
}
