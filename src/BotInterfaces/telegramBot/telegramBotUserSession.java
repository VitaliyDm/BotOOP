package BotInterfaces.telegramBot;

import BotInterfaces.UserSession;
import questions.QuestionsGenerator;

import java.io.IOException;

public class telegramBotUserSession extends UserSession {
    public telegramBotUserSession(QuestionsGenerator questionsGenerator, Bot bot, Long chatId) throws IOException {
        super(questionsGenerator);
        userDialog = new telegramBotDialog(questionHelper, bot, chatId);
    }

    @Override
    public void startDialog() throws IOException {
        userDialog.mainDialog();
    }

    void set_messagesQueue(String message){
        ((telegramBotDialog) userDialog).messagesQueue.add(message);
    }
}


