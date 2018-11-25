package BotInterfaces.telegramBot;

import constants.Constants;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.util.Calendar;

class UserThread extends Thread {

    private TelegramBotUserSession user;
    public Long LastActivityTime;

    void set_messagesQueue(String message){
        LastActivityTime = Calendar.getInstance().getTime().getTime();
        user.setMessagesQueue(message);
    }

    UserThread(Bot bot, Long chatId) throws IOException {
        user = new TelegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS), bot, chatId);
    }

    public void run() {
        try {
            user.startDialog();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
