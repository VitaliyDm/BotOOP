package BotInterfaces;

import java.io.IOException;

public interface IOInterface {
    String read() throws IOException, InterruptedException;
    void write(String text) throws IOException;
}
