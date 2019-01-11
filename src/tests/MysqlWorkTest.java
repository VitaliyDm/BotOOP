package tests;

import mysqlWork.Getters.TelegramSessionGetter;
import mysqlWork.Setters.TelegramSessionSetter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MysqlWorkTest {
    private Long userId;
    private String questionId;
    private int score;
    private TelegramSessionSetter dataBaseSetter;
    private TelegramSessionGetter dataBaseGetter;

    @Before
    public void getDefaultValues(){
        userId = 1234567L;
        questionId = "1.34";
        score = 10;
        dataBaseSetter = new TelegramSessionSetter();
        dataBaseGetter = new TelegramSessionGetter();
        dataBaseSetter.deleteDataInDataBase(userId);
        dataBaseSetter.setDataToDataBase(userId, questionId, score);
    }

    @After
    public void deleteUser(){
        dataBaseSetter.deleteDataInDataBase(userId);
    }

    @Test
    public void setDataToDataBaseTest(){
        var session = dataBaseGetter.getUserSession(userId);
        assertEquals(questionId, session.getUserQuestions());
        assertEquals(java.util.Optional.ofNullable(score), session.getScore());
    }

    @Test
    public void updateDataInDataBaseTest(){
        int newScore = 20;
        dataBaseSetter.updateDataInDataBase(userId, questionId, newScore);
        var session = dataBaseGetter.getUserSession(userId);
        assertEquals(questionId, session.getUserQuestions());
        assertEquals(java.util.Optional.ofNullable(newScore), session.getScore());
    }

    @Test
    public void deleteDataInDataBase(){
        dataBaseSetter.deleteDataInDataBase(userId);
        var session = dataBaseGetter.getUserSession(userId);
        try {
            var a = session.getScore();
        }catch (NullPointerException ignored){ }
    }

}
