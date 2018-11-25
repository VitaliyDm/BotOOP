package mysqlWork.Setters;

import mysqlWork.SqlSetter;

public class SaveTelegramSession extends SqlSetter {
    public void setDataToDataBase(Long chatId, String userQuestions, Integer userScore){
        setRequest(
                String.format("INSERT INTO `botoop`.`session` (`chatId`, `score`, `userQuestions`) VALUES ('%d', '%d', '%s');",
                        chatId,userScore, userQuestions)
        );
    }
}
