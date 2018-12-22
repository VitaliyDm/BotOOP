package BotInterfaces.telegramBot;

import java.sql.SQLException;

public interface TelegramBotUserSessionDAO{
    public TelegramBotUserSession getUserSession(Long chatId) throws SQLException;
    public void addUserSession(TelegramBotUserSession userSession) throws SQLException;
    public void updateUserSession(Long bus_id, TelegramBotUserSession userSession) throws SQLException;
    public void deleteUserSession(TelegramBotUserSession userSession) throws SQLException;


}