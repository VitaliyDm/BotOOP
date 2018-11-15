package BotInterfaces.telegram;

import BotInterfaces.Dialog;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;
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

        System.out.println("Зашел в диалог");
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        telegramApi = new Bot();
        try {
            System.out.println("захотел поставить регистр");
            api.registerBot(telegramApi);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
        System.out.println("Все поставил");
    }

    @Override
    public String read() throws IOException {
        if (!telegramApi.messagesQueue.isEmpty()){
            return telegramApi.messagesQueue.pop();
        }
        return null;
    }

    @Override
    public void write(String text) throws IOException {
        if (telegramApi.chatId != 0){
            telegramApi.sendMessage(text);
        }
    }
}
