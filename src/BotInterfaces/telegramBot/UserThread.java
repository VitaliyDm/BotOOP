package BotInterfaces.telegramBot;

import constants.Constants;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.net.Socket;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

class UserThread extends Thread {

    private telegramBotUserSession user;
    public Long LastActivityTime;

    void set_messagesQueue(String message){
        LastActivityTime = Calendar.getInstance().getTime().getTime();
        user.set_messagesQueue(message);
    }

    UserThread(Bot bot, Long chatId) throws IOException {
        user = new telegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS), bot, chatId);
    }

    public void run() {
        try {
            user.startDialog();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
