package BotInterfaces.telegramBot;

import BotInterfaces.IOInterface;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.concurrent.ArrayBlockingQueue;

public class TelegramIOManager implements IOInterface {
    ArrayDeque messagesQueue = new ArrayDeque();
    private Bot bot;
    private Long chatId;

    TelegramIOManager(Bot bot, Long chatId){
        this.bot = bot;
        this.chatId = chatId;
    }

    @Override
    public String read() {
        while(true){
            if(messagesQueue.isEmpty()){
                Thread.yield();
            }
            break;
        }
        return (String) messagesQueue.pop();
    }

    @Override
    public void write(String text) {
        bot.sendMessage(text, chatId);
    }
}
