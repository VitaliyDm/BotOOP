package questions;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import com.google.gson.Gson;

final class QuestionsTopic{
    public List<Question> values;
}

final class Question{
    public String question;
    public List<String> answer;
    public String comment;
    public Float id;
}

public final class QuestionsGenerator {
    private static File questionsBase = new File("src/questions/question_base.json");
    private static QuestionsTopic questions;
    public static HashMap<Float, Question> AllQuestions = new HashMap<>();

    public QuestionsGenerator(){
        //Чтение вопросов из файла
        var gson = new Gson();
        questions = new QuestionsTopic();
        try (var reader = new FileInputStream(questionsBase)){
            var data = new byte[(int) questionsBase.length()];
            reader.read(data);
            questions = gson.fromJson(new String(data), QuestionsTopic.class);
        } catch (Exception e){
            System.out.println("err");
            //TODO: Написать обработку ошибок
        }
        for (var question : questions.values)
            AllQuestions.put(question.id, question);
    }

    public static ArrayList<Float> GetQuizQuestions(){
        //TODO: Придумать как можно миксовать вопросы и кидать их пользователю
       var res = new ArrayList<Float>();
       for (var question : questions.values)
           res.add(question.id);
       return res;
    }
}
