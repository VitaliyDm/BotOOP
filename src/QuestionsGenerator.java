import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

final class Question{
    String text;
    String answer;
    String comment;
}

public final class QuestionsGenerator {
    private final File questionsBase;

    public QuestionsGenerator(){
        questionsBase = new File("src/question_base.json");
        //GetQuestions();
    }

    private HashMap<String, Question> GetQuestions(){
        //Чтение вопросов из файла
        var gson = new Gson();
        var questions = new HashMap<String, Question>();
        try (var reader = new FileInputStream(questionsBase)){
            var data = new byte[(int) questionsBase.length()];
            reader.read(data);
            questions = gson.fromJson(new String(data), questions.getClass());
        } catch (Exception e){
            //TODO: Написать обработку ошибок
        }
        return questions;
    }

    public HashMap<String, Question> GetQuizQuestions(){
        //TODO: Придумать как можно миксовать вопросы и кидать их пользователю
        return GetQuestions();
    }
}
