package org.quickfixj.domain.kraken;

public enum MessageType {
    ORDER_BOOK_TRANSACTION("ORDER_BOOK_TRANSACTION"),
    ORDER_BOOK_UPDATE("ORDER_BOOK_UPDATE"),
    TICKER("TICKER");

    MessageType(String orderBookTransaction) {
    }
}
