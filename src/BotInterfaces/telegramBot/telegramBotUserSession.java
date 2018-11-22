package BotInterfaces.telegramBot;

import BotInterfaces.UserSession;
import questions.QuestionsGenerator;

import java.io.IOException;

public class telegramBotUserSession extends UserSession {
    public telegramBotUserSession(QuestionsGenerator questionsGenerator) throws IOException {
        super(questionsGenerator);
        userDialog = new telegramBotDialog(questionHelper);
        startDialog();
    }

    @Override
    public void startDialog() throws IOException {
        Thread myThready = new Thread(new Runnable()
        {
            public void run()  //Этот метод будет выполняться в побочном потоке
            {
                try {
                    userDialog.mainDialog();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        myThready.start();
    }

    void set_messagesQueue(String message){
        ((telegramBotDialog) userDialog).messagesQueue.add(message);
    }

    void set_bot(Bot bot){
        ((telegramBotDialog) userDialog).bot = bot;
    }

    public void set_chatId(Long chatId){
        ((telegramBotDialog) userDialog).chatId = chatId;
    }
}


