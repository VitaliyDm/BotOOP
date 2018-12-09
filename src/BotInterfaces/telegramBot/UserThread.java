package BotInterfaces.telegramBot;

import constants.Constants;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;

class UserThread extends Thread {

    public TelegramBotUserSession UserSession;
    public Long LastActivityTime;

    void setMessagesQueue(String message){
        LastActivityTime = Calendar.getInstance().getTime().getTime();
        UserSession.setMessagesQueue(message);
    }

    UserThread(Bot bot, Long chatId) throws IOException {
        LastActivityTime = Calendar.getInstance().getTime().getTime();
        UserSession = new TelegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS), bot, chatId);
    }

    public void run() {
        try {
            UserSession.startDialog();
        } catch (IOException | InterruptedException e) {
            Bot.log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
