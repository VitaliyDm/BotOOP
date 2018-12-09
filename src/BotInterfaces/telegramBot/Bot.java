package BotInterfaces.telegramBot;

import mysqlWork.Getters.TelegramSessionGetter;
import mysqlWork.Setters.TelegramSessionSetter;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import constants.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class Bot extends TelegramLongPollingBot {
    private static Bot bot;
    static Logger log = Logger.getLogger(Bot.class.getName());
    static final Map<Long, UserThread> users = new HashMap<>();
    static TelegramSessionSetter dataBaseSetter = new TelegramSessionSetter();
    public static TelegramSessionGetter dataBaseGetter = new TelegramSessionGetter();

    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().readConfiguration(Bot.class.getResourceAsStream("logging.properties"));
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        bot = new Bot();
        try {
            api.registerBot(bot);
        } catch (TelegramApiException e){
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        ControlThread controlThread = new ControlThread();
        controlThread.run();
    }

    void sendMessage(String text, Long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        message.setText(text);
        try {
            sendMessage(message);
        } catch (TelegramApiException e){
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        System.out.println(chatId);
        synchronized (users) {
            if(!users.containsKey(chatId)){
                try {
                    var session = dataBaseGetter.getUserSession(chatId);
                    users.put(chatId, new UserThread(bot, chatId));
                    if (session != null){
                        users.get(chatId).UserSession.setSession(session.UserQuestions, session.Score);
                    }
                    users.get(chatId).start();
                } catch (IOException e) {
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            }
            users.get(chatId).setMessagesQueue(message.getText());
        }
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