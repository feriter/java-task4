package ru.nsu.ccfit;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Log {
    private static Writer writer;

    private Log() {

    }

    public static synchronized void println(String str) {
        if (writer == null) {
            try {
                writer = new FileWriter("log.txt");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.write(str + '\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
