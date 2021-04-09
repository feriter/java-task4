package ru.nsu.ccfit;

import java.util.ArrayList;
import java.util.HashMap;

public class Executor {
    private final ArrayList<Factory> factories;
    private final ArrayList<Consumer> consumers;
    private final Depot depot;
    private final TCConfiguration config;

    public Executor(String configFileName) {
        config = new TCConfiguration(configFileName);
        factories = new ArrayList<>();
        HashMap<String, Storage> factoryStorages = new HashMap<>();
        HashMap<String, Storage> consumerStorages = new HashMap<>();
        consumers = new ArrayList<>();

        for (var gName : config.goods) {
            if (!factoryStorages.containsKey(gName)) {
                factoryStorages.put(gName, new Storage(gName, config.departureStorageCapacity.get(gName)));
                consumerStorages.put(gName, new Storage(gName, config.arrivalStorageCapacity.get(gName)));
            }

            factories.add(new Factory(factoryStorages.get(gName), config));
            consumers.add(new Consumer(consumerStorages.get(gName), config));
        }

        var depStation = new RailwayStation(factoryStorages, config.departurePlaces);
        var arrStation = new RailwayStation(consumerStorages, config.arrivalPlaces);
        var railwayData = new RailwayData(config, depStation, arrStation);

        depot = new Depot(railwayData, config);
    }

    public void execute() {
        for (var fact : factories) {
            fact.start();
        }
        depot.launch();
        for (var cons : consumers) {
            cons.start();
        }
        System.out.println("Program started. Press Enter to finish it");
    }

    public void terminate() {
        Log.println("Termination began");
        for (var fact : factories) {
            fact.interrupt();
        }
        depot.shutDown();
        for (var cons : consumers) {
            cons.interrupt();
        }
    }
}
