package org.quickfixj.orderbook;

import org.junit.jupiter.api.Test;
import org.quickfixj.parser.kraken.OrderBookUpdateParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookTest {

    void setUp() {
    }

    @Test
    void shouldBuildCorrectOrderbook() throws IOException {
        //given
        var inputOrderbookFile = new File("src/test/resources/orderBookUpdateTestData.txt");
        var parser = new OrderBookUpdateParser();

        //when
        List<String> lines = Files.readAllLines(inputOrderbookFile.toPath());
        for (String line : lines) {
            parser.parse(line);
        }

        //then
        System.out.println("FINISHED!");
        OrderBook orderBook = OrderBookStaticFactory.getOrderBook();
        orderBook.printBook();
    }
}