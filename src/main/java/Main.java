import BotInterfaces.ConsoleBot.ConsoleBotUserSession;
import BotInterfaces.telegramBot.Bot;
import constants.Constants;
import mysqlWork.SessionEntity;
import mysqlWork.SessionInfoService;
import questions.QuestionsGenerator;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
//        try {
//            new ConsoleBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS));
//        } catch (IOException | InterruptedException e){
//            e.printStackTrace();
//        }
        new Bot();
    }
}