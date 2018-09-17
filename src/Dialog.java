import questions.QuestionHelper;
import questions.QuestionsGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Dialog {
    protected String startCommand;
    protected String stopCommand;
    protected String showHelpCommand;
    protected String nextQuestionCommand;
    protected String questionHelpCommand;

    private BufferedWriter outputWritter;
    private BufferedReader inputReader;
    private QuestionHelper questionHelper;

    public Dialog(BufferedReader input, BufferedWriter output, QuestionHelper helper){
        startCommand = "/start";
        stopCommand = "/end";
        showHelpCommand = "/help";
        nextQuestionCommand = "/next";
        questionHelpCommand = "/questionHelp";
        inputReader = input;
        outputWritter = output;
        questionHelper = helper;
        try {
            outputWritter.write(showHelp());
            outputWritter.flush();
        } catch (Exception e){
            //TODO: обработка ошибок
        }
        mainDialog();
    }

    private void mainDialog(){
        var questionShowed = false;
        while (true){
            try {
                var userAnswer = inputReader.readLine();
                if (userAnswer.equals(startCommand))
                    outputWritter.write(String.format("%s \n\r", questionHelper.GetNextQuestion()));
                else if (userAnswer.equals(stopCommand))
                    break;
                else if (userAnswer.equals(showHelpCommand))
                    outputWritter.write(showHelp());
                else if (userAnswer.equals(nextQuestionCommand)){
                    outputWritter.write(String.format("%s \n\r", questionHelper.GetNextQuestion()));
                    questionShowed = true;
                }
                else if (userAnswer.equals(questionHelpCommand))
                    outputWritter.write(String.format("%s \n\r", questionHelper.GetHelp()));
                else if (questionShowed)
                {
                    if (questionHelper.CheckAnswer(userAnswer)) {
                        outputWritter.write(String.format("To continue print %s, or print %s to end\n\r", nextQuestionCommand, stopCommand));
                        questionShowed = false;
                    }else
                        outputWritter.write(String.format("to get help print: %s or take next question: %s\n\r", questionHelpCommand, nextQuestionCommand));
                }
                outputWritter.flush();
            } catch (Exception e) {
                //TODO: should throw exception an write debug log
            }
        }
    }

    protected String showHelp(){
        var botInfo = "With bot you can play game: \"Что? Где? Когда?\"";
        var botCommandsInfo = String.format("To play game print \"%s\", to end game print: \"%s\", to get help print: \"%s\"", startCommand, stopCommand, showHelpCommand);
        return String.format("About:\n\r %s\n\r Commands: \n\r%s\n\r", botInfo, botCommandsInfo);
    }
}
