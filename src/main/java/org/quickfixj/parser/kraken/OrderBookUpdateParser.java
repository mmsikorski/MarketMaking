package org.quickfixj.parser.kraken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.quickfixj.domain.kraken.OrderBookUpdateModel;
import org.quickfixj.orderbook.Order;
import org.quickfixj.orderbook.OrderBook;
import org.quickfixj.orderbook.OrderBookStaticFactory;
import org.quickfixj.orderbook.OrderSide;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.google.gson.*;

public class OrderBookUpdateParser {

    OrderBook orderBook = OrderBookStaticFactory.getOrderBook();
    Gson gson = new Gson();

    public OrderBookUpdateModel parse(String message) {
        try {
            if (message.contains("heartbeat")) return null;

                // Parse the top-level JSON as an array
            JsonArray jsonElements = gson.fromJson(message, JsonArray.class);

            // Extract the second element (update object)
            JsonObject updateObject = jsonElements.get(1).getAsJsonObject();

            // Handle initial snapshots ("as" and "bs")
            if (updateObject.has("as") || updateObject.has("bs")) {
                if (updateObject.has("as")) {
                    JsonArray asks = updateObject.getAsJsonArray("as");
                    processUpdates(OrderSide.ASK, asks);
                }
                if (updateObject.has("bs")) {
                    JsonArray bids = updateObject.getAsJsonArray("bs");
                    processUpdates(OrderSide.BID, bids);
                }
                orderBook.printBook();
            }

            // Handle incremental updates ("a" and "b")
            if (updateObject.has("a")) {
                JsonArray asks = updateObject.getAsJsonArray("a");
                processUpdates(OrderSide.ASK, asks);
                orderBook.printBook();
            }
            if (updateObject.has("b")) {
                JsonArray bids = updateObject.getAsJsonArray("b");
                processUpdates(OrderSide.BID, bids);
                orderBook.printBook();
            }

        } catch (JsonSyntaxException e) {
            System.err.println("Invalid JSON syntax: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Invalid JSON structure: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return null; // Return a proper model if needed
    }

    private void processUpdates(OrderSide side, JsonArray updates) {
        for (JsonElement element : updates) {
            JsonArray level = element.getAsJsonArray();
            double price = level.get(0).getAsDouble();
            double volume = level.get(1).getAsDouble();
            // Update the order book
            orderBook.updatePriceLevel(side, price, volume);
        }
    }
}
