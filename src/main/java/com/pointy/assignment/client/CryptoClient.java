package com.pointy.assignment.client;

import com.pointy.assignment.client.tasks.GetConversionRateTask;
import com.pointy.assignment.common.constants.Constants;
import com.pointy.assignment.common.constants.Constants.PrecisionConstants;
import com.pointy.assignment.common.exceptions.CryptoClientException;
import com.pointy.assignment.common.exceptions.CryptoRuntimeException;
import com.pointy.assignment.common.util.CommonUtils;
import com.pointy.assignment.common.util.DateUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CryptoClient {

    private static Logger LOGGER = LoggerFactory.getLogger(CryptoClient.class);

    private HttpClient httpClient;

    private ExecutorService executorService;

    public CryptoClient() {
        this.executorService = Executors.newFixedThreadPool(Constants.CRYPTO_THREADPOOL_SIZE);
        this.httpClient = HttpClientBuilder.create().build();
    }

    public Double getConversionRate(String fromCurrency, String toCurrency, String asOfDate)
        throws CryptoClientException {
        return getConversionRates(fromCurrency, toCurrency, asOfDate, asOfDate).get(0);
    }

    public List<Double> getConversionRates(String fromCurrency, String toCurrency, String fromDate,
        String toDate) throws CryptoClientException {

        String ds = fromDate;

        List<Future<Double>> futures = new ArrayList<>();
        while (ds.compareTo(toDate) <= 0) {
            futures.add(executorService
                .submit(new GetConversionRateTask(httpClient, fromCurrency, toCurrency, ds)));
            ds = DateUtils.nextDate(ds);
        }

        List<Double> conversionRates = new ArrayList<>();
        Double conversionRate;
        ds = fromDate;
        for (Future<Double> future : futures) {
            try {
                conversionRate = future.get();
                conversionRate = CommonUtils
                    .withPrecision(conversionRate, PrecisionConstants.CURRENCY_PRECISION);
                conversionRates.add(conversionRate);
                LOGGER.info("Conversion rate {} on {}", conversionRate, ds);
                ds = DateUtils.nextDate(ds);
            } catch (InterruptedException e) {
                LOGGER.error("Unexpected error in the threadpool", e);
                throw new CryptoRuntimeException("Unexpected error", e);
            } catch (ExecutionException e) {
                LOGGER.error("GetConversionTask failed", e);
                throw new CryptoClientException("Crypto client failed", e);
            }
        }

        return conversionRates;
    }
}
