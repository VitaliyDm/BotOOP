package BotInterfaces.telegramBot;

import BotInterfaces.Dialog;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import questions.QuestionHelper;

import java.io.*;
import java.util.ArrayDeque;

public class telegramBotDialog extends Dialog {
    PipedOutputStream pipedOutputStream = new PipedOutputStream();
    final PipedInputStream in = new PipedInputStream(pipedOutputStream);
    private telegramBotDialog.Bot telegramApi;
    private ArrayDeque<String> messagesQueue = new ArrayDeque<>();
    private long chatId;
    private int status

    public status(){

    }

    public telegramBotDialog(QuestionHelper helper) throws IOException {
        super(helper);
        questionHelper = helper;

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        telegramApi = new telegramBotDialog.Bot();
        try {
            api.registerBot(telegramApi);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public String read() throws IOException{
        while(messagesQueue.isEmpty()){
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return messagesQueue.pop();
    }

    @Override
    public void write(String text) throws IOException {
        if (chatId != 0){
            telegramApi.sendMessage(text);
        }
    }

    class Bot extends TelegramLongPollingBot {
        void sendMessage(String text){
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
            messagesQueue.add(message.getText());
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
}
