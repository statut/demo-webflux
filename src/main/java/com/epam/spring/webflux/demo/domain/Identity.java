package com.epam.spring.webflux.demo.domain;

public class Identity {
    private String id;

    public Identity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
