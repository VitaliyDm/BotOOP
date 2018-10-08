import questions.QuestionHelper;
import questions.QuestionsGenerator;

import java.io.*;

public class UserSession {
    int userScore;
    Dialog userDialog;
    QuestionHelper questionHelper;

    public UserSession(QuestionsGenerator questionsGenerator) throws IOException {
        userScore = 0;
        questionHelper = new QuestionHelper(questionsGenerator);
        userDialog =
                new Dialog(new BufferedReader(new InputStreamReader(System.in)), new BufferedWriter(new OutputStreamWriter(System.out)), questionHelper);
        userDialog.mainDialog();
    }
}
