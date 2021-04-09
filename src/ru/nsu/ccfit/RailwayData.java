package ru.nsu.ccfit;

public class RailwayData {
    public final RailwayStation departureStation;
    public final RailwayStation arrivalStation;
    public final Railways forwardRailways;
    public final Railways backRailways;

    public RailwayData(TCConfiguration cfg, RailwayStation dep, RailwayStation arr) {
        forwardRailways = new Railways(cfg.forwardRailwayCount);
        backRailways = new Railways(cfg.backRailwayCount);
        departureStation = dep;
        arrivalStation = arr;
    }
}
