package BotInterfaces;

import questions.QuestionHelper;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class UserSession {
    protected Dialog userDialog;
    protected QuestionHelper questionHelper;
    public boolean GameStarted = false;

    public UserSession(QuestionsGenerator questionsGenerator) {
        questionHelper = new QuestionHelper(questionsGenerator);
    }

    public abstract void saveSession();

    public void setSession(String serializedQuestionsId, int score){
        questionHelper.setQuestionsById(new ArrayList<>(Arrays.asList(serializedQuestionsId.split(", "))));
        questionHelper.setScore(score);
        GameStarted = true;
    }

    public abstract void startDialog() throws IOException, InterruptedException;
    public abstract void startDialog(boolean gameStarted) throws IOException, InterruptedException;
}
