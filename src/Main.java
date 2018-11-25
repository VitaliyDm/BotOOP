import mysqlWork.Getters.GetTelegramSession;

public class Main{
    public static void main(String[] args){
        //try {
        //    new ConsoleBotUserSession(new QuestionsGenerator(Constants.PATH_TO_QUESTIONS));
        //} catch (IOException | InterruptedException e){
        //    e.printStackTrace();
        //}

        var getter = new GetTelegramSession();
        getter.getUserSession((long)22);
    }
}