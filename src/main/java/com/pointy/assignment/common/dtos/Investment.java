package com.pointy.assignment.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Investment {

    @JsonProperty("transactions")
    private List<Transaction> transactions;

    @JsonProperty("profit")
    private double profit;

    @JsonProperty("currency")
    private String currency;

    public Investment(List<Transaction> transactions, double profit, String currency) {
        this.transactions = transactions;
        this.profit = profit;
        this.currency = currency;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getProfit() {
        return profit;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Investment that = (Investment) o;

        if (Double.compare(that.getProfit(), getProfit()) != 0) {
            return false;
        }
        if (!getTransactions().equals(that.getTransactions())) {
            return false;
        }
        return getCurrency().equals(that.getCurrency());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getTransactions().hashCode();
        temp = Double.doubleToLongBits(getProfit());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getCurrency().hashCode();
        return result;
    }
}
