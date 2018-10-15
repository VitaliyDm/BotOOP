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

    protected enum commands {start("/start"), end("/end"), help("/help"), questionHelp("/questionHelp"), next("/next");
        private String command;
        commands(String s) {
            this.command = s;
        }
        public String getCommand(){return command;}
    }
    protected HashMap<String, commands> stringToCommands = new HashMap<>();

    public Dialog(BufferedReader input, BufferedWriter output, QuestionHelper helper) throws IOException {
        stringToCommands.put("/start", commands.start);
        stringToCommands.put("/end", commands.end);
        stringToCommands.put("/help", commands.help);
        stringToCommands.put("/questionHelp", commands.questionHelp);
        stringToCommands.put("/next", commands.next);

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
            if (parsedCommand == null) {
                if (!questionShowed) {
                    outputWritter.write(String.format("Неизвестная команда. Введите %s для помоци или %s для следующего вопроса\n\r", commands.help.getCommand(), commands.next.getCommand()));
                    outputWritter.flush();
                    continue;
                }
                if (questionHelper.checkAnswer(userAnswer)) {
                    outputWritter.write(String.format("Ответ верный!\nДля продолжения введите %s, или введите %s для завершения игры\n\r", commands.next.getCommand(), commands.end.getCommand()));
                    questionShowed = false;
                } else
                    outputWritter.write(String.format("Неверный ответ!\nДля подсказки по вопросу введите: %s либо перейти к следующему вопросу: %s\n\r", commands.help.getCommand(), commands.next.getCommand()));
                outputWritter.flush();
                continue;
            }
            switch (parsedCommand) {
                case start:
                    outputWritter.write(String.format("%s \n\r", questionHelper.getNextQuestion()));
                    questionShowed = true;
                    break;
                case end:
                    return;
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
                " Для получения справки введите: \"%s\"", commands.start.getCommand(), commands.end.getCommand(), commands.help.getCommand());
        return String.format("Об игре:\n\r %s\n\r Команды: \n\r%s\n\r", botInfo, botCommandsInfo);
    }
}
