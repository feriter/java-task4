package ru.nsu.ccfit;

public class Railways {
    private final int capacity;
    private int busyCount;

    public Railways(int cap) {
        capacity = cap;
        busyCount = 0;
    }

    public synchronized void takeRailway() throws InterruptedException {
        while (busyCount >= capacity) {
            wait();
        }
        busyCount++;
    }

    public synchronized void freeRailway() throws Exception {
        if (busyCount <= 0) {
            throw new Exception("Unexpected freeRailway call");
        }
        busyCount--;
        notifyAll();
    }
}
