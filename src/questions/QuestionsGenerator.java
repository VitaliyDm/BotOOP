package questions;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

final class Question{
    String text;
    String answer;
    String comment;
    String id;
}

public final class QuestionsGenerator {
    private static File questionsBase = new File("src/questions/question_base.json");

    private static HashMap<String, ArrayList<Question>> GetQuestions(){
        //Чтение вопросов из файла
        var gson = new Gson();
        var questions = new HashMap<String, ArrayList<Question>>();
        try (var reader = new FileInputStream(questionsBase)){
            var data = new byte[(int) questionsBase.length()];
            reader.read(data);
            questions = gson.fromJson(new String(data), questions.getClass());
        } catch (Exception e){
            //TODO: Написать обработку ошибок
        }
        return questions;
    }

    public static ArrayList<Question> GetQuizQuestions(){
        //TODO: Придумать как можно миксовать вопросы и кидать их пользователю
       return GetQuestions().get("1");
    }
}
