package BotInterfaces.telegramBot;

import constants.Constants;

import java.util.Calendar;
import java.util.logging.Level;

public class ControlThread implements Runnable{
    public void run(){
        while (true){
            checkAndRemoveInactiveThreads();
            try {
                Thread.sleep(Constants.TIMEOUT);
            } catch (InterruptedException e) {
                Bot.log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    private void checkAndRemoveInactiveThreads(){
        synchronized (Bot.users) {
            for (var sessionId : Bot.users.keySet()){
                var userSession = Bot.users.get(sessionId).UserSession;
                if (userSession.getUserDialog().getIsEnd()){
                    Bot.sessionDAO.deleteSessionInfo(userSession.getCurrentChatId());
                    Bot.users.remove(sessionId);
                    continue;
                }

                var currentTime = Calendar.getInstance().getTime().getTime();
                if (Bot.users.get(sessionId).LastActivityTime + Constants.TIMEOUT< currentTime){
                    userSession.saveSession();
                    Bot.users.remove(sessionId);
                }
            }
        }
    }
}
