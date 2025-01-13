package org.quickfixj.handler;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.quickfixj.domain.kraken.MessageType;
import org.quickfixj.domain.kraken.Subscriptions;

@Setter
@Accessors(chain = true, fluent = true)
public class WebSocketHandlerBuilder {
    public MessageType name;
    public Subscriptions subscriptions;
    public String URI;

    @SneakyThrows
    public WebSocketHandler build() {
        return new WebSocketHandler(this.URI, this.name, this.subscriptions);
    }
}
