package org.quickfixj.orderbook;

public record Order(String id, double price, double quantity, long timestamp) {
}
