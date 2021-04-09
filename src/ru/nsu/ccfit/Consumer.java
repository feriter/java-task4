package ru.nsu.ccfit;

public class Consumer extends Thread {
    private final String name;
    private final Storage storage;
    private final TCConfiguration config;

    public Consumer(Storage s, TCConfiguration cfg) {
        storage = s;
        name = storage.getName();
        config = cfg;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            Good good;
            try {
                Thread.sleep(config.goodConsumeTime.get(name));
                good = storage.getGood();
            } catch (InterruptedException e) {
                Log.println(name + " consumer stopped");
                return;
            }
            Log.println("          " + name + " consumer consumed good: " + good.getId()
                    + ". Time is " + MyTime.getTimeInMillis());
        }
    }
}
