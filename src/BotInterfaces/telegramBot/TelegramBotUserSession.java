package BotInterfaces.telegramBot;

import BotInterfaces.UserSession;
import questions.QuestionsGenerator;

import java.io.IOException;

public class TelegramBotUserSession extends UserSession {
    private Long currentChatId;

    public TelegramBotUserSession(QuestionsGenerator questionsGenerator, Bot bot, Long chatId) throws IOException {
        super(questionsGenerator);
        userDialog = new TelegramBotDialog(questionHelper, bot, chatId);
        currentChatId = chatId;
    }

    @Override
    public void saveSession() {
        var serializedString = questionHelper.getQuestionsId().toString();
        serializedString = serializedString.substring(1, serializedString.length());
        Bot.dataBaseSetter.setDataToDataBase(currentChatId, serializedString, questionHelper.getScore());
    }

    @Override
    public void startDialog() throws IOException, InterruptedException {
        userDialog.mainDialog();
    }

    void setMessagesQueue(String message){
        ((TelegramBotDialog) userDialog).messagesQueue.add(message);
    }
}


