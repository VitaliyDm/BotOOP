package BotInterfaces.telegramBot;

import constants.Constants;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.util.Calendar;

class UserThread extends Thread {

    public TelegramBotUserSession UserSession;
    public Long LastActivityTime;

    void setMessagesQueue(String message){
        LastActivityTime = Calendar.getInstance().getTime().getTime();
        UserSession.setMessagesQueue(message);
    }

    UserThread(Bot bot, Long chatId) throws IOException {
        UserSession = new TelegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS), bot, chatId);
    }

    public void run() {
        try {
            UserSession.startDialog(UserSession.GameStarted);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
