package BotInterfaces.ConsoleBot;

import BotInterfaces.UserSession;
import questions.QuestionsGenerator;

import java.io.IOException;

public class ConsoleBotUserSession extends UserSession {
    public ConsoleBotUserSession(QuestionsGenerator questionsGenerator) throws IOException {
        super(questionsGenerator);
        userDialog = new ConsoleBotDialog(questionHelper);
    }

    @Override
    public void startDialog() throws IOException {
        userDialog.mainDialog();
    }
}
