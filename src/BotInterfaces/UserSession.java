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

    public void SaveSession(){
        var a = questionHelper.getQuestionsId();
        var b = questionHelper.getScore();
    }

    public abstract void startDialog() throws IOException;
}
