package org.quickfixj.rest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quickfixj.orderbook.OrderBook;
import org.quickfixj.orderbook.OrderBookStaticFactory;
import org.quickfixj.orderbook.OrderSide;

import java.io.IOException;

public class RestApi {

    private static final OkHttpClient client = new OkHttpClient();
    private OrderBook orderBook = OrderBookStaticFactory.getOrderBook();


    void fetchOrderBook() {
        try {
            // Build the request URL
            String API_URL = ApiPaths.KrakenPaths.GET_ORDER_BOOK_DATA;

            String PAIR = "BTCUSD";

            String DEPTH = "10";

            String requestUrl = API_URL + "?pair=" + PAIR + "&count=" + DEPTH;

            // Create the HTTP request
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .get()
                    .build();

            // Execute the request
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("HTTP error: " + response.code());
                    return;
                }

                // Parse JSON response
                String responseBody = response.body().string();
                JSONObject responseJson = new JSONObject(responseBody);

                if (!responseJson.getJSONArray("error").isEmpty()) {
                    System.err.println("API error: " + responseJson.getJSONArray("error").toString());
                    return;
                }

                JSONObject result = responseJson.getJSONObject("result");
                JSONObject orderBook = result.getJSONObject("XXBTZUSD");

                JSONArray bids = orderBook.getJSONArray("bids");
                JSONArray asks = orderBook.getJSONArray("asks");

                orderBook.clear();
//                System.out.println("Bids:");
                printOrderBookEntries(bids, OrderSide.BID);

//                System.out.println("Asks:");
                printOrderBookEntries(asks, OrderSide.ASK);
            }

        } catch (IOException e) {
            System.err.println("Error fetching order book: " + e.getMessage());
        }
    }

    private void printOrderBookEntries(JSONArray entries, OrderSide side) {
        for (int i = 0; i < entries.length(); i++) {
            JSONArray entry = entries.getJSONArray(i);
            String price = entry.getString(0);
            String volume = entry.getString(1);


            Double priceD = Double.valueOf(price);
            Double volumeD = Double.valueOf(volume);
//            System.out.printf("Price: %s, Volume: %s\n",
//                    priceD, // Price
//                    volumeD
//            );

            orderBook.updatePriceLevel(side, priceD, volumeD);
        }
    }

}
