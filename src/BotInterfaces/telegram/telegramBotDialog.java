package BotInterfaces.telegram;

import BotInterfaces.Dialog;
import org.telegram.telegrambots.api.objects.Message;
import questions.QuestionHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class telegramBotDialog extends Dialog {
    private BufferedReader reader;
    private BufferedWriter writer;
    private Bot telegramApi;

    public telegramBotDialog(QuestionHelper helper) {
        super(helper);
        questionHelper = helper;
        telegramApi = new Bot();
    }

    @Override
    public String read() throws IOException {
        if (!telegramApi.messagesQueue.isEmpty()){
            var message = telegramApi.messagesQueue.pop();
            return message;
        }
        return null;
    }

    @Override
    public void write(String text) throws IOException {
        telegramApi.sendMessage(text);
    }
}
