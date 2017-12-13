package com.pointy.assignment.client.tasks;

import com.fasterxml.jackson.databind.JsonNode;
import com.pointy.assignment.common.exceptions.CryptoClientFailedException;
import com.pointy.assignment.common.exceptions.CryptoRuntimeException;
import com.pointy.assignment.common.util.CommonUtils;
import com.pointy.assignment.common.util.DateUtils;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetConversionRateTask extends HttpGetTask<Double> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetConversionRateTask.class);

    private static final String FROM_SYMBOL_PARAM = "fsym";
    private static final String TO_SYMBOL_PARAM = "tsyms";
    private static final String TIMESTAMP = "ts";

    private String fromCurrency;

    private String toCurrency;

    private String asOfDate;

    public GetConversionRateTask(HttpClient httpClient, String fromCurrency,
        String toCurrency, String asOfDate) {
        super(httpClient);
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.asOfDate = asOfDate;
    }

    @Override
    public URI preExecute() {
        try {
            return new URIBuilder()
                .setScheme("https")
                .setHost("min-api.cryptocompare.com")
                .setPath("/data/pricehistorical")
                .addParameter(FROM_SYMBOL_PARAM, this.fromCurrency)
                .addParameter(TO_SYMBOL_PARAM, this.toCurrency)
                .addParameter(TIMESTAMP, String.valueOf(DateUtils.dateInEpochSeconds(asOfDate)))
                .build();
        } catch (URISyntaxException e) {
            throw new CryptoRuntimeException("URI not built properly", e);
        }
    }

    @Override
    public Double postExecute(JsonNode jsonNode) throws CryptoClientFailedException {
        if (jsonNode.get(fromCurrency) == null) {
            LOGGER.info("Received an error {} from mini crypto api", jsonNode);
            throw new CryptoClientFailedException(
                String.format("Error received %s\n", jsonNode));
        }

        return CommonUtils.parseDouble(jsonNode.get(fromCurrency).get(toCurrency).asText());
    }
}
