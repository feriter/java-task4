package ru.nsu.ccfit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RailwayStation {
    private final int capacity;
    private final ArrayList<Train> trains;
    private final HashMap<String, Storage> storages;

    public RailwayStation(HashMap<String, Storage> s, int c) {
        capacity = c;
        storages = s;
        trains = new ArrayList<>();
    }

    public synchronized void takeStation(Train train) throws InterruptedException {
        while (trains.size() >= capacity) {
            wait();
        }
        trains.add(train);
    }

    public synchronized void freeStation(Train train) throws Exception {
        if (!trains.remove(train)) {
            throw new Exception("Unexpected freeStation call");
        }
        notify();
    }

    public Storage getStorage(String name) {
        return storages.get(name);
    }
}
