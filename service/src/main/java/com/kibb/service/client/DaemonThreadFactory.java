package com.kibb.service.client;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(final Runnable runnable){
        Thread t=  new Thread(runnable);
        t.setDaemon(true);

        return  t;
    }
}
