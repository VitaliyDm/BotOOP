package BotInterfaces.telegramBot;

import BotInterfaces.Dialog;
import BotInterfaces.UserSession;
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
        var serializedString = questionHelper.getQuestionsId().toString();
        serializedString = serializedString.substring(1, serializedString.length() - 1);
        if (Bot.dataBaseGetter.getUserSession(currentChatId) == null)
            Bot.dataBaseSetter.setDataToDataBase(currentChatId, serializedString, questionHelper.getScore());
        else
            Bot.dataBaseSetter.updateDataInDataBase(currentChatId, serializedString, questionHelper.getScore());
    }

    @Override
    public void startDialog(boolean gameStarted) throws IOException, InterruptedException {
        userDialog.mainDialog(gameStarted);
    }

    void setMessagesQueue(String message){
        ioManager.messagesQueue.add(message);
    }
}


