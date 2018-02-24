package com.kibb.service.client;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerEndpointConfig;

public final class SingletonEndPointConfigurator extends ServerEndpointConfig.Configurator {

    private Endpoint singleInstance;
    public SingletonEndPointConfigurator(Endpoint singleInstance){
        this.singleInstance= singleInstance;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException{
        if (endpointClass != this.singleInstance.getClass()){
            throw new UnsupportedOperationException("This SingletonEndpointConfigurator only creates " +
                    "endpoints of class " + this.singleInstance.getClass()+ ", "
                    + endpointClass + " is not supported");
        }

        return (T) singleInstance;
    }
}
