package BotInterfaces;

import java.io.IOException;

public interface IOInterface {
    String read() throws IOException;
    void write(String text) throws IOException;
}
