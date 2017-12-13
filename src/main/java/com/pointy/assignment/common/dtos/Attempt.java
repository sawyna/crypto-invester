package com.pointy.assignment.common.dtos;

public class Attempt {

    private int index;

    private double exchangeRate;

    private double quantity;

    private Action action;

    public Attempt(int index, double exchangeRate, double quantity, Action action) {
        this.index = index;
        this.exchangeRate = exchangeRate;
        this.quantity = quantity;
        this.action = action;
    }

    public int getIndex() {
        return index;
    }

    public double getExchangeRate() {
        return exchangeRate;
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

        Attempt attempt = (Attempt) o;

        if (getIndex() != attempt.getIndex()) {
            return false;
        }
        if (Double.compare(attempt.getExchangeRate(), getExchangeRate()) != 0) {
            return false;
        }
        if (Double.compare(attempt.getQuantity(), getQuantity()) != 0) {
            return false;
        }
        return getAction() == attempt.getAction();
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getIndex();
        temp = Double.doubleToLongBits(getExchangeRate());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getQuantity());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getAction().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format(
            "Attempt{index=%d, exchangeRate=%f, quantity=%f, action=%s}"
            , index, exchangeRate, quantity, action);
    }
}
