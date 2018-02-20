package com.kibb.service.client;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@javax.websocket.ClientEndpoint
public class ClientEndpoint<T> {
    private static final Logger LOGGER = Logger.getLogger(ClientEndpoint.class.getName());

    private final List<MessageHandler<T>> listeners = new ArrayList<>();
    private final URI serverEndpoint;
    private final MessageHandler<T> messageHandler;
    private Session session;

    public ClientEndpoint(String serverEndpoint, MessageHandler<T> messageHandler){
        this.serverEndpoint = URI.create(serverEndpoint);
        this.messageHandler = messageHandler;
    }

    @OnMessage
    public void onWebSocketText(String fullTweet) throws IOException{
        T message = messageHandler.proccessMessage(fullTweet);
        listeners.stream().forEach(messageListener -> messageListener.onMessage(message));
    }
    @OnError
    public void onError(Throwable error){
        LOGGER.warning("Error received: " +error.getMessage());
        close();
        naiveReconnectRetry();
    }
    @OnClose
    public void onClose()

}
