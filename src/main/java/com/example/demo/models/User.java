package com.example.demo.models;

public class User {
    private String name;

    public User(String navn) {
        this.name = navn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

