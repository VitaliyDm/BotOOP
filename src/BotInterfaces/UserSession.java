package BotInterfaces;

import questions.QuestionHelper;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class UserSession {
    protected Dialog userDialog;
    protected QuestionHelper questionHelper;

    public UserSession(QuestionsGenerator questionsGenerator) {
        questionHelper = new QuestionHelper(questionsGenerator);
    }

    public abstract void saveSession();

    public void setSession(String serializedQuestionsId, int score){
        questionHelper.setQuestionsById(new ArrayList<>(Arrays.asList(serializedQuestionsId.split(", "))));
        questionHelper.setScore(score);
    }

    public abstract void startDialog() throws IOException, InterruptedException;
}
