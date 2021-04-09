package ru.nsu.ccfit;

import java.util.ArrayList;
import java.util.Calendar;

public class Train extends Thread {
    private final String name;
    private final ArrayList<Good> goods;
    private final RailwayData railwayData;
    private final Depot depot;
    private final TCConfiguration config;
    private final long startTime;

    private void loadGoods() throws Exception{
        var where = railwayData.departureStation.getStorage(config.trainGoodName.get(name));
        Log.println(name + " train ordered station");
        railwayData.departureStation.takeStation(this);
        Log.println(name + " train took a station and started loading goods: " + where.getName());
        var capacity = config.trainCapacity.get(name);
        while (goods.size() <= capacity) {
            Thread.sleep(config.goodLoadTime.get(where.getName()));
            goods.add(where.getGood());
        }
        Log.println(name + " train loaded goods. " + MyTime.getTimeInMillis());
        railwayData.departureStation.freeStation(this);
    }

    private void unloadGoods(Storage where) throws Exception {
        while (!goods.isEmpty()) {
            Thread.sleep(config.goodUnloadTime.get(goods.get(0).getName()));
            where.addGood(goods.remove(0));
        }
        Log.println(name + " train unloaded goods. " + MyTime.getTimeInMillis());
        railwayData.arrivalStation.freeStation(this);
    }

    public Train(String n, Depot d, RailwayData rwData, TCConfiguration cfg) {
        config = cfg;
        name = n;
        depot = d;
        goods = new ArrayList<>();
        railwayData = rwData;
        startTime = MyTime.getTimeInMillis();
        Log.println(n + " train created at " + startTime);
    }

    @Override
    public void run() {
        var amortizationTime = config.trainAmortizationTime.get(name);
        while (MyTime.getTimeInMillis() - startTime < amortizationTime && !isInterrupted()) {
            try {
                loadGoods();

                Log.println(name + " train wants to send goods");
                railwayData.forwardRailways.takeRailway();
                if (isInterrupted()) return;
                Log.println(name + " train took a railway to customers");
                int distPassed = 0;
                int offset = config.trainSpeed.get(name) / 10;
                while (distPassed < config.distance) {
                    distPassed += offset;
                    Thread.sleep(100);
                }
                if (isInterrupted()) return;
                Log.println(name + " train arrived at consumer side and is waiting for place on station");

                railwayData.arrivalStation.takeStation(this);
                Log.println(name + " train took a place on station");
                if (isInterrupted()) return;

                railwayData.forwardRailways.freeRailway();

                var st2 = railwayData.arrivalStation.getStorage(goods.get(0).getName());
                Log.println(name + " train started unloading goods: " + st2.getName());
                unloadGoods(st2);
                if (isInterrupted()) return;

                Log.println(name + " train wants to return home");
                railwayData.backRailways.takeRailway();
                if (isInterrupted()) return;
                Log.println(name + " train took a railway home");
                distPassed = 0;
                while (distPassed < config.distance) {
                    distPassed += offset;
                    Thread.sleep(100);
                }
                if (isInterrupted()) return;
                railwayData.backRailways.freeRailway();
                Log.println(name + " train is home");
                if (isInterrupted()) return;

            } catch (Exception e) {
                Log.println(name + " train stopped halfway and died");
                return;
            }
        }
        Log.println(name + " train is now dangerous to use and is heading to dump :(");
        depot.disposeTrain(this);
    }

    public String getTrainName() {
        return name;
    }
}
