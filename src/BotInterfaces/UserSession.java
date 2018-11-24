package BotInterfaces;

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

    public void SaveSession(){
        var a = questionHelper.getQuestionsId();
        var b = userScore;
    }

    public abstract void startDialog() throws IOException;
}
