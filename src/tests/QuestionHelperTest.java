package tests;

import org.junit.Before;
import org.junit.Test;
import questions.QuestionHelper;
import questions.QuestionsGenerator;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuestionHelperTest {
    private QuestionHelper questionHelper = null;
    private QuestionsGenerator questionsGenerator = null;

    @Before
    public void getDefaultValues(){
        try{
            questionsGenerator = new QuestionsGenerator("src/tests/testQuestions.json");
            questionHelper = new QuestionHelper(questionsGenerator);
        } catch (IOException e){
            //pass
        }
    }

    @Test
    public void testGettingQuestion(){
        var question = questionHelper.getNextQuestion();
        assertEquals("Герой Олдоса Хаксли говорит о своей возлюбленной: \"Она пробудила во мне интерес к культуре. Я был наполовину дикарем, когда попал к ней в руки\". Далее герой произносит три слова, которые являются аллюзией на название произведения, впервые опубликованного в 1899 году. Назовите это произведение.",
                question);
        question = questionHelper.getNextQuestion();
        assertEquals("В 1848 году русская гвардия должна была участвовать в подавлении Венгерского восстания, однако в итоге вернулась в казармы, не вступая в боевые действия. Петербургские остряки предположили, что на медали в честь этого похода будут выбиты три слова. Эти три слова входят в название известного произведения. Какого?",
                question);
    }

    @Test
    public void testGettingQuestionHelp(){
        questionHelper.getNextQuestion();
        assertEquals( "Герой, перефразируя Киплинга, говорит о \"бремени белой женщины\"",
                questionHelper.getHelp());
        questionHelper.getNextQuestion();
        assertEquals("Или Туда и обратно", questionHelper.getHelp());
    }

    @Test
    public void testCheckingAnswers(){
        questionHelper.getNextQuestion();
        assertTrue(questionHelper.checkAnswer("Бремя белого человека"));
        assertTrue(questionHelper.checkAnswer("Бремя белых"));
        assertTrue(questionHelper.checkAnswer("Бремя белых людей"));
        assertTrue(questionHelper.checkAnswer("БрЕмЯ БЕЛЫХ людей"));
        questionHelper.getNextQuestion();
        assertTrue(questionHelper.checkAnswer("Хоббит"));
        assertTrue(questionHelper.checkAnswer("ХоББит"));
    }
}
