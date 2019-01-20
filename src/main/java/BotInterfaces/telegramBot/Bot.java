package BotInterfaces.telegramBot;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import mysqlWork.SessionEntity;
import mysqlWork.SessionInfoService;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import constants.Constants;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class Bot extends TelegramLongPollingBot {
    private static final String loggingProperties = "./logging.properties";

    private static Bot bot;
    static Logger log = Logger.getLogger(Bot.class.getName());
    static Map<Long, UserThread> users = new HashMap<>();
    public static SessionInfoService dbServise = new SessionInfoService();

    private static String botToken;

    private static class ConfigStructure{
        public String apiToken;
    }

    public static void main(String[] args) throws IOException {
        LogManager.getLogManager().readConfiguration(Bot.class.getResourceAsStream(loggingProperties));
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
            execute(message);
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
            UserThread userSession = users.computeIfAbsent(chatId, id -> createUserSession(id));
            userSession.setMessagesQueue(message.getText());
        }
    }

    private UserThread createUserSession(long chatId){
        try {
            UserThread currentUserSession = new UserThread(bot, chatId);
            SessionEntity session = dbServise.get(chatId);
            if (session != null){
                currentUserSession.UserSession.setSession(session.getUserQuestions(), session.getScore());
            }
            currentUserSession.start();
            return currentUserSession;
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }

    private static void setConfig() throws IOException{
        File configFile = new File(Constants.PATH_TO_BOT_CONFIG);
        Gson gson = new Gson();
        ConfigStructure config = new ConfigStructure();
        JsonReader reader = new JsonReader(new FileReader(configFile));
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