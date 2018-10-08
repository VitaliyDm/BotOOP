package questions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;

final class QuestionsTopic{
    public List<Question> values;
}

final class Question{
    public String question;
    public List<String> answer;
    public String comment;
    public String id;
}

public final class QuestionsGenerator {
    private final File questionsBase = new File("src/questions/question_base.json");
    private static QuestionsTopic questions;
    public static HashMap<String, Question> AllQuestions = new HashMap<>();

    public QuestionsGenerator() throws IOException {
        //Чтение вопросов из файла
        var gson = new Gson();
        questions = new QuestionsTopic();
        var reader = new FileInputStream(questionsBase);
        var data = new byte[(int) questionsBase.length()];
        reader.read(data);
        questions = gson.fromJson(new String(data), QuestionsTopic.class);
        for (var question : questions.values)
            AllQuestions.put(question.id, question);
    }

    public ArrayList<String> GetQuizQuestions(){
        //TODO: Придумать как можно миксовать вопросы и кидать их пользователю
       var res = new ArrayList<String>();
       for (var question : questions.values)
           res.add(question.id);
       return res;
    }
}
