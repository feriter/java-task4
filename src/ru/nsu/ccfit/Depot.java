package ru.nsu.ccfit;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Depot {
    private boolean isShutDown;
    private final RailwayData railwayData;
    private final TCConfiguration config;
    private final ExecutorService threadPool;
    private final ArrayList<Train> trains;

    public Depot(RailwayData rwData, TCConfiguration cfg) {
        railwayData = rwData;
        config = cfg;
        threadPool = Executors.newFixedThreadPool(config.trainNames.size());
        isShutDown = false;
        trains = new ArrayList<>();
    }

    private void launchNewTrain(String name) {
        threadPool.submit(() -> {
            try {
                Log.println(name + " train started creating");
                Thread.sleep(config.trainCreateTime.get(name));
            } catch (InterruptedException e) {
                Log.println(name + " train will not be created");
            }
            var newTrain = new Train(name, this, railwayData, config);
            trains.add(newTrain);
            newTrain.start();
        });
    }

    public synchronized void disposeTrain(Train train) {
        train.interrupt();
        if (!isShutDown) {
            launchNewTrain(train.getTrainName());
        }
    }

    public void shutDown() {
        isShutDown = true;
        threadPool.shutdown();
        for (var train : trains) {
            disposeTrain(train);
        }
    }

    public void launch() {
        for (var tName : config.trainNames) {
            launchNewTrain(tName);
        }
    }
}
