package org.quickfixj.orderbook;

import lombok.Getter;

public class OrderBookStaticFactory {

    @Getter
    private static OrderBook orderBook = new OrderBook();

}
