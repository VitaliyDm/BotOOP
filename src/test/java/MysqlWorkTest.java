import mysqlWork.SessionEntity;
import mysqlWork.SessionInfoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MysqlWorkTest {
    private Long userId;
    private String questionId;
    private int score;
    private SessionInfoService dbServise = new SessionInfoService();
    private SessionEntity sessionEntity = new SessionEntity().sessionEntity(0, 0, "");

    @Before
    public void getDefaultValues(){
        userId = 1234567L;
        questionId = "1.34";
        score = 10;
        sessionEntity.setChatId(userId);
        sessionEntity.setScore(score);
        sessionEntity.setUserQuestions(questionId);
        dbServise.add(sessionEntity);
    }

    @After
    public void deleteUser(){
        try {
            dbServise.delete(userId);
        } catch (IllegalArgumentException ignored){ }
    }

    @Test
    public void setDataToDataBaseTest(){
        SessionEntity session = dbServise.get(userId);
        assertEquals(questionId, session.getUserQuestions());
        assertEquals(score, (int)session.getScore());
    }

    @Test
    public void updateDataInDataBaseTest(){
        int newScore = 20;
        sessionEntity.setScore(newScore);
        dbServise.update(sessionEntity);
        SessionEntity session = dbServise.get(userId);
        assertEquals(questionId, session.getUserQuestions());
        assertEquals(newScore, (int)session.getScore());
    }

    @Test
    public void deleteDataInDataBase(){

        dbServise.delete(userId);
        SessionEntity session = dbServise.get(userId);
        Integer a = null;
        try
        {
            a = session.getScore();
        }catch (IllegalArgumentException | NullPointerException ignored){ }
        assertNull(a);
    }

}
