package com.pointy.assignment.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

    @JsonProperty("date")
    private String date;

    @JsonProperty("from")
    private String fromCurrency;

    @JsonProperty("to")
    private String toCurrency;

    @JsonProperty("quantity")
    private double quantity;

    @JsonProperty("action")
    private Action action;

    public Transaction(String date, String fromCurrency, String toCurrency, double quantity,
        Action action) {
        this.date = date;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.quantity = quantity;
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getQuantity() {
        return quantity;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Transaction that = (Transaction) o;

        if (Double.compare(that.getQuantity(), getQuantity()) != 0) {
            return false;
        }
        if (!getDate().equals(that.getDate())) {
            return false;
        }
        if (!getFromCurrency().equals(that.getFromCurrency())) {
            return false;
        }
        if (!getToCurrency().equals(that.getToCurrency())) {
            return false;
        }
        return getAction() == that.getAction();
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getDate().hashCode();
        result = 31 * result + getFromCurrency().hashCode();
        result = 31 * result + getToCurrency().hashCode();
        temp = Double.doubleToLongBits(getQuantity());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getAction().hashCode();
        return result;
    }
}
