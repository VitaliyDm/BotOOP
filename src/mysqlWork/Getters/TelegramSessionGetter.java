package mysqlWork.Getters;

import mysqlWork.SessionInfo;
import mysqlWork.SqlGetter;

import java.sql.SQLException;

public class TelegramSessionGetter extends SqlGetter {
    private SessionInfo gettedSession = null;

    @Override
    protected void requestAction() throws SQLException {
        gettedSession = null;
        if (resultSet.next()){
//            gettedSession = new SessionInfo();
//            gettedSession.Score = resultSet.getInt("score");
//            gettedSession.UserQuestions = resultSet.getString("userQuestions");
        }
    }

    public SessionInfo getUserSession(Long chatId){
        getRequest(String.format("select * from botoop.session where chatId=%d", chatId));
        return gettedSession;
    }
}
