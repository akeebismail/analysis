package com.kibb.service.client;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;

@javax.websocket.ClientEndpoint
public class ClientEndpoint<T> {
    private static final Logger LOGGER = Logger.getLogger(ClientEndpoint.class.getName());

    private final List<MessageListener<T>> listeners = new ArrayList<>();
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
    public void onClose(){
        LOGGER.warning(format("Session to %s closed, retrying....", serverEndpoint));
        naiveReconnectRetry();
    }

    public void addListener(MessageListener<T> listener){
        listeners.add(listener);
    }

    public void connect(){
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try{
            session = container.connectToServer(this, serverEndpoint);
            LOGGER.info("Connected to: " +serverEndpoint);
        }catch (DeploymentException | IOException e){
            LOGGER.warning(format("Error connecting to %s: %s", serverEndpoint, e.getMessage()));
        }
    }
    public void close(){
        if (session != null){
            try{
                session.close();
            }catch (IOException e){
                LOGGER.warning(format("Error closing Session: %s", e.getMessage()));
            }
        }
    }

    public static ClientEndpoint<String> createPassThroughEndPoint(String serverEndPoint){
        return  new ClientEndpoint<>(serverEndPoint, originalText -> originalText);
    }

    private void naiveReconnectRetry(){
        try {
            SECONDS.sleep(5);
            connect();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
