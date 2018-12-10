package BotInterfaces.ConsoleBot;

import BotInterfaces.Dialog;
import BotInterfaces.UserSession;
import questions.QuestionsGenerator;

import java.io.IOException;

public class ConsoleBotUserSession extends UserSession {
    public ConsoleBotUserSession(QuestionsGenerator questionsGenerator) throws IOException, InterruptedException {
        super(questionsGenerator);
        var ioManager = new ConsoleIOManager();
        userDialog = new Dialog(questionHelper, ioManager);
        startDialog(false);
    }

    @Override
    public void saveSession() {
    }

    @Override
    public void startDialog(boolean gameStarted) throws IOException, InterruptedException {
        userDialog.mainDialog(gameStarted);
    }
}
