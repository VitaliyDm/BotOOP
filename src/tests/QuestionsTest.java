package tests;

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

    public QuestionsResolvers getDefaultValues(){
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
        var questionsResolvers = getDefaultValues();
        var generatedQuestions = questionsResolvers.generator.getQuizQuestions();
        var generatedQuestionsLength = generatedQuestions.size();
        assertEquals(2, generatedQuestionsLength);
    }

    @Test
    public void testGettingQuestion(){
        var questionsResolvers = getDefaultValues();
        var question = questionsResolvers.helper.getNextQuestion();
        assertEquals("Герой Олдоса Хаксли говорит о своей возлюбленной: \"Она пробудила во мне интерес к культуре. Я был наполовину дикарем, когда попал к ней в руки\". Далее герой произносит три слова, которые являются аллюзией на название произведения, впервые опубликованного в 1899 году. Назовите это произведение.",
                        question);
        question = questionsResolvers.helper.getNextQuestion();
        assertEquals("В 1848 году русская гвардия должна была участвовать в подавлении Венгерского восстания, однако в итоге вернулась в казармы, не вступая в боевые действия. Петербургские остряки предположили, что на медали в честь этого похода будут выбиты три слова. Эти три слова входят в название известного произведения. Какого?",
                        question);
    }

    @Test
    public void testGettingQuestionHelp(){
        var questionsResolvers = getDefaultValues();
        questionsResolvers.helper.getNextQuestion();
        assertEquals( "Герой, перефразируя Киплинга, говорит о \"бремени белой женщины\"",
                        questionsResolvers.helper.getHelp());
        questionsResolvers.helper.getNextQuestion();
        assertEquals("Или Туда и обратно", questionsResolvers.helper.getHelp());
    }

    @Test
    public void testCheckingAnswers(){
        var questionsResolver = getDefaultValues();
        questionsResolver.helper.getNextQuestion();
        assertTrue(questionsResolver.helper.checkAnswer("Бремя белого человека"));
        assertTrue(questionsResolver.helper.checkAnswer("Бремя белых"));
        assertTrue(questionsResolver.helper.checkAnswer("Бремя белых людей"));
        assertTrue(questionsResolver.helper.checkAnswer("БрЕмЯ БЕЛЫХ людей"));
        questionsResolver.helper.getNextQuestion();
        assertTrue(questionsResolver.helper.checkAnswer("Хоббит"));
        assertTrue(questionsResolver.helper.checkAnswer("ХоББит"));
    }
}