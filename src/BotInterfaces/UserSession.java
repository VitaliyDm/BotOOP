package BotInterfaces;

import BotInterfaces.ConsoleBot.ConsoleBotDialog;
import BotInterfaces.Dialog;
import questions.QuestionHelper;
import questions.QuestionsGenerator;

import java.io.*;

public abstract class UserSession {
    protected int userScore;
    protected Dialog userDialog;
    protected QuestionHelper questionHelper;

    public UserSession(QuestionsGenerator questionsGenerator) {
        userScore = 0;
        questionHelper = new QuestionHelper(questionsGenerator);
    }

    public abstract void startDialog() throws IOException;
}
