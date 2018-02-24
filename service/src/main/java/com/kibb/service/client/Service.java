package com.kibb.service.client;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Service implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Service.class.getName());
    private final String endPointToConnectTo;
    private final String serviceEndPointPath;
    private final int servicePort;

    private WebSocketServer webSocketServer;
    private ClientEndpoint<String> clientEndpoint;
    private MessageHandler<String> messageHandler;

    public Service(String endPointToConnectTo, String serviceEndPointPath, int servicePort,
                   MessageHandler<String> messageHandler){
        this.endPointToConnectTo = endPointToConnectTo;
        this.serviceEndPointPath = serviceEndPointPath;
        this.messageHandler = messageHandler;
        this.servicePort = servicePort;
    }

    public static void main(String[] args) throws IOException, DeploymentException {
        new Service("ws://localhost:8081/tweets/", "/testing/", 8090,originalText -> originalText).run();
    }

    @Override
    public void run(){
        LOGGER.setLevel(Level.FINE);

        try{
            BroadCastingServiceEndpoint<String> broadCastingServiceEndpoint = new BroadCastingServiceEndpoint<>();
            // create a client endpoint that connects to the given server endpoint and puts all messages through the message handler
            clientEndpoint = new ClientEndpoint<>(endPointToConnectTo, messageHandler);
            clientEndpoint.addListener(broadCastingServiceEndpoint);
            clientEndpoint.connect();

            // run the Jetty server for the server endpoint that clients will connect to. Tne endpoint simply informs
            // all listeners of all messages

            webSocketServer = new WebSocketServer(serviceEndPointPath, servicePort,broadCastingServiceEndpoint);
            webSocketServer.run();
        }catch (Exception e){
            //This is where more sensible error handling will be done
            LOGGER.severe(e.getMessage());
        }
    }

    public void stop() throws Exception{
        clientEndpoint.close();
        webSocketServer.stop();
    }
}
