package BotInterfaces.telegramBot;

import constants.Constants;

import java.util.HashMap;

public class ControlThread implements Runnable{
    private HashMap<Long, UserThread> activeThreads = new HashMap<>();

    public ControlThread(HashMap<Long, UserThread> usersThreads){
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
        }
    }
}
