package BotInterfaces.telegramBot;

import constants.Constants;
import mysqlWork.SessionEntity;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

class UserThread extends Thread {

    private TelegramBotUserSession UserSession;
    TelegramBotUserSession getUserSession(){return  UserSession;}
    public void setUserSession(TelegramBotUserSession userSession){
        this.UserSession = userSession;
    }

    private LocalDateTime LastActivityTime;
    LocalDateTime getLastActivityTime(){return LastActivityTime;}
    public void updateLastActivityTimeToNow(){
        this.LastActivityTime = LocalDateTime.now();
    }

    private static final String loggingProperties = "/logging.properties";
    private static Logger log = Logger.getLogger(Bot.class.getName());

    void setMessagesQueue(String message){
        updateLastActivityTimeToNow();
        UserSession.setMessagesQueue(message);
    }

    UserThread(Bot bot, Long chatId) throws IOException {
        LogManager.getLogManager().readConfiguration(UserThread.class.getResourceAsStream(loggingProperties));
        updateLastActivityTimeToNow();
        UserSession = new TelegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS), bot, chatId);
        SessionEntity session = bot.dbServise.get(chatId);
        if (session != null){
            UserSession.setSession(session.getUserQuestions(), session.getScore());
        }
    }

    public void run() {
        try {
            UserSession.startDialog(UserSession.GameStarted);
        } catch (IOException | InterruptedException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
