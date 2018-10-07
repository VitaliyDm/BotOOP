import questions.QuestionHelper;
import questions.QuestionsGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

public class Dialog {
    private BufferedWriter outputWritter;
    private BufferedReader inputReader;
    private QuestionHelper questionHelper;

    protected enum commands {start, end, help, questionHelp, next}
    protected HashMap<String, commands> stringToCommands = new HashMap<>();
    protected HashMap<commands, String> commandsToString = new HashMap<>();

    public Dialog(BufferedReader input, BufferedWriter output, QuestionHelper helper) throws IOException {
        stringToCommands.put("/start", commands.start);
        stringToCommands.put("/end", commands.end);
        stringToCommands.put("/help", commands.help);
        stringToCommands.put("/questionHelp", commands.questionHelp);
        stringToCommands.put("/next", commands.next);

        commandsToString.put(commands.start, "/start");
        commandsToString.put(commands.end, "/end");
        commandsToString.put(commands.help, "/help");
        commandsToString.put(commands.questionHelp, "/questionHelp");
        commandsToString.put(commands.next, "/next");

        inputReader = input;
        outputWritter = output;
        questionHelper = helper;
        outputWritter.write(showHelp());
        outputWritter.flush();
    }

    public void mainDialog() throws IOException {
        var questionShowed = false;
        while (true){
            var userAnswer = inputReader.readLine();
            var parsedCommand = stringToCommands.get(userAnswer);
            if (parsedCommand == null && questionShowed) {
                if (questionHelper.checkAnswer(userAnswer)) {
                    outputWritter.write(String.format("Ответ верный!\nДля продолжения введите %s, или введите %s для завершения игры\n\r", commandsToString.get(commands.next), commandsToString.get(commands.end)));
                    questionShowed = false;
                } else
                    outputWritter.write(String.format("Неверный ответ!\nДля подсказки по вопросу введите: %s либо перейти к следующему вопросу: %s\n\r", commandsToString.get(commands.help), commandsToString.get(commands.next)));
                outputWritter.flush();
            }
            if (parsedCommand == null)
                continue;
            switch (parsedCommand) {
                case start:
                    outputWritter.write(String.format("%s \n\r", questionHelper.getNextQuestion()));
                    questionShowed = true;
                    break;
                case end:
                    break;
                case help:
                    outputWritter.write(showHelp());
                    break;
                case questionHelp:
                    outputWritter.write(String.format("%s \n\r", questionHelper.getHelp()));
                    break;
                case next:
                    outputWritter.write(String.format("%s \n\r", questionHelper.getNextQuestion()));
                    questionShowed = true;
                    break;
            }
            outputWritter.flush();
        }
    }

    protected String showHelp(){
        var botInfo = "Вы можете сыграть с ботом в игру: \"Что? Где? Когда?\"";
        var botCommandsInfo = String.format("Чтоб начать игру введите \"%s\", для завершения игры введите: \"%s\"," +
                " Для получения справки введите: \"%s\"", commandsToString.get(commands.start), commandsToString.get(commands.end), commandsToString.get(commands.help));
        return String.format("Об игре:\n\r %s\n\r Команды: \n\r%s\n\r", botInfo, botCommandsInfo);
    }
}
