package BotInterfaces.telegramBot;

import BotInterfaces.Dialog;
import BotInterfaces.UserSession;
import mysqlWork.SessionEntity;
import mysqlWork.SessionInfoService;
import questions.QuestionsGenerator;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TelegramBotUserSession extends UserSession {
    private Long currentChatId;
    private TelegramIOManager ioManager;
    private Bot bot;
    public SessionInfoService sessionInfoService;

    Long getCurrentChatId() { return currentChatId; }

    public TelegramBotUserSession(QuestionsGenerator questionsGenerator,
                                  Bot currentBot, Long chatId, SessionInfoService sessionInfoService){
        super(questionsGenerator);


        bot = currentBot;
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
        SessionEntity dbSession = sessionInfoService.get(currentChatId);
        if (dbSession == null)

            sessionInfoService.add(new SessionEntity().sessionEntity(currentChatId, questionHelper.getScore(), serializedString));
        else {
            dbSession.setScore(questionHelper.getScore());
            dbSession.setUserQuestions(serializedString);
            sessionInfoService.update(dbSession);
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


