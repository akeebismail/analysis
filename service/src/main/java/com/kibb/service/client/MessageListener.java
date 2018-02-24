package com.kibb.service.client;

@FunctionalInterface
public interface MessageListener<T> {
    void onMessage(T message);
}
