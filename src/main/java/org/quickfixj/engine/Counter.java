package org.quickfixj.engine;

import org.quickfixj.logger.MarketMakingLogger;

import java.util.concurrent.atomic.AtomicLong;

public class Counter {

    private static MarketMakingLogger logger = MarketMakingLogger.create(Counter.class);

    private static AtomicLong counter = new AtomicLong();

    public static void add() {
        logger.info(String.valueOf(counter.getAndIncrement()));

    }
}
