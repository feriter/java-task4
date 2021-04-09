package ru.nsu.ccfit;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        var exec = new Executor("TC.cfg");
        exec.execute();
        int c = 0;
        while (c != '\n') {
            try {
                c = System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        exec.terminate();
    }
}
