package com.kibb.sense.twitter.server;

import com.kibb.service.client.BroadCastingServiceEndpoint;
import com.kibb.service.client.MessageListener;
import com.kibb.service.client.WebSocketServer;

public class TweetServer implements Runnable {

    private static final int PORT = 8081;
    private WebSocketServer server;
    private final BroadCastingServiceEndpoint<String> tweetsEnpoint;

    public TweetServer(){
        tweetsEnpoint = new BroadCastingServiceEndpoint<>();
        server = new WebSocketServer("/tweets", PORT, tweetsEnpoint);
    }

    public static void main(String[] args){
        new TweetServer().run();
    }

    @Override
    public void run() {
        server.run();
    }

    public void stop() throws Exception{
        server.stop();
    }

    public MessageListener<String > getMessageListener(){
        return tweetsEnpoint;
    }
}
