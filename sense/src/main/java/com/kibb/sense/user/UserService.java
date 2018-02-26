package com.kibb.sense.user;

import com.kibb.sense.twitter.TweetParser;
import com.kibb.service.client.Service;

public class UserService implements Runnable {
    private static final int PORT = 8083;
    private final Service service;

    public UserService(){
        service = new Service("ws://localhost:8081/tweets/",
                "/users/", PORT, TweetParser::getTwitterHandlerfromTweet);
    }
    @Override
    public void run() {
        service.run();
    }
    public void stop() throws Exception{
        service.stop();
    }

    public static void main(String[] args){
        new UserService().run();
    }
}
