package org.quickfixj;

import org.quickfixj.domain.kraken.BuiltInSubscriptions;
import org.quickfixj.domain.kraken.MessageType;
import org.quickfixj.domain.kraken.Subscriptions;
import org.quickfixj.domain.kraken.TradeModel;
import org.quickfixj.engine.AskBidProcessor;
import org.quickfixj.engine.OutputApi;
import org.quickfixj.handler.HandlerGenerator;
import org.quickfixj.orderbook.OrderBook;
import org.quickfixj.orderbook.OrderBookSnapshot;
import org.quickfixj.orderbook.OrderBookStaticFactory;
import org.quickfixj.orderbook.OrderSide;
import org.quickfixj.rest.RestApiExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.quickfixj.domain.kraken.MessageType.ORDER_BOOK_TRANSACTION;
import static org.quickfixj.domain.kraken.MessageType.ORDER_BOOK_UPDATE;

public class Main {
    public static void main(String[] args) {

        String wsUrl = "wss://ws.kraken.com";

        RestApiExecutor restApiExecutor = new RestApiExecutor();
        restApiExecutor.fetchDataInSeparatedThread();
        OutputApi outputApi = new OutputApi();
        AskBidProcessor processor = new AskBidProcessor();
//        restApiExecutor.fetchData();

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


        OrderBookSnapshot orderBookSnapshot = new OrderBookSnapshot();
        Runnable task = () -> {
            long start = System.currentTimeMillis();
            System.out.println("\nSTART ANALYSE! " + start);
            OrderBook orderBook = OrderBookStaticFactory.getOrderBook();
            orderBookSnapshot.orderBookLevels(orderBook, OrderSide.ASK, 8);
            orderBookSnapshot.orderBookLevels(orderBook, OrderSide.BID, 8);
            Double lastTradePrice = orderBook.getLastTradePrice();
            Double lastTradeQuantity = orderBook.getLastTradeQuantity();
            System.out.println("LAST TRADE: " + lastTradePrice + "| quantity: " + lastTradeQuantity);
            Optional<AskBidProcessor.AskAndBidOrders> askAndBidOrders = processor.makeTradingDecision(orderBook);
            askAndBidOrders.ifPresent(r -> {
                TradeModel askOrder = r.askOrder();
                TradeModel bidOrder = r.bidOrder();
                System.out.println("Order to Execute: " + r);
                outputApi.placeOrder(askOrder);
                outputApi.placeOrder(bidOrder);
            });
            long end = System.currentTimeMillis();
            System.out.println("\nEND ANALYSE! " + end);
            System.out.println("\n Time diff " + String.valueOf(end - start));

        };

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(task, 5, 2, TimeUnit.SECONDS);


    }
}