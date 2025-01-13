package org.quickfixj.orderbook;

import lombok.Getter;
import lombok.Setter;
import org.quickfixj.logger.MarketMakingLogger;

import java.util.*;
import java.util.zip.CRC32;

@Getter
@Setter
public class OrderBook {

    MarketMakingLogger log = MarketMakingLogger.create(OrderBook.class);
    private String exchangeName;
    private String instrumentName;

    private final NavigableMap<Double, Double> bids = new TreeMap<>(Collections.reverseOrder());
    private final NavigableMap<Double, Double> asks = new TreeMap<>();
    volatile private Double lastTradePrice;
    volatile private Double lastTradeQuantity;


    public OrderBook() {
    }

    // Add or update a price level
    public synchronized void updatePriceLevel(OrderSide side, double price, double volume) {
        NavigableMap<Double, Double> book = side.equals(OrderSide.ASK) ? asks : bids;
        if (volume == 0) {
            System.out.println("DELETE!");
            book.remove(price); // Remove the price level if volume is 0
        } else {
            System.out.println("UPDATE!");
            book.put(price, volume); // Add or update the volume at the price level
        }
    }

    // Get the best price level (highest bid or lowest ask)
    public synchronized Map.Entry<Double, Double> getBest(OrderSide side) {
        NavigableMap<Double, Double> book = side.equals(OrderSide.BID) ? bids : asks;
        return book.isEmpty() ? null : book.firstEntry(); // Best price level
    }

    // Get the top N levels for bids or asks
    public synchronized List<Map.Entry<Double, Double>> getTopLevels(OrderSide side, int n) {
        NavigableMap<Double, Double> book = side.equals(OrderSide.BID) ? bids : asks;
        List<Map.Entry<Double, Double>> topLevels = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Double, Double> entry : book.entrySet()) {
            if (count >= n) break;
            topLevels.add(entry);
            count++;
        }
        return topLevels;
    }

    // Clear the entire order book
    public synchronized void clearBook() {
        bids.clear();
        asks.clear();
    }

    // Print the current state of the order book
    public synchronized void printBook() {
        System.out.println("Order Book for " + instrumentName + " @ " + exchangeName);
        System.out.println("Last trade price and quantity : " + lastTradePrice + " | " + lastTradeQuantity);
        System.out.println(String.format("%-20s %-10s %-20s", "ASKS (Price -> Volume)", "", "BIDS (Price -> Volume)"));

        // Get the lists of asks and bids
        List<Map.Entry<Double, Double>> asksList = new ArrayList<>(asks.entrySet());
        List<Map.Entry<Double, Double>> bidsList = new ArrayList<>(bids.entrySet());

        // Find the maximum number of rows to print
        int maxRows = Math.max(asksList.size(), bidsList.size());
        maxRows =10;
        System.out.println(
                asksList.get(0).getKey() > bidsList.get(0).getKey() ? "TRUE" : "FALSE"
        );
        for (int i = 0; i < maxRows; i++) {

            String askEntry = (i < asksList.size())
                    ? String.format("%.5f -> %.8f", asksList.get(i).getKey(), asksList.get(i).getValue())
                    : ""; // Empty if no more asks

            String bidEntry = (i < bidsList.size())
                    ? String.format("%.5f -> %.8f", bidsList.get(i).getKey(), bidsList.get(i).getValue())
                    : ""; // Empty if no more bids

            // Print the row with aligned columns
            System.out.println(String.format("%-20s %-10s %-20s", askEntry, "", bidEntry));
        }
    }

}
