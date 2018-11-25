package BotInterfaces;

import questions.QuestionHelper;
import questions.QuestionsGenerator;

import java.io.*;

public abstract class UserSession {
    protected Dialog userDialog;
    protected QuestionHelper questionHelper;

    public UserSession(QuestionsGenerator questionsGenerator) {
        questionHelper = new QuestionHelper(questionsGenerator);
    }

    public abstract void serializeSession();

    public abstract void startDialog() throws IOException, InterruptedException;
}
