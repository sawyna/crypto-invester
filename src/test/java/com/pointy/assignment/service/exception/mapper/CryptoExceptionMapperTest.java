package com.pointy.assignment.service.exception.mapper;

import com.pointy.assignment.common.error.Error;
import com.pointy.assignment.common.exceptions.CryptoBaseException;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import com.pointy.assignment.common.exceptions.CryptoInvalidInputException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CryptoExceptionMapperTest {

    @InjectMocks
    private CryptoExceptionMapper cryptoExceptionMapper;

    @BeforeMethod
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void after() {

    }

    @Test
    public void testCryptoInvalidInputException() {
        String exceptionMessage = "STUB Message";
        CryptoInvalidInputException exception = new CryptoInvalidInputException(exceptionMessage);
        Response actualResponse = cryptoExceptionMapper.toResponse(exception);

        //asserting each property of the response object as there is no equals implementation
        Assert.assertEquals(actualResponse.getStatus(), Status.FORBIDDEN.getStatusCode());
        Assert.assertEquals(((Error) actualResponse.getEntity()).getErrorCode(), "403");
        Assert.assertEquals(((Error) actualResponse.getEntity()).getMessage(), exceptionMessage);
    }

    @Test
    public void testCryptoInternalException() {
        String exceptionMessage = "STUB Message";
        CryptoInternalException exception = new CryptoInternalException(exceptionMessage);
        Response actualResponse = cryptoExceptionMapper.toResponse(exception);

        //asserting each property of the response object as there is no equals implementation
        Assert
            .assertEquals(actualResponse.getStatus(), Status.INTERNAL_SERVER_ERROR.getStatusCode());
        Assert.assertEquals(((Error) actualResponse.getEntity()).getErrorCode(), "500");
        Assert.assertEquals(((Error) actualResponse.getEntity()).getMessage(), exceptionMessage);
    }

    @Test
    public void testAnyOtherException() {

        CryptoBaseException exception = new CryptoBaseException("");
        Response actualResponse = cryptoExceptionMapper.toResponse(exception);

        //asserting each property of the response object as there is no equals implementation
        Assert
            .assertEquals(actualResponse.getStatus(), Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

}
