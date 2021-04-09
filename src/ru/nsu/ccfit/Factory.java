package ru.nsu.ccfit;

public class Factory extends Thread {
    private final String name;
    private final Storage storage;
    private final TCConfiguration config;

    public Factory(Storage s, TCConfiguration cfg) {
        storage = s;
        name = storage.getName();
        config = cfg;
    }

    @Override
    public void run() {
        Good g;
        while (!isInterrupted()) {
            try {
                Thread.sleep(config.goodCreateTime.get(name));
                g = new Good(name);
                Log.println("          " + name + " factory created good: " + g.getId()
                        + ". Time is " + MyTime.getTimeInMillis());
                storage.addGood(g);
            }
            catch (InterruptedException e) {
                Log.println(name + " factory stopped");
                return;
            }
        }
    }
}
