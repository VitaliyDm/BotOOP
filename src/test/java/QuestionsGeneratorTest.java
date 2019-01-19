import org.junit.Before;
import org.junit.Test;
import questions.QuestionsGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class QuestionsGeneratorTest {
    private QuestionsGenerator questionsGenerator = null;

    @Before
    public void getDefaultValues(){
        try{
            questionsGenerator = new QuestionsGenerator("src/test/java/testQuestions.json");
        } catch (IOException e){
            //pass
        }
    }

    @Test(expected = com.google.gson.JsonSyntaxException.class)
    public void testIncorrectFileFormat() throws IOException{
        new QuestionsGenerator("src/test/java/incorrectFile.json");
    }

    @Test(expected = FileNotFoundException.class)
    public void testIncorrectPath() throws IOException{
        new QuestionsGenerator("src/incorrectPath.json");
    }

    @Test
    public void questionsGeneration(){
        ArrayList generatedQuestions = questionsGenerator.getQuizQuestions();
        assertEquals(2, generatedQuestions.size());
    }
}