package org.quickfixj.orderbook;

import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderProcessor {
    private final Disruptor<OrderEvent> disruptor;

    public OrderProcessor(OrderBook orderBook) {
        ExecutorService executor = Executors.newCachedThreadPool();
        disruptor = new Disruptor<>(OrderEvent.FACTORY, 1024, executor);

        disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
            Order order = event.getOrder();
//            orderBook.addOrder(OrderSide.BID, order);
        });

        disruptor.start();
    }


    public void publishOrder(Order order) {
        disruptor.publishEvent((event, sequence) -> event.setOrder(order));
    }

    public void shutdown() {
        disruptor.shutdown();
    }
}
