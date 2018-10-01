import questions.QuestionsGenerator;

import java.io.IOException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        new QuestionsGenerator();
        try {
            new UserSession();
        } catch (IOException e){

        }

    }
}