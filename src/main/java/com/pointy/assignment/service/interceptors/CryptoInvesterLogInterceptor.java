package com.pointy.assignment.service.interceptors;

import java.util.UUID;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * Simple Input interceptor which generates a unique identifier
 * to identify all the logs of one request
 */
@Component
public class CryptoInvesterLogInterceptor extends AbstractPhaseInterceptor<Message> {

    public CryptoInvesterLogInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        MDC.put("requestId", UUID.randomUUID().toString());
    }
}
