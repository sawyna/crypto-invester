package com.pointy.assignment.client.tasks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pointy.assignment.common.exceptions.CryptoClientFailedException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.Callable;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HttpGetTask<T> implements Callable<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetConversionRateTask.class);

    private HttpClient httpClient;

    public HttpGetTask(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public abstract URI preExecute();

    public abstract T postExecute(JsonNode jsonNode) throws CryptoClientFailedException;

    @Override
    public T call() throws Exception {

        URI uri = preExecute();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null && httpResponse.getStatusLine() != null
            && httpResponse.getStatusLine().getStatusCode() == 200) {
            try (InputStream inputStream = httpEntity.getContent()) {

                JsonNode jsonNode = new ObjectMapper()
                    .readTree(inputStream);

                return postExecute(jsonNode);
            }
        } else {
            LOGGER.error("Error received %d\n", httpResponse.getStatusLine().getStatusCode());
            throw new CryptoClientFailedException(
                String.format("Error received %d\n", httpResponse.getStatusLine().getStatusCode()));
        }
    }
}
