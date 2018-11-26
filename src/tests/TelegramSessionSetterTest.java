package tests;

import mysqlWork.Getters.TelegramSessionGetter;
import mysqlWork.Setters.TelegramSessionSetter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelegramSessionSetterTest {
    @Test
    public void setDataToDataBaseTest(){
        TelegramSessionSetter dataBaseSetter = new TelegramSessionSetter();
        Long userId = 1234567L;
        String question = "TestString";
        int score = 10;
        dataBaseSetter.setDataToDataBase(userId, question, score);
        TelegramSessionGetter dataBaseGetter = new TelegramSessionGetter();
        var session = dataBaseGetter.getUserSession(userId);
        assertEquals(question, session.UserQuestions);
        assertEquals(score, session.Score);
    }
}
