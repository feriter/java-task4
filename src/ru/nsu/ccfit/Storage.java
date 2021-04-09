package ru.nsu.ccfit;

import java.util.ArrayList;

public class Storage {
    private final String name;
    private final ArrayList<Good> goods;
    private final int capacity;

    public Storage(String n, int c) {
        name = n;
        goods = new ArrayList<>();
        capacity = c;
    }

    public synchronized void addGood(Good good) throws InterruptedException {
        while (goods.size() >= capacity) {
            wait();
        }
        goods.add(good);
        notifyAll();
    }

    public synchronized Good getGood() throws InterruptedException {
        while (goods.isEmpty()) {
            wait();
        }
        var goodToReturn = goods.remove(0);
        notifyAll();
        return goodToReturn;
    }

    public String getName() {
        return name;
    }
}
