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
    public long chatId;
    public ArrayDeque<String> messagesQueue = new ArrayDeque<>();

    public static void main(String args[]) {
        System.out.println("Зашел в Маин");
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            System.out.println("захотел поставить регистр");
            api.registerBot(new Bot());
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
        System.out.println("Все поставил");
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
        System.out.println(chatId);
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
        System.out.println("Зашел в onUpdateReceived");
        Message message = update.getMessage();
        chatId = message.getChatId();
        if (message != null && message.hasText()){
            sendMessage(message.getText() + " это отвечает onUpdateReceived");
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