package org.quickfixj.handler;

import org.quickfixj.domain.kraken.MessageType;
import org.quickfixj.domain.kraken.Subscriptions;

public class HandlerGenerator {

    public void generate(Subscriptions subscriptions, String Url, MessageType type) {
        new WebSocketHandlerBuilder().subscriptions(subscriptions).name(type).URI(Url).build().connect();

    }
}
