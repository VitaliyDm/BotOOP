package questions;

import java.util.Collection;
import java.util.HashMap;

public class QuestionHelper {
    private HashMap<Float, Question> userQuestions = new HashMap<>();
    private float currentQuestionId;

    public QuestionHelper(){
        var allQuestions = QuestionsGenerator.GetQuizQuestions();
        for (var question : allQuestions){
            userQuestions.put(Float.parseFloat(question.id), question);
        }
    }

    public String GetNextQuestion(){
        currentQuestionId = userQuestions.keySet().iterator().next();
        return userQuestions.get(currentQuestionId).text;
    }

    public String GetHelp(){
        return userQuestions.get(currentQuestionId).comment;
    }

    public boolean CheckAnswer(String answer){
        var ansCorrect = answer.toLowerCase().equals(userQuestions.get(currentQuestionId).answer.toLowerCase());
        if (ansCorrect)
            userQuestions.remove(currentQuestionId);
        return ansCorrect;
    }
}
