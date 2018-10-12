package tests;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import questions.QuestionHelper;
import questions.QuestionsGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class QuestionsTest {
    private class QuestionsResolvers{
        public QuestionHelper helper;
        public QuestionsGenerator generator;

        public  QuestionsResolvers(QuestionsGenerator questionsGenerator, QuestionHelper questionHelper){
            helper = questionHelper;
            generator = questionsGenerator;
        }
    }

    public QuestionsResolvers setDefaultValues(){
        QuestionsGenerator questionGenerator = null;
        QuestionHelper questionHelper = null;
        try{
            questionGenerator = new QuestionsGenerator("src/tests/testQuestions.json");
            questionHelper = new QuestionHelper(questionGenerator);
        } catch (IOException e){
            //pass
        }
        return new QuestionsResolvers(questionGenerator, questionHelper);
    }

    @Test(expected = com.google.gson.JsonSyntaxException.class)
    public void testIncorrectFileFormat() throws IOException{
        new QuestionsGenerator("src/tests/incorrectFile.json");
    }

    @Test(expected = FileNotFoundException.class)
    public void testIncorrectPath() throws IOException{
        new QuestionsGenerator("src/incorrectPath.json");
    }

    @Test
    public void questionsGeneration(){
        var questionsResolvers = setDefaultValues();
        var generatedQuestions = questionsResolvers.generator.getQuizQuestions();
        var generatedQuestionsLength = generatedQuestions.size();
        assertEquals(generatedQuestionsLength, 2);
    }
}