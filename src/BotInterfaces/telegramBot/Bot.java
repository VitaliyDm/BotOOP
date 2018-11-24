package BotInterfaces.telegramBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import questions.QuestionsGenerator;
import constants.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Bot extends TelegramLongPollingBot {
    private static Bot bot;
    private static Map<Long, telegramBotUserSession> users = new HashMap<>();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        bot = new Bot();
        try {
            api.registerBot(bot);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    void sendMessage(String text, Long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        message.setText(text);
        try {
            sendMessage(message);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        if(!users.containsKey(chatId)){
            try {
                users.put(chatId, new telegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        telegramBotUserSession user = users.get(chatId);
        user.set_bot(bot);
        user.set_chatId(chatId);
        user.set_messagesQueue(message.getText());
    }

    @Override
    public String getBotUsername() {
        return Constants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Constants.BOT_TOKEN;
    }

}