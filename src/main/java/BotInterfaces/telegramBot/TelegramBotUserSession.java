package BotInterfaces.telegramBot;

import BotInterfaces.Dialog;
import BotInterfaces.UserSession;
import mysqlWork.SessionEntity;
import questions.QuestionsGenerator;

import java.io.IOException;

public class TelegramBotUserSession extends UserSession {
    private Long currentChatId;
    private TelegramIOManager ioManager;

    public Long getCurrentChatId() { return currentChatId; }

    public TelegramBotUserSession(QuestionsGenerator questionsGenerator, Bot bot, Long chatId) throws IOException {
        super(questionsGenerator);
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
        SessionEntity dbSession = Bot.dbServise.get(currentChatId);
        if (dbSession == null)
            Bot.dbServise.add(new SessionEntity(currentChatId, questionHelper.getScore(), serializedString));
        else {
            dbSession.setScore(questionHelper.getScore());
            dbSession.setUserQuestions(serializedString);
            Bot.dbServise.update(dbSession);
        }
    }

    @Override
    public void startDialog(boolean gameStarted) throws IOException, InterruptedException {
        userDialog.mainDialog(gameStarted);
    }

    void setMessagesQueue(String message){
        ioManager.messagesQueue.add(message);
    }
}


