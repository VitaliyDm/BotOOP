package questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionHelper {
    private ArrayList<Float> userQuestions = new ArrayList<>();
    private float currentQuestionId;

    public QuestionHelper() {
        userQuestions = QuestionsGenerator.GetQuizQuestions();
    }

    public String GetNextQuestion() {
        currentQuestionId = userQuestions.iterator().next();
        return QuestionsGenerator.AllQuestions.get(currentQuestionId).question;
    }

    public String GetHelp() {
        return QuestionsGenerator.AllQuestions.get(currentQuestionId).comment;
    }

    public boolean CheckAnswer(String answer) {
        var ansCorrect = false;
        for (var ans : QuestionsGenerator.AllQuestions.get(currentQuestionId).answer)
            if (answer.toLowerCase().equals(ans.toLowerCase()))
                ansCorrect = true;
        if (ansCorrect)
            userQuestions.remove(currentQuestionId);
        return ansCorrect;
    }
}
