package com.kibb.sense.user;

public class TwitterUser {
    private String twitterHandle;
    TwitterUser(String twitterHandler){
        this.twitterHandle = twitterHandler;
    }

    @Override
    public String toString(){
        return twitterHandle;
    }
}
