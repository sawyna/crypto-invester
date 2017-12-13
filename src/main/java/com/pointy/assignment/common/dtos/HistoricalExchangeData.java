package com.pointy.assignment.common.dtos;

import java.util.List;

public class HistoricalExchangeData {

    private List<Double> exchangeRates;

    private String startDate;

    private String endDate;

    private String fromCurrency;

    private String toCurrency;

    private double initialQuantity;

    public HistoricalExchangeData(List<Double> exchangeRates, String startDate,
        String endDate, String fromCurrency, String toCurrency, double initialQuantity) {
        this.exchangeRates = exchangeRates;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.initialQuantity = initialQuantity;
    }

    public List<Double> getExchangeRates() {
        return exchangeRates;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getInitialQuantity() {
        return initialQuantity;
    }
}
