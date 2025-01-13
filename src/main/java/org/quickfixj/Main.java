package org.quickfixj;

import org.quickfixj.domain.kraken.BuiltInSubscriptions;
import org.quickfixj.domain.kraken.MessageType;
import org.quickfixj.domain.kraken.Subscriptions;
import org.quickfixj.handler.HandlerGenerator;

import java.util.List;

import static org.quickfixj.domain.kraken.MessageType.ORDER_BOOK_TRANSACTION;
import static org.quickfixj.domain.kraken.MessageType.ORDER_BOOK_UPDATE;

public class Main {
    public static void main(String[] args) {

        String wsUrl = "wss://ws.kraken.com";

        HandlerGenerator handlerGenerator = new HandlerGenerator();

        List<String> subscriptions = List.of(BuiltInSubscriptions.TICKER_BTC_USD);
        handlerGenerator.generate(new Subscriptions(subscriptions), wsUrl, MessageType.TICKER);


//        handlerGenerator.generate(new Subscriptions(
//                List.of(BuiltInSubscriptions.BTC_USD_TRADE)
//        ), wsUrl, ORDER_BOOK_TRANSACTION);
//
//
        handlerGenerator.generate(new Subscriptions(
                List.of(BuiltInSubscriptions.BTC_USD_ORDER_BOOK_UPDATE)
        ), wsUrl, ORDER_BOOK_UPDATE);


    }
}