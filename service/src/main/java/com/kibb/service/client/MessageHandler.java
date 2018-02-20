package com.kibb.service.client;

@FunctionalInterface
public interface MessageHandler<T> {

    T proccessMessage(String message);
}
