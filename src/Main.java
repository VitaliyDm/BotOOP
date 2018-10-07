import questions.QuestionsGenerator;

import java.io.IOException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        try {
            new UserSession(new QuestionsGenerator());
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}