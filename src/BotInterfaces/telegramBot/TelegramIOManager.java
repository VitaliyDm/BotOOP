package BotInterfaces.telegramBot;

import BotInterfaces.IOInterface;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class TelegramIOManager implements IOInterface {
    ArrayBlockingQueue messagesQueue = new ArrayBlockingQueue(100, true);
    private Bot bot;
    private Long chatId;

    TelegramIOManager(Bot bot, Long chatId){
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
