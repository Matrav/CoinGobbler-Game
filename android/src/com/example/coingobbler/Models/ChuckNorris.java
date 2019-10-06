package com.example.coingobbler.Models;

public class ChuckNorris {

    public String type;
    public Value value;

    public ChuckNorris(String type, Value value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}
