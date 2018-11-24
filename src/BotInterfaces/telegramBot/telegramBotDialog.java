package BotInterfaces.telegramBot;

import BotInterfaces.Dialog;
import questions.QuestionHelper;

import java.io.*;
import java.util.ArrayDeque;

public class telegramBotDialog extends Dialog {
    ArrayDeque<String> messagesQueue = new ArrayDeque<>();
    private Bot bot;
    private Long chatId;

    telegramBotDialog(QuestionHelper helper, Bot bot, Long chatId) throws IOException {
        super(helper);

        this.bot = bot;
        this.chatId = chatId;
    }

    @Override
    public String read() throws IOException{
        while(messagesQueue.isEmpty()){
            Thread.yield();
        }
        return messagesQueue.pop();
    }

    @Override
    public void write(String text) throws IOException {
        bot.sendMessage(text, chatId);
    }
}
