//<<<<<<< HEAD:src/test/java/SerializationSessionTest.java
import BotInterfaces.telegramBot.Bot;
import BotInterfaces.telegramBot.TelegramBotUserSession;
import constants.Constants;
import mysqlWork.SessionEntity;
import org.junit.Before;
import org.junit.Test;
import questions.QuestionsGenerator;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SerializationSessionTest {
    private Long userId;
    private String questionId;
    private int score;
    private Bot bot;

    @Before
    public void getDefaultValues(){
        userId = 1234567L;
        questionId = "1.1, 1.2";
        score = 10;
        bot = new Bot();
    }

    @Test
    public void SafeSessionTest() throws IOException {
        TelegramBotUserSession session = new TelegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS), bot, userId);
        session.setSession(questionId, score);
        session.saveSession();

        SessionEntity serialaizedSession = bot.dbServise.get(userId);
        assertEquals((int)serialaizedSession.getScore(), score);
        assertEquals(serialaizedSession.getUserQuestions(), questionId);
    }
}
//=======
//package tests;
//
//import BotInterfaces.telegramBot.Bot;
//import BotInterfaces.telegramBot.TelegramBotUserSession;
//import constants.Constants;
//import org.junit.Before;
//import org.junit.Test;
//import questions.QuestionsGenerator;
//
//import java.io.IOException;
//
//import static org.junit.Assert.assertEquals;
//
////public class SerializationSessionTest {
////    private Long userId;
////    private String questionId;
////    private int score;
////    private Bot bot;
////
////    @Before
////    public void getDefaultValues(){
////        userId = 1234567L;
////        questionId = "1.1, 1.2";
////        score = 10;
////        bot = new Bot();
////    }
////
////    @Test
////    public void SafeSessionTest() throws IOException {
////        var session = new TelegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS), bot, userId);
////        session.setSession(questionId, score);
////        session.saveSession();
////
////        var serialaizedSession = bot.dataBaseGetter.getUserSession(userId);
////        assertEquals(serialaizedSession.Score, score);
////        assertEquals(serialaizedSession.UserQuestions, questionId);
////    }
////}
//>>>>>>> 78a099030758b07e6ab83209be46e1b7223e74ad:src/tests/SerializationSessionTest.java
