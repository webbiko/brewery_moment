package com.brewery.app.data.datasource.remote.util;

import com.brewery.app.BuildConfig;

/**
 * This class provides constants that will be used as helper to remote data access
 */
public final class RemoteConstants {

    public static final String SUPPORTED_CONTENT_TYPE = "application/json";

    public static final String API_BASE_URL = BuildConfig.BASE_URL;

    private RemoteConstants() {
        // Api constants provides utilities information and must never be instantiated
    }

    public final class Application {
        public final class Error {

            private Error() {
                // private constructor
            }

            public static final int GENERIC_ERROR = 1000;

            public static final int BUSINESS_ERROR = 412;
        }

        public final class StatusCode {

            private StatusCode() {
                // private constructor
            }

            public static final int SUCCESS = 200;
        }
    }
}
