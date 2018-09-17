import java.io.File;
import java.io.FileInputStream;
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
    }

    private void GetQuestions(){
        var gson = new Gson();
        var questions = new HashMap<String, HashMap<String, Question>>();
        try (var reader = new FileInputStream(questionsBase)){
            questions = gson.fromJson(reader.readAllBytes().toString(), questions.getClass());
        } catch (Exception e){
            //TODO: Написать обработку ошибок
        }
    }
}
