package com.example;

import java.util.Date;
import java.util.Random;

public class JokeProvider {

    private final static String[] JOKES = {
            "Bananas do not apeel to me.",
            "A better joke!"
    };

    public static String getJoke() {
        long seed = new Date().getTime();
        Random rand = new Random(seed);
        return JOKES[rand.nextInt(JOKES.length)];
    }
}
