package BotInterfaces.telegramBot;

import java.util.ArrayList;
import java.util.HashMap;

public class ControlThread implements Runnable{
    private HashMap<Long, UserThread> activeThreads = new HashMap<>();

    public ControlThread(HashMap<Long, UserThread> usersThreads){
        activeThreads = usersThreads;
    }

    public void run(){

    }

    private void checkAndRemoveInactiveThreads(){
        for (var sessionId : activeThreads.keySet()){

        }
    }
}
