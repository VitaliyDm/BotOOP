package BotInterfaces.telegramBot;

import BotInterfaces.UserSession;
import constants.Constants;
import mysqlWork.SessionInfoService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ControlThread implements Runnable{
    private Map<Long, UserThread> users;
    private SessionInfoService dbServise;

    private static final String loggingProperties = "/logging.properties";
    private static Logger log = Logger.getLogger(Bot.class.getName());

    ControlThread(Map<Long, UserThread> users, SessionInfoService dbServise) throws IOException {
        LogManager.getLogManager().readConfiguration(ControlThread.class.getResourceAsStream(loggingProperties));

        this.users = users;
        this.dbServise = dbServise;
    }
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
                    dbServise.delete(userSession.getCurrentChatId());
                    users.remove(sessionId);
                    continue;
                }

                LocalDateTime currentTime = LocalDateTime.now();
                if (users.get(sessionId).getLastActivityTime().plusSeconds(Constants.TIMEOUT).isAfter(currentTime)){
                        userSession.saveSession();
                    users.remove(sessionId);
                }
            }
        }
    }
}
