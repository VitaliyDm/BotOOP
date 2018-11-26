package questions;

import java.util.ArrayList;
import java.util.Iterator;

public class QuestionHelper {
    private ArrayList<Question> userQuestions = new ArrayList<>();
    private Question currentQuestion;
    private QuestionsGenerator generator;
    private Iterator<Question> questionsIterator;
    private int score;

    public QuestionHelper(QuestionsGenerator questionsGenerator) {
        generator = questionsGenerator;
        userQuestions = generator.getQuizQuestions();
        questionsIterator = userQuestions.iterator();
    }

    public void setQuestionsById(ArrayList<String> questionsId){
        userQuestions = generator.getQuestionsById(questionsId);
        questionsIterator = userQuestions.iterator();
    }

    public String getNextQuestion() {
        if (!questionsIterator.hasNext()) {
            questionsIterator = userQuestions.iterator();
        }
        currentQuestion = questionsIterator.next();
        return currentQuestion.question;
    }

    public String getHelp() {
        return currentQuestion.comment;
    }

    public boolean checkAnswer(String answer) {
        var ansCorrect = false;
        for (var ans : currentQuestion.answer)
            if (answer.toLowerCase().equals(ans.toLowerCase()))
                ansCorrect = true;
        if (ansCorrect)
        {
            questionsIterator.remove();
            score += 10;
        }
        return ansCorrect;
    }

    public ArrayList<String> getQuestionsId(){
        var result = new ArrayList<String>();
        for(var question : userQuestions)
            result.add(question.id);
        return result;
    }

    public int getScore(){
        return score;
    }
    public void setScore(int newScore){ score = newScore; }
}
