import questions.QuestionHelper;
import questions.QuestionsGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Dialog {
    protected String startCommand;
    protected String stopCommand;
    protected String showHelpCommand;
    protected String nextQuestionCommand;
    protected String questionHelpCommand;

    private BufferedWriter outputWritter;
    private BufferedReader inputReader;
    private QuestionHelper questionHelper;

    public Dialog(BufferedReader input, BufferedWriter output, QuestionHelper helper) throws IOException {
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
            e.printStackTrace();
            throw new IOException("Cannot write in console");
        }
        mainDialog();
    }

    private void mainDialog() throws IOException {
        var questionShowed = false;
        while (true){
            try {
                var userAnswer = inputReader.readLine();
                if (userAnswer.equals(startCommand)){
                    outputWritter.write(String.format("%s \n\r", questionHelper.GetNextQuestion()));
                    questionShowed = true;
                }
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
                        outputWritter.write(String.format("Для продолжения введите %s, или введите %s для завершения игры\n\r", nextQuestionCommand, stopCommand));
                        questionShowed = false;
                    }else
                        outputWritter.write(String.format("Для подсказки по вопросу введите: %s либо перейти к следующему вопросу: %s\n\r", questionHelpCommand, nextQuestionCommand));
                }
                outputWritter.flush();
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException("Cannot write in console");
            }
        }
    }

    protected String showHelp(){
        var botInfo = "Вы можете сыграть с ботом в игру: \"Что? Где? Когда?\"";
        var botCommandsInfo = String.format("Чтоб начать игру введите \"%s\", для завершения игры введите: \"%s\", Для получения справки введите: \"%s\"", startCommand, stopCommand, showHelpCommand);
        return String.format("Об игре:\n\r %s\n\r Команды: \n\r%s\n\r", botInfo, botCommandsInfo);
    }
}
