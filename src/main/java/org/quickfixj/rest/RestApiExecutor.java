package org.quickfixj.rest;

import org.quickfixj.logger.MarketMakingLogger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RestApiExecutor {

    public static RestApiExecutor executor;

    public RestApiExecutor() {
        this.executor = this;
    }

    public static RestApiExecutor staticGetter() {
        return executor;
    }

    MarketMakingLogger log = MarketMakingLogger.create(RestApiExecutor.class);
    private RestApi api = new RestApi();


    public void fetchDataInSeparatedThread( ) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        executor.scheduleAtFixedRate(this::fetchData, 500, 200, TimeUnit.MILLISECONDS);
    }

    public void fetchData() {
        log.info("FETCH ORDER_BOOK");
        api.fetchOrderBook();
    }

}
