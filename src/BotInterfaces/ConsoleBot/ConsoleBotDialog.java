package BotInterfaces.ConsoleBot;

import BotInterfaces.Dialog;
import questions.QuestionHelper;

import java.io.*;

public class ConsoleBotDialog extends Dialog{
    private BufferedReader reader;
    private BufferedWriter writer;

    public ConsoleBotDialog(QuestionHelper helper) throws IOException {
        super(helper);
        questionHelper = helper;
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
        write(showHelp());
    }

    @Override
    public String read() throws IOException {
        String a = reader.readLine();
        return a;
    }

    @Override
    public void write(String text) throws IOException {
        writer.write(String.format("%s\r\n", text));
        writer.flush();
    }
}
