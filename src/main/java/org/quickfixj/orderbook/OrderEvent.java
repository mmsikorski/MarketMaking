package org.quickfixj.orderbook;

import com.lmax.disruptor.EventFactory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderEvent {
    private Order order;

    public static final EventFactory<OrderEvent> FACTORY = OrderEvent::new;
}
