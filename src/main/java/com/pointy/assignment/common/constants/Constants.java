package com.pointy.assignment.common.constants;

/**
 * A bucket of common constants to be used across the project
 */
public interface Constants {

    int CRYPTO_THREADPOOL_SIZE = 20;

    enum Currency {
        EUR("EUR");

        private String label;

        Currency(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    interface HeaderConstants {

        String CONTENT_TYPE_LABEL = "Content-Type";
        String JSON_CONTENT_TYPE = "application/json";
    }

    interface PrecisionConstants {

        int CRYPTO_PRECISION = 4;
        int CURRENCY_PRECISION = 2;
    }
}
