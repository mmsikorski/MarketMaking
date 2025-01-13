package org.quickfixj.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.quickfixj.domain.kraken.*;
import org.quickfixj.engine.Counter;
import org.quickfixj.logger.MarketMakingLogger;
import org.quickfixj.orderbook.OrderBook;
import org.quickfixj.orderbook.OrderBookStaticFactory;
import org.quickfixj.parser.kraken.OrderBookUpdateParser;

import java.util.List;

public class IncomingMessageParser {

    private static OrderBookUpdateParser orderBookUpdateParser = new OrderBookUpdateParser();
    private static MarketMakingLogger log = MarketMakingLogger.create(IncomingMessageParser.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderBook orderBook = OrderBookStaticFactory.getOrderBook();



    public void handle(MessageType messageType, String message) {
        switch (messageType) {
            case ORDER_BOOK_TRANSACTION -> {
                toTransactionModel(message);
            }
            case ORDER_BOOK_UPDATE -> {
                toOrderBookUpdateModel(message);
            }

            case TICKER -> {
                toTickerModel(message);
            }
        }

    }

    private void toTickerModel(String message) {
        if (message.contains("heartbeat")) return;

//        log.info("Ticker: " + message);
        // Deserialize the array into List<Object>
        TickerModel parsedData = null;
        try {
            parsedData = objectMapper.readValue(message, TickerModel.class
            );
            Counter.add();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Manually map to your TickerData class
        List<Object> c = parsedData.getDetails().getC();
        double price = Double.valueOf((String) c.get(0));
        double quantity = Double.valueOf((String) c.get(1));
        orderBook.setLastTradePrice(price);
        orderBook.setLastTradeQuantity(quantity);
        log.info("Ticker:" + price*quantity + " " + price + " " + quantity);
    }

    private TradeModel toOrderBookUpdateModel(String message) {
        OrderBookUpdateModel model = orderBookUpdateParser.parse(message);
//        log.info("Order: " + model);
        return null;
    }

    private TransactionModel toTransactionModel(String message) {
        log.info("Transaction: " + message);
        return null;
    }
}
