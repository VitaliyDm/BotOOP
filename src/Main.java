import BotInterfaces.UserSession;
import questions.QuestionsGenerator;

import java.io.IOException;

public class Main{
    public static void main(String[] args){
        try {
            new UserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS), );
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}