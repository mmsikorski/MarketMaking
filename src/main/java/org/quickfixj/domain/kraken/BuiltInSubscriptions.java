package org.quickfixj.domain.kraken;

public class BuiltInSubscriptions {
    public static String BTC_USD_TRADE = """
            {
             "event": "subscribe",
             "pair": ["XBT/USD"],
             "subscription": {"name": "trade"}
            }
            """;


    public static String TICKER_BTC_USD = """
            {
                "event": "subscribe",
                "pair": ["XBT/USD"],
                "subscription": {
                    "name": "ticker"
                }
            }
            """;


    public static String BTC_USD_ORDER_BOOK_UPDATE = """
            {
                "event": "subscribe",
                "pair": ["XBT/USD"],
                "subscription": {
                    "name": "book",
                    "depth": 10
                }
            }
            """;
}
