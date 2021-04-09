package ru.nsu.ccfit;

import java.util.UUID;

public class Good {
    private final String name;
    private final UUID id;

    public Good(String t) {
        name = t;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
