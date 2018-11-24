package BotInterfaces.telegramBot;

import BotInterfaces.UserSession;
import questions.QuestionsGenerator;

import java.io.IOException;

public class telegramBotUserSession extends UserSession {
    public telegramBotUserSession(QuestionsGenerator questionsGenerator) throws IOException {
        super(questionsGenerator);
        userDialog = new telegramBotDialog(questionHelper, bot, chatId);
    }

    @Override
    public void startDialog() throws IOException, InterruptedException {
        userDialog.mainDialog();
    }

    void set_messagesQueue(String message){
        ((telegramBotDialog) userDialog).messagesQueue.add(message);
    }
}


