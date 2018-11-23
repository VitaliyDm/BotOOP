import BotInterfaces.ConsoleBot.ConsoleBotUserSession;
import questions.QuestionsGenerator;
import constants.Constants;

import java.io.IOException;

public class Main{
    public static void main(String[] args){
        try {
            new ConsoleBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS));
        } catch (IOException e){
            e.printStackTrace();
        }


    }
}