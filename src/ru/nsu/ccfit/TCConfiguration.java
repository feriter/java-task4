package ru.nsu.ccfit;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class TCConfiguration {
    public final ArrayList<String> goods = new ArrayList<>();
    public final HashMap<String, Integer> goodCreateTime = new HashMap<>();
    public final HashMap<String, Integer> goodLoadTime = new HashMap<>();
    public final HashMap<String, Integer> goodUnloadTime = new HashMap<>();
    public final HashMap<String, Integer> goodConsumeTime = new HashMap<>();
    public final HashMap<String, Integer> departureStorageCapacity = new HashMap<>();
    public final HashMap<String, Integer> arrivalStorageCapacity = new HashMap<>();

    public final Integer distance;
    public final Integer departurePlaces;
    public final Integer arrivalPlaces;
    public final Integer forwardRailwayCount;
    public final Integer backRailwayCount;

    public final ArrayList<String> trainNames = new ArrayList<>();
    public final HashMap<String, String> trainGoodName = new HashMap<>();
    public final HashMap<String, Integer> trainCreateTime = new HashMap<>();
    public final HashMap<String, Integer> trainAmortizationTime = new HashMap<>();
    public final HashMap<String, Integer> trainSpeed = new HashMap<>();
    public final HashMap<String, Integer> trainCapacity = new HashMap<>();

    public TCConfiguration(String fileName) {
        var props = new Properties();
        try {
            props.load(new FileInputStream(fileName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        var gNames = props.getProperty("goods").split(",");
        goods.addAll(Arrays.asList(gNames));

        for (var name : goods) {
            if (!goodLoadTime.containsKey(name)) {
                departureStorageCapacity.put(name, Integer.parseInt(props.getProperty(name + ".departureStorageCapacity")));
                arrivalStorageCapacity.put(name, Integer.parseInt(props.getProperty(name + ".arrivalStorageCapacity")));
                goodCreateTime.put(name, Integer.parseInt(props.getProperty(name + ".createTime")));
                goodConsumeTime.put(name, Integer.parseInt(props.getProperty(name + ".consumeTime")));
                goodLoadTime.put(name, Integer.parseInt(props.getProperty(name + ".loadTime")));
                goodUnloadTime.put(name, Integer.parseInt(props.getProperty(name + ".unloadTime")));
            }
        }

        distance = Integer.parseInt(props.getProperty("distance"));
        departurePlaces = Integer.parseInt(props.getProperty("departurePlaces"));
        arrivalPlaces = Integer.parseInt(props.getProperty("arrivalPlaces"));
        forwardRailwayCount = Integer.parseInt(props.getProperty("forwardRailwaysCount"));
        backRailwayCount = Integer.parseInt(props.getProperty("backRailwaysCount"));

        var tNames = props.getProperty("trainNames").split(",");
        trainNames.addAll(Arrays.asList(tNames));

        for (var name : trainNames) {
            if (!trainSpeed.containsKey(name)) {
                trainGoodName.put(name, props.getProperty(name + ".good"));
                trainSpeed.put(name, Integer.parseInt(props.getProperty(name + ".speed")));
                trainCreateTime.put(name, Integer.parseInt(props.getProperty(name + ".createTime")));
                trainAmortizationTime.put(name, Integer.parseInt(props.getProperty(name + ".amortizationTime")));
                trainCapacity.put(name, Integer.parseInt(props.getProperty(name + ".capacity")));
            }
        }
    }
}
