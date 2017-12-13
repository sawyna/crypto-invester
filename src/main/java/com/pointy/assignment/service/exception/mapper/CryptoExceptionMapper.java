package com.pointy.assignment.service.exception.mapper;

import com.pointy.assignment.common.constants.Constants.HeaderConstants;
import com.pointy.assignment.common.error.Error;
import com.pointy.assignment.common.exceptions.CryptoBaseException;
import com.pointy.assignment.common.exceptions.CryptoInternalException;
import com.pointy.assignment.common.exceptions.CryptoInvalidInputException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.springframework.stereotype.Component;

/**
 * Maps java exceptions from service layer to corresponding
 * http status codes with appropriate error messages
 */
@Component
@Provider
public class CryptoExceptionMapper implements ExceptionMapper<CryptoBaseException> {

    @Override
    public Response toResponse(CryptoBaseException exception) {
        if (exception instanceof CryptoInvalidInputException) {
            return Response.status(Status.FORBIDDEN)
                .entity(Error.build(exception.getMessage(), "403"))
                .header(HeaderConstants.CONTENT_TYPE_LABEL, HeaderConstants.JSON_CONTENT_TYPE)
                .build();
        }

        if (exception instanceof CryptoInternalException) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(Error.build(exception.getMessage(), "500"))
                .header(HeaderConstants.CONTENT_TYPE_LABEL, HeaderConstants.JSON_CONTENT_TYPE)
                .build();
        }

        return
            Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(Error.build("Unknown internal error occurred", "500"))
                .header(HeaderConstants.CONTENT_TYPE_LABEL, HeaderConstants.JSON_CONTENT_TYPE)
                .build();
    }
}
