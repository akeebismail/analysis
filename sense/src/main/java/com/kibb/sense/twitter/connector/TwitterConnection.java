package com.kibb.sense.twitter.connector;

import sun.rmi.runtime.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.lang.String.format;

public class TwitterConnection implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(TwitterConnection.class.getName());
    private static final URI TWITTER_URI = URI.create("https://stream.twitter.com/1.1/statuses/sample.json");

    private final Consumer<String> tweetConsumer;

    public TwitterConnection(Consumer<String > tweetConsumer){
        this.tweetConsumer = tweetConsumer;
    }

    public static void main(String[] args){
        new TwitterConnection(LOGGER::info).run();
    }
    @Override
    public void run() {

    }

    private void drinkFromTheFireHose() throws IOException, InterruptedException{
        HttpURLConnection httpURLConnection = connectToTwitter();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))){
            processTweets(reader.lines());
        }catch (Exception e){
            LOGGER.info(format("Exception Thrown: %s \nRetrying... \n",e.getMessage()));
            retryConnection();
        }
    }

    void processTweets(Stream<String> stream){
        stream.filter(this::isNotDeleteEvent)
                .forEach(tweetConsumer);
    }

    private boolean isNotDeleteEvent(String tweet){
        return !tweet.startsWith("{\"delete\"");
    }

    private void retryConnection() throws IOException, InterruptedException{
        Thread.sleep(2000);
        drinkFromTheFireHose();
    }

    private HttpURLConnection connectToTwitter() throws IOException{
        HttpURLConnection httpURLConnection = (HttpURLConnection) TWITTER_URI.toURL().openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("GET");
        TwitterOAUTH oauth = new TwitterOAUTH();
        String signature = oauth.generateSignature();
        httpURLConnection.addRequestProperty("Authorization","OAuth " + oauth.getAuthParams()
            + ", oauth_signature=\""+ signature + "\"");

        return httpURLConnection;

    }
    public void stop(){
        //stop
    }
}
