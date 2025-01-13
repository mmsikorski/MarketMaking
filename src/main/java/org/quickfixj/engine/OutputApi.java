package org.quickfixj.engine;

import org.quickfixj.domain.kraken.TradeModel;

public class OutputApi {
    private final ExecuteOrder executeOrder = new ExecuteOrder();

    public void placeOrder(TradeModel tradeModel) {
        executeOrder.send(tradeModel);
    }

    void cancelOrder() {

    }
}
