package questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionHelper {
    private ArrayList<Float> userQuestions = new ArrayList<>();
    private float currentQuestionId;
    private QuestionsGenerator generator;

    public QuestionHelper(QuestionsGenerator questionsGenerator) {
        generator = questionsGenerator;
        userQuestions = generator.GetQuizQuestions();
    }

    public String getNextQuestion() {
        currentQuestionId = userQuestions.iterator().next();
        return generator.AllQuestions.get(currentQuestionId).question;
    }

    public String getHelp() {
        return generator.AllQuestions.get(currentQuestionId).comment;
    }

    public boolean checkAnswer(String answer) {
        var ansCorrect = false;
        for (var ans : generator.AllQuestions.get(currentQuestionId).answer)
            if (answer.toLowerCase().equals(ans.toLowerCase()))
                ansCorrect = true;
        if (ansCorrect)
            userQuestions.remove(currentQuestionId);
        return ansCorrect;
    }
}
