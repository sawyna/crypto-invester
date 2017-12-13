package com.pointy.assignment.common.util;

import com.pointy.assignment.common.exceptions.CryptoRuntimeException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    public static Double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            LOGGER.info("Input string {} is not an double", s);
            throw new CryptoRuntimeException(
                String.format("Illegal String %s. Cannot be converted to double", s));
        }
    }

    public static Double divisionWithPrecision(double a, double b, int precision) {
        double value = a / b;
        return withPrecision(value, precision);
    }

    public static Double withPrecision(double a, int precision) {
        return BigDecimal.valueOf(a)
            .setScale(precision, RoundingMode.HALF_EVEN)
            .doubleValue();
    }
}
