package BotInterfaces.telegram;

import BotInterfaces.Dialog;
import org.telegram.telegrambots.api.objects.Message;
import questions.QuestionHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class telegramBotDialog extends Dialog {
    private BufferedReader reader;
    private BufferedWriter writer;

    public telegramBotDialog(QuestionHelper helper) {
        super(helper);
        questionHelper = helper;
        Message message = update.getMessage();
    }

    @Override
    public String read() throws IOException {
        return null;
    }

    @Override
    public void write(String text) throws IOException {

    }
}
