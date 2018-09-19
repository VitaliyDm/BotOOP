package questions;

import java.util.HashMap;

public class QuestionHelper {
    private HashMap<Float, Question> userQuestions = new HashMap<>();
    private float currentQuestionId;

    public QuestionHelper(){
        userQuestions = QuestionsGenerator.GetQuizQuestions();
    }

    public String GetNextQuestion(){
        currentQuestionId = userQuestions.keySet().iterator().next();
        return userQuestions.get(currentQuestionId).question;
    }

    public String GetHelp(){
        return userQuestions.get(currentQuestionId).comment;
    }

    public boolean CheckAnswer(String answer){
        var ansCorrect = false;
        for (var ans : userQuestions.get(currentQuestionId).answer )
            if (answer.toLowerCase().equals(ans.toLowerCase()))
                ansCorrect = true;
        if (ansCorrect)
            userQuestions.remove(currentQuestionId);
        return ansCorrect;
    }
}
