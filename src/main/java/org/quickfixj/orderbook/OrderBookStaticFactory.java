package org.quickfixj.orderbook;

public class OrderBookStaticFactory {

    private static OrderBook orderBook = new OrderBook();

    public static OrderBook getOrderBook() {
        return orderBook;
    }
}
