import mysqlWork.SessionEntity;
import mysqlWork.SessionInfoFactory;
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
    private SessionInfoFactory dbServiseFactory = new SessionInfoFactory();
    private SessionInfoService dbService;
    private SessionEntity sessionEntity = new SessionEntity().sessionEntity(0, 0, "");

    @Before
    public void getDefaultValues(){
        userId = 1234567L;
        questionId = "1.34";
        score = 10;
        sessionEntity.setChatId(userId);
        sessionEntity.setScore(score);
        sessionEntity.setUserQuestions(questionId);
        dbService = dbServiseFactory.getSessionInfoService();
        dbService.add(sessionEntity);
    }

    @After
    public void deleteUser(){
        try {
            dbService.delete(userId);
        } catch (IllegalArgumentException ignored){ }
    }

    @Test
    public void setDataToDataBaseTest(){
        SessionEntity session = dbService.get(userId);
        assertEquals(questionId, session.getUserQuestions());
        assertEquals(score, (int)session.getScore());
    }

    @Test
    public void updateDataInDataBaseTest(){
        int newScore = 20;
        sessionEntity.setScore(newScore);
        dbService.update(sessionEntity);
        SessionEntity session = dbService.get(userId);
        assertEquals(questionId, session.getUserQuestions());
        assertEquals(newScore, (int)session.getScore());
    }

    @Test
    public void deleteDataInDataBase(){

        dbService.delete(userId);
        SessionEntity session = dbService.get(userId);
        Integer a = null;
        try
        {
            a = session.getScore();
        }catch (IllegalArgumentException | NullPointerException ignored){ }
        assertNull(a);
    }

}
