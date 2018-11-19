package BotInterfaces.telegramBot;

import BotInterfaces.ConsoleBot.ConsoleBotDialog;
import BotInterfaces.UserSession;
import questions.QuestionsGenerator;

import java.io.IOException;

public class telegramBotUserSession extends UserSession {
    public telegramBotUserSession(QuestionsGenerator questionsGenerator) throws IOException {
        super(questionsGenerator);

        userDialog = new telegramBotDialog(questionHelper);
        startDialog();
    }

    @Override
    public void startDialog() throws IOException {
        userDialog.mainDialog();
    }
}
