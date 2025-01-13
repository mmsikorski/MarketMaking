package org.quickfixj.engine;

import org.quickfixj.domain.kraken.TradeModel;

import java.util.Optional;

public class AskBidProcessor {

    public Optional<AskAndBidOrders> makeTradingDecision(/* orderbook input */) {
        analyzeOrderBookData();
        return Optional.empty();
    }

    public Optional<AskAndBidOrders> analyzeOrderBookData() {
        return Optional.empty();
    }

    public record AskAndBidOrders(TradeModel askOrder, TradeModel bidOrder) {}
}
