package BotInterfaces.telegramBot;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
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

import java.io.File;
import java.io.FileReader;
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

    private static String botToken;

    private static class ConfigStructure{
        public String apiToken;
    }

    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().readConfiguration(Bot.class.getResourceAsStream("logging.properties"));
        try {
            setConfig();
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
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
        System.out.println('s' + chatId);
        synchronized (users) {
            users.computeIfAbsent(chatId, key -> createUserSession(key));
            var session = dataBaseGetter.getUserSession(chatId);
            var currentUserSession = users.get(chatId);
            if (session != null){
                currentUserSession.UserSession.setSession(session.UserQuestions, session.Score);
            }
            currentUserSession.start();
            currentUserSession.setMessagesQueue(message.getText());
        }
    }

    private UserThread createUserSession(long chatId){
        try {
            return new UserThread(bot, chatId);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }

    private static void setConfig() throws IOException{
        var configFile = new File(Constants.PATH_TO_BOT_CONFIG);
        var gson = new Gson();
        var config = new ConfigStructure();
        var reader = new JsonReader(new FileReader(configFile));
        ConfigStructure appConfig = gson.fromJson(reader, ConfigStructure.class);
        botToken = appConfig.apiToken;
    }

    @Override
    public String getBotUsername() {
        return Constants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}