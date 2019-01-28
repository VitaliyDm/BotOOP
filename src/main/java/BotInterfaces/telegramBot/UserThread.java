package BotInterfaces.telegramBot;

import constants.Constants;
import mysqlWork.SessionEntity;
import mysqlWork.SessionInfoFactory;
import mysqlWork.SessionInfoService;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

class UserThread extends Thread {

    public SessionInfoService sessionInfoService;
    private TelegramBotUserSession UserSession;
    TelegramBotUserSession getUserSession(){return  UserSession;}
    private void setUserSession(TelegramBotUserSession userSession){
        this.UserSession = userSession;
    }

    private void unloadUserSession(Bot bot, Long chatId){
        SessionEntity session = sessionInfoService.get(chatId);
        if (session != null){
            UserSession.setSession(session.getUserQuestions(), session.getScore());
        }
    }

    private LocalDateTime LastActivityTime;
    LocalDateTime getLastActivityTime(){return LastActivityTime;}
    private void updateLastActivityTimeToNow(){
        this.LastActivityTime = LocalDateTime.now();
    }

    private static final String loggingProperties = "/logging.properties";
    private static Logger log = Logger.getLogger(Bot.class.getName());

    void setMessagesQueue(String message){
        updateLastActivityTimeToNow();
        UserSession.setMessagesQueue(message);
    }

    UserThread(Bot bot, Long chatId, SessionInfoService sessionInfoService) throws IOException {
        this.sessionInfoService = sessionInfoService;
        LogManager.getLogManager().readConfiguration(UserThread.class.getResourceAsStream(loggingProperties));
        updateLastActivityTimeToNow();
        setUserSession(new TelegramBotUserSession(
                new QuestionsGenerator(Constants.PATH_TO_QUESTIONS), bot, chatId, sessionInfoService));
        unloadUserSession(bot, chatId);
    }

    public void run() {
        try {
            UserSession.startDialog(UserSession.GameStarted);
        } catch (IOException | InterruptedException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
