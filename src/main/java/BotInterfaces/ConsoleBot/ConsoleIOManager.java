package BotInterfaces.ConsoleBot;

import BotInterfaces.IOInterface;

import java.io.*;

public class ConsoleIOManager implements IOInterface {
    private BufferedReader reader;
    private BufferedWriter writer;

    public ConsoleIOManager(){
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    @Override
    public String read() throws IOException {
        return reader.readLine();
    }

    @Override
    public void write(String text) throws IOException {
        writer.write(String.format("%s\r\n", text));
        writer.flush();
    }
}
