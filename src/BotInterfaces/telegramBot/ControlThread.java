package BotInterfaces.telegramBot;

import constants.Constants;

import java.util.Calendar;
import java.util.Map;

public class ControlThread implements Runnable{
    private final Map<Long, UserThread> activeThreads;

    public ControlThread(Map<Long, UserThread> usersThreads){
        activeThreads = usersThreads;
    }

    public void run(){
        while (true){
            checkAndRemoveInactiveThreads();
            try {
                Thread.sleep(Constants.TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkAndRemoveInactiveThreads(){
        for (var sessionId : activeThreads.keySet()){
            var userSession = activeThreads.get(sessionId).UserSession;
            if (userSession.getUserDialog().getIsEnd()){
                Bot.dataBaseSetter.deleteDataInDataBase(userSession.getCurrentChatId());
                activeThreads.remove(sessionId);
                continue;
            }

            var currentTime = Calendar.getInstance().getTime().getTime();
            if (activeThreads.get(sessionId).LastActivityTime + Constants.TIMEOUT< currentTime
                    || activeThreads.get(sessionId).UserSession.getUserDialog().getIsEnd()){
                activeThreads.get(sessionId).UserSession.saveSession();
                activeThreads.remove(sessionId);
            }
        }
    }
}
