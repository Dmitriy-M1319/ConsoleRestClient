package org.example;

import org.example.client.TrainRestClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Runnable run = ()-> {
            try {
                TrainRestClient client = new TrainRestClient();
                int count = 100;
                for (int i = 0; i < count; i++) {
                    client.post();
                }
                client.closeClient();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        };

        for (int i = 0; i < 8; i++) {
            Thread thread = new Thread(run, "thread" + (i + 1));
            thread.start();
        }

    }
}