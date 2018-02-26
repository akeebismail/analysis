package com.kibb.sense.twitter;

import com.kibb.service.client.BroadCastingServiceEndpoint;
import com.kibb.service.client.DaemonThreadFactory;
import com.kibb.service.client.WebSocketServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResource;
import static java.nio.file.Paths.get;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Reads tweets from a file and sends them to the Twitter Service endpoint.
 */
public class CannedTweetsService implements Runnable {
    //LOGGER
    private static final Logger LOGGER = Logger.getLogger(CannedTweetsService.class.getName());
    private ExecutorService executor = newSingleThreadExecutor(new DaemonThreadFactory());
    private BroadCastingServiceEndpoint<String> tweetsEndpoint = new BroadCastingServiceEndpoint<>();

    private final WebSocketServer server = new WebSocketServer("/tweets",8081, tweetsEndpoint);
    private final Path filePath;

    public CannedTweetsService(Path filePath){
        this.filePath = filePath;
    }

    public static void main(String[] args) throws URISyntaxException{
        new CannedTweetsService(get(getSystemResource("./tweetdata60-mins.txt").toURI())).run();
    }

    @Override
    public void run() {
        executor.submit(server);
        try (Stream<String> lines = Files.lines(filePath)){
            lines.filter(s -> !s.equals("OK"))
                    .peek(s2 -> this.addArtificialDelay())
                    .forEach(s1 ->tweetsEndpoint.onMessage(s1));
        }catch (IOException e){

        }
    }

    private void addArtificialDelay(){
        try {

            //reading the file is FAST, add an artificial delay
            MILLISECONDS.sleep(5);
        }catch (InterruptedException e){
            LOGGER.log(Level.WARNING,e.getMessage(), e);
        }
    }

    public void stop() throws Exception{
        server.stop();
        executor.shutdownNow();
    }
}
