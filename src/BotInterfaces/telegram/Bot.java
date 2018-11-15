package BotInterfaces.telegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayDeque;
import java.util.Queue;

public class Bot extends TelegramLongPollingBot {
    private long chatId;
    public ArrayDeque<String> messagesQueue = new ArrayDeque<>();

    public static void main(String args[]) {
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(new Bot());
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void sendReplyMessage(Message message, String text, boolean setReply){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.enableMarkdown(true);
        if (setReply)
            sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void sendReplyMessage(Message message, String text){
        sendReplyMessage(message, text, false);
    }

    public void sendMessage(String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableMarkdown(true);
        message.setText(text);
        try {
            sendMessage(message);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        chatId = message.getChatId();
        if (message != null && message.hasText()){
            System.out.println(message.getText());
            messagesQueue.addLast(message.getText());
        }
    }

    @Override
    public String getBotUsername() {
        return "WhoWhereWhenBot";
    }

    @Override
    public String getBotToken() {
        return "697301961:AAEdrNBIeEN_hIMi07qmqd4La-rfPwmC4go";
    }
}