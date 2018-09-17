import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Dialog {
    protected String startCommand;
    protected String stopCommand;
    protected String showHelpCommand;

    private BufferedWriter outputWritter;
    private BufferedReader inputReader;
    private QuestionsGenerator questionsGenerator;

    public Dialog(BufferedReader input, BufferedWriter output){
        startCommand = "/start";
        stopCommand = "/end";
        showHelpCommand = "/help";
        inputReader = input;
        outputWritter = output;
        showHelp();
    }

    private void mainDialog(){
        var questionShowed = false;
        while (true){
            try {
                String userAnswer = inputReader.readLine();
                if (userAnswer.equals(stopCommand))
                    break;
                else if (userAnswer.equals(showHelpCommand))
                    showHelp();
                else if (questionShowed)
                    break;
                    //questionsGenerator.checkAnswer();
            } catch (Exception e) {
                //TODO: should throw exeption an write debug log
            }
        }
    }

    protected void showHelp(){
        var botInfo = "With bot you can play game: \"Что? Где? Когда?\"";
        var botCommandsInfo = String.format("To play game print \"%s\", to end game print: \"%s\", to get help print: \"%s\"", startCommand, stopCommand, showHelpCommand);
        try {
            outputWritter.write(String.format("About:\n\r %s\n\r Commands: \n\r%s", botInfo, botCommandsInfo));
        }catch (Exception e){
            //TODO: придумать какую-то обработку ошибок
        }
    }
}
