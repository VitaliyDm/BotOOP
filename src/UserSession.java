import questions.QuestionHelper;

import java.io.*;

public class UserSession {
    int userScore;
    Dialog userDialog;
    QuestionHelper questionHelper;

    public UserSession(){
        userScore = 0;
        questionHelper = new QuestionHelper();
        userDialog =
                new Dialog(new BufferedReader(new InputStreamReader(System.in)), new BufferedWriter(new OutputStreamWriter(System.out)), questionHelper);
    }
}
