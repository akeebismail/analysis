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

        }catch (){

        }
    }
}
