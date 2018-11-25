package mysqlWork.Getters;

import mysqlWork.SqlGetter;

import java.sql.SQLException;

public class GetTelegramSession extends SqlGetter {
    class 
    @Override
    protected void requestAction() throws SQLException {
        var chatId = resultSet.getLong("chatId");
        var score = resultSet.getInt("score");
        var userQuestions = resultSet.getString("userQuestions");
    }

    protected void getUserSession(Long chatId){
        getRequest(String.format("select * from users where chatId=%d", chatId));
    }
}
