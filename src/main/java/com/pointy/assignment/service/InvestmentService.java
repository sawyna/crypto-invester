package com.pointy.assignment.service;

import com.pointy.assignment.common.dtos.Investment;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import com.pointy.assignment.common.exceptions.CryptoInvalidInputException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * InvestmentService contract which defines the REST API contract
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface InvestmentService {

    /**
     * Gives optimal investment when invested a given quantity
     * in crypto currency on {@code dateString}
     */
    @GET
    @Path("/transactions/date/{ds}/currency/{currency}/quantity/{quantity}")
    Investment getTransactionsForOptimalInvestmentInEuros(
        @PathParam("ds") String dateString,
        @PathParam("currency") String currency,
        @PathParam("quantity") Double quantity
    ) throws CryptoInvalidInputException, CryptoInternalException;

    /**
     * This is a more generic version of API to get optimal investment strategy
     * where the toCurrency is also a parameter
     */
    Investment getTransactionsForOptimalInvestment(
        String dateString,
        String fromCurrency,
        String toCurrency,
        Double quantity
    ) throws CryptoInternalException, CryptoInvalidInputException;
}
