package mysqlWork.Getters;

import mysqlWork.SessionInfo;
import mysqlWork.SqlGetter;

import java.sql.SQLException;

public class GetTelegramSession extends SqlGetter {
    private SessionInfo gettedSession = new SessionInfo();

    @Override
    protected void requestAction() throws SQLException {
        var chatId = resultSet.getLong("chatId");
        gettedSession.Score = resultSet.getInt("score");
        gettedSession.UserQuestions = resultSet.getString("userQuestions");
    }

    public SessionInfo getUserSession(Long chatId){
        getRequest(String.format("select * from users where chatId=%d", chatId));
        return gettedSession;
    }
}
