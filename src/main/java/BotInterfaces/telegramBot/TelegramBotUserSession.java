package BotInterfaces.telegramBot;

import BotInterfaces.Dialog;
import BotInterfaces.UserSession;
import mysqlWork.SessionEntity;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TelegramBotUserSession extends UserSession {
    private Long currentChatId;
    private TelegramIOManager ioManager;
    private Bot bot;

    Long getCurrentChatId() { return currentChatId; }

    public TelegramBotUserSession(QuestionsGenerator questionsGenerator, Bot bot, Long chatId){
        super(questionsGenerator);

        this.bot = bot;
        ioManager = new TelegramIOManager(bot, chatId);
        userDialog = new Dialog(questionHelper, ioManager);
        currentChatId = chatId;
    }

    Dialog getUserDialog(){
        return userDialog;
    }

    @Override
    public void saveSession() {
        String serializedString = questionHelper.getQuestionsId().toString();
        serializedString = serializedString.substring(1, serializedString.length() - 1);
        SessionEntity session = new SessionEntity();
        session.setChatId(currentChatId);
        session.setScore(questionHelper.getScore());
        session.setUserQuestions(serializedString);
        if (bot.dbServise.get(currentChatId) == null)
            bot.dbServise.add(session);
        else
            bot.dbServise.update(session);
    }

    @Override
    public void startDialog(boolean gameStarted) throws IOException, InterruptedException {
        userDialog.mainDialog(gameStarted);
    }

    void setMessagesQueue(String message){
        ioManager.messagesQueue.add(message);
    }
}


