package org.quickfixj.orderbook;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class OrderBookSnapshot {

    public void orderBookLevels(OrderBook orderBook, OrderSide side, int levels) {
        // Retrieve bids or asks based on the side
        NavigableMap<Double, Double> bidsOrAsks = orderBook.getBidsOrAsks(side);

        // Get the top N levels from the order book
        NavigableMap<Double, Double> topLevels = getTopLevels(bidsOrAsks, levels);

        // Print detailed information about the top levels
        printLevelInfo(topLevels, side);

        //System.out.println("\nTop " + side.name() + " levels (Summary): " + topLevels);
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

    public static void printLevelInfo(NavigableMap<Double, Double> levels, OrderSide side) {
        System.out.println("Top " + side.name() + " Levels Details:");
        System.out.println("==================================================================");
        System.out.println("Level |   Price   |  Volume  | % Diff from Prev | % Diff from Best");
        System.out.println("------------------------------------------------------------------");

        Double bestPrice = null;
        Double previousPrice = null;
        int level = 1;

        for (Map.Entry<Double, Double> entry : levels.entrySet()) {
            Double price = entry.getKey();
            Double volume = entry.getValue();

            String percentDifferencePrevStr = "";
            String percentDifferenceBestStr = "";

            if (previousPrice != null) {
                // Calculate percentage difference from the previous level
                double percentDifferencePrev = ((price - previousPrice) / previousPrice) * 100.0;
                percentDifferencePrevStr = String.format("%.4f%%", percentDifferencePrev);
            }

            if (bestPrice == null) {
                bestPrice = price; // The first price is the best price
            } else {
                // Calculate percentage difference from the best level
                double percentDifferenceBest = ((price - bestPrice) / bestPrice) * 100.0;
                percentDifferenceBestStr = String.format("%.4f%%", percentDifferenceBest);
            }

            // Print formatted level info
            System.out.printf("  %4d   | %8.4f | %8.4f | %15s | %16s\n",
                    level, price, volume, percentDifferencePrevStr, percentDifferenceBestStr);

            previousPrice = price;
            level++;
        }

        System.out.println("==================================================================");
    }

    public static Map.Entry<Double, Double> getNthLevel(OrderBook orderBookL, OrderSide side, int n) {
        NavigableMap<Double, Double> orderBook = orderBookL.getBidsOrAsks(side);
        if (n <= 0 || n > orderBook.size()) {
            throw new IllegalArgumentException("Invalid level: " + n);
        }

        int currentIndex = 1;
        for (Map.Entry<Double, Double> entry : orderBook.entrySet()) {
            if (currentIndex == n) {
                return entry;
            }
            currentIndex++;
        }

        return null; // Should never reach here due to bounds check
    }


}
