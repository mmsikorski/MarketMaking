package org.quickfixj.orderbook;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class OrderBookSnapshot {

    public void orderBookLevels(OrderBook orderBook, OrderSide side, int levels) {
        NavigableMap<Double, Double> bidsOrAsks = orderBook.getBidsOrAsks(side);

        NavigableMap<Double, Double> topLevels = getTopLevels(bidsOrAsks, levels);

        System.out.println("Top " + side.name() + " levels " + topLevels);


    }


    public static NavigableMap<Double, Double> getTopLevels(NavigableMap<Double, Double> orderBook, int n) {
        // Create a new TreeMap for the result
        NavigableMap<Double, Double> topLevels = new TreeMap<>(orderBook.comparator());

        // Use an iterator to extract the first n entries
        int count = 0;
        for (Map.Entry<Double, Double> entry : orderBook.entrySet()) {
            if (count++ >= n) break;
            topLevels.put(entry.getKey(), entry.getValue());
        }

        return topLevels;
    }
}
