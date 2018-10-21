package BotInterfaces.ConsoleBot;

import BotInterfaces.Dialog;
import questions.QuestionHelper;

import java.io.*;

public class ConsoleBotDialog extends Dialog{
    private BufferedReader reader;
    private BufferedWriter writer;

    public ConsoleBotDialog(QuestionHelper helper){
        questionHelper = helper;
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    @Override
    public String read() throws IOException {
        return reader.readLine();
    }

    @Override
    public void write(String text) throws IOException {
        writer.write(text);
        writer.flush();
    }
}
