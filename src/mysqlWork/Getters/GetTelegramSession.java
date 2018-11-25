package mysqlWork.Getters;

import mysqlWork.SessionInfo;
import mysqlWork.SqlGetter;

import java.sql.SQLException;

public class GetTelegramSession extends SqlGetter {
    private SessionInfo gettedSession = new SessionInfo();

    @Override
    protected void requestAction() throws SQLException {
        if (resultSet.next()){
            gettedSession.Score = resultSet.getInt("score");
            gettedSession.UserQuestions = resultSet.getString("userQuestions");
        }
    }

    public SessionInfo getUserSession(Long chatId){
        getRequest(String.format("select * from botoop.session where chatId=%d", chatId));
        return gettedSession;
    }
}
