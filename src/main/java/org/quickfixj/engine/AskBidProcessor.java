package org.quickfixj.engine;

import lombok.ToString;
import org.quickfixj.domain.kraken.TradeModel;
import org.quickfixj.orderbook.OrderBook;
import org.quickfixj.orderbook.OrderBookSnapshot;
import org.quickfixj.orderbook.OrderSide;

import java.util.Map;
import java.util.Optional;

public class AskBidProcessor {

    public Optional<AskAndBidOrders> makeTradingDecision(OrderBook orderBook) {
        return analyzeOrderBookData(orderBook);
    }

    private Optional<AskAndBidOrders> analyzeOrderBookData(OrderBook orderBook) {
        Map.Entry<Double, Double> nthLevelBid = OrderBookSnapshot.getNthLevel(orderBook, OrderSide.BID, 5);
        Map.Entry<Double, Double> nthLevelAsk = OrderBookSnapshot.getNthLevel(orderBook, OrderSide.ASK, 5);
        TradeModel askTrade = new TradeModel(OrderSide.ASK, nthLevelAsk.getKey(), nthLevelAsk.getValue(), null);
        TradeModel bidTrade = new TradeModel(OrderSide.BID, nthLevelBid.getKey(), nthLevelBid.getValue(), null);
        return Optional.ofNullable(new AskAndBidOrders(askTrade, bidTrade));
    }

    public record AskAndBidOrders(TradeModel askOrder, TradeModel bidOrder) {

        @Override
        public String toString() {
            return "AskAndBidOrders{" +
                    "askOrder=" + askOrder +
                    ", bidOrder=" + bidOrder +
                    '}';
        }
    }
}
