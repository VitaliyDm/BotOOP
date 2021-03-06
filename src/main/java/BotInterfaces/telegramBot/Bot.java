package BotInterfaces.telegramBot;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import mysqlWork.SessionEntity;
import mysqlWork.SessionInfoFactory;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class Bot extends TelegramLongPollingBot{
    private static final String loggingProperties = "/logging.properties";
    static Logger log = Logger.getLogger(Bot.class.getName());
    Map<Long, UserThread> users = new HashMap<>();
    public SessionInfoFactory sessionInfoFactory;
    private String botToken;

    private static class ConfigStructure{
        String apiToken;
    }

    static {
        ApiContextInitializer.init();
    }

    public Bot() throws IOException {
        LogManager.getLogManager().readConfiguration(Bot.class.getResourceAsStream(loggingProperties));
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            this.setConfig();
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        this.sessionInfoFactory = new SessionInfoFactory();
        try {
            api.registerBot(this);
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
            UserThread currentUserSession =
                    new UserThread(this, chatId, this.sessionInfoFactory.getSessionInfoService());
            currentUserSession.start();
            return currentUserSession;
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }

    private void setConfig() throws IOException{
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

    private class ControlThread implements Runnable{

        @Override
        public void run(){
            while (true){
                checkAndRemoveInactiveThreads();
                try {
                    Thread.sleep(Constants.TIMEOUT);
                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }

        private void checkAndRemoveInactiveThreads(){
            synchronized (users) {
                for (Long sessionId : users.keySet()){
                    TelegramBotUserSession userSession = users.get(sessionId).getUserSession();
                    if (userSession.getUserDialog().getIsEnd()){
                        userSession.sessionInfoService.delete(userSession.getCurrentChatId());
                        userSession.sessionInfoService.saveAndClose();
                        users.remove(sessionId);
                        continue;
                    }

                    LocalDateTime currentTime = LocalDateTime.now();
                    if (users.get(sessionId).getLastActivityTime()
                            .plusSeconds(Constants.TIMEOUT)
                            .isAfter(currentTime)){
                        userSession.saveSession();
                        userSession.sessionInfoService.saveAndClose();
                        users.remove(sessionId);
                    }
                }
            }
        }
    }

}