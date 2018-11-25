package BotInterfaces.telegramBot;

import BotInterfaces.Dialog;
import questions.QuestionHelper;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;

public class TelegramBotDialog extends Dialog {
    ArrayBlockingQueue messagesQueue = new ArrayBlockingQueue(100, true);
    private Bot bot;
    private Long chatId;

    TelegramBotDialog(QuestionHelper helper, Bot bot, Long chatId) throws IOException {
        super(helper);

        this.bot = bot;
        this.chatId = chatId;
    }

    @Override
    public String read() throws InterruptedException {
        return (String) messagesQueue.take();
    }

    @Override
    public void write(String text) throws IOException {
        bot.sendMessage(text, chatId);
    }
}
