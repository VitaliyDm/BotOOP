package questions;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final class QuestionsTopic{
    public List<Question> values;
}

public final class QuestionsGenerator {
    private final File questionsBase;
    private static QuestionsTopic questions;
    public static HashMap<String, Question> AllQuestions = new HashMap<>();

    public QuestionsGenerator(String pathToQuestions) throws IOException {
        //Чтение вопросов из файла
        questionsBase = new File(pathToQuestions);
        var gson = new Gson();
        questions = new QuestionsTopic();
        var reader = new JsonReader(new FileReader(questionsBase));
        questions = gson.fromJson(reader, QuestionsTopic.class);
        for (var question : questions.values)
            AllQuestions.put(question.id, question);
    }

    public ArrayList<Question> getQuizQuestions(){
       var res = new ArrayList<Question>();
       for (var question : questions.values)
           res.add(question);
       return res;
    }

    public ArrayList<Question> getQuestionsById(ArrayList<String> questionsId){
        var result = new ArrayList<Question>();
        for (var question : questions.values)
            for (var id : questionsId)
                if (id == question.id)
                    result.add(question);
        return result;
    }
}
