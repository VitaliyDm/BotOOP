package BotInterfaces.telegramBot;

import java.sql.SQLException;

public class TelegramBotUserSessionDAOImpl implements TelegramBotUserSessionDAO{
    @Override
    public TelegramBotUserSession getUserSession(Long chatId) throws SQLException {
        return null;
    }

    @Override
    public void addUserSession(TelegramBotUserSession userSession) throws SQLException {

    }

    @Override
    public void updateUserSession(Long bus_id, TelegramBotUserSession userSession) throws SQLException {

    }

    @Override
    public void deleteUserSession(TelegramBotUserSession userSession) throws SQLException {

    }
}
