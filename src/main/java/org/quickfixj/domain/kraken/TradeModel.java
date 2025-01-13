package org.quickfixj.domain.kraken;

import org.quickfixj.orderbook.OrderSide;

public record TradeModel(OrderSide orderSide, Double price, Double quantity, String ticker){
}
