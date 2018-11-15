import BotInterfaces.ConsoleBot.ConsoleBotUserSession;
import BotInterfaces.telegram.telegramBotUserSession;
import BotInterfaces.UserSession;
import questions.QuestionsGenerator;

import java.io.IOException;

public class Main{
    public static void main(String[] args){
        try {
            new telegramBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS));
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}