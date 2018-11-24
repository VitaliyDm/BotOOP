package BotInterfaces.telegramBot;

import constants.Constants;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;

class UserThread implements Runnable {

    private telegramBotUserSession user;

    void set_messagesQueue(String message){
        user.set_messagesQueue(message);
    }

    void set_bot(Bot bot){
        user.set_bot(bot);
    }

    void set_chatId(Long chatId){
        user.set_chatId(chatId);
    }

    public void run() {
        try {
            user = new telegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
