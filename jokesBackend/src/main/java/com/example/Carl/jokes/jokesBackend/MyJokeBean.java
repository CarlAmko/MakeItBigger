package com.example.Carl.jokes.jokesBackend;

import com.example.JokeProvider;

public class MyJokeBean {

    public MyJokeBean() {
        this.jokeString = JokeProvider.getJoke();
    }

    private String jokeString;

    public String getData() {
        return jokeString;
    }

    public void setData(String data) {
        jokeString = data;
    }
}