package com.kibb.sense.twitter;


import com.kibb.sense.twitter.connector.TwitterConnection;
import com.kibb.service.client.BroadCastingServiceEndpoint;
import com.kibb.service.client.DaemonThreadFactory;
import com.kibb.service.client.WebSocketServer;

import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class LiveTweetService implements Runnable {
    private static final Logger LOGGER = Logger.getLogger("com.kibb.sense.twitter");
    private static final int port = 8081;
    private static final String URI = "/tweets";

    private final ExecutorService executor = newSingleThreadExecutor(new DaemonThreadFactory());
    private BroadCastingServiceEndpoint<String> tweetsEndpoint = new BroadCastingServiceEndpoint<>();
    private WebSocketServer server = new WebSocketServer(URI,port,tweetsEndpoint);
    private TwitterConnection twitterConnector;

    public static void main(String[] args){
        new LiveTweetService().run();
    }
    @Override
    public void run() {
        LOGGER.setLevel(Level.FINE);
        executor.submit(server);
        twitterConnector = new TwitterConnection(tweetsEndpoint::onMessage);
        twitterConnector.run();

    }

    public void stop() throws Exception{
        server.stop();
        twitterConnector.stop();
        executor.shutdownNow();

    }
}
