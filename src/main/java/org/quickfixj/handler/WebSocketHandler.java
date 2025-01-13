package org.quickfixj.handler;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.quickfixj.domain.kraken.MessageType;
import org.quickfixj.domain.kraken.Subscriptions;
import org.quickfixj.parser.IncomingMessageParser;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketHandler extends WebSocketClient {

    public MessageType messageType;
    public IncomingMessageParser parser;
    public Subscriptions subscriptions;

    public WebSocketHandler(String uri, MessageType messageType, Subscriptions subscriptions) throws URISyntaxException {
        super(new URI(uri));
        parser = new IncomingMessageParser();
        this.subscriptions = subscriptions;
        this.messageType = messageType;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to Kraken WebSocket!");
        // Subscribe to a public channel (example: ticker for BTC/USD)
        subscriptions.subscription().forEach(this::send);

    }

    @Override
    public void onMessage(String message) {
        parser.handle(messageType, message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed. Code: " + code + ", Reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("An error occurred: " + ex.getMessage());
    }
}
