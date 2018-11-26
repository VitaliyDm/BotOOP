import BotInterfaces.ConsoleBot.ConsoleBotUserSession;
import constants.Constants;
import questions.QuestionsGenerator;

import java.io.IOException;

public class Main{
    public static void main(String[] args){
        try {
            new ConsoleBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS));
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}