package BotInterfaces.ConsoleBot;

import BotInterfaces.UserSession;
import questions.QuestionsGenerator;

import java.io.IOException;

public class ConsoleBotUserSession extends UserSession {
    public ConsoleBotUserSession(QuestionsGenerator questionsGenerator) throws IOException, InterruptedException {
        super(questionsGenerator);
        userDialog = new ConsoleBotDialog(questionHelper);
        startDialog();
    }

    @Override
    public void serializeSession() {
    }

    @Override
    public void startDialog() throws IOException, InterruptedException {
        userDialog.mainDialog();
    }
}
