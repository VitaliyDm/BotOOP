package mysqlWork.Setters;

import mysqlWork.SqlSetter;

public class TelegramSessionSetter extends SqlSetter {
    public void setDataToDataBase(Long chatId, String userQuestions, Integer userScore){
        setRequest(
                String.format("INSERT INTO botoop.session (chatId, score, userQuestions) VALUES ('%d', '%d', '%s')",
                        chatId,userScore, userQuestions)
        );
    }

    public void updateDataInDataBase(Long chatId, String userQuestions, Integer userScore){
        setRequest(
                String.format("UPDATE botoop.session SET score='%d', userQuestions='%s' WHERE chatID=%d",
                        userScore, userQuestions, chatId)
        );
    }
}
