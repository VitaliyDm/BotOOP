package BotInterfaces;

import questions.QuestionHelper;

import java.io.IOException;

public class Dialog{
    protected QuestionHelper questionHelper;
    protected Boolean isEnd = false;
    private IOInterface ioManager;

    public Boolean getIsEnd(){
        return isEnd;
    }

    public enum commands {start("/start"), end("/end"), help("/help"), questionHelp("/questionHelp"), next("/next"),
        score("/score");
        private String command;
        commands(String s) {
            this.command = s;
        }
        public String getCommand(){return command;}
    }

    public Dialog(QuestionHelper helper, IOInterface ioInterface) {
        questionHelper = helper;
        ioManager = ioInterface;
    }

    public void mainDialog() throws IOException, InterruptedException {
        mainDialog(false);
    }

    public void mainDialog(boolean gameStarted) throws IOException, InterruptedException {
        var questionShowed = false;
        ioManager.write(showHelp());
        while (true){
            var userAnswer = ioManager.read();
            commands parsedCommand = null;

            for (var currentCommand : commands.values()) {
                if (currentCommand.command.equals(userAnswer))
                    parsedCommand = currentCommand;
            }

            if (parsedCommand == null) {
                if (!questionShowed) {
                    ioManager.write(String.format("Неизвестная команда. Введите %s для помоци или %s для следующего вопроса", commands.help.getCommand(), commands.next.getCommand()));
                    continue;
                }
                if (questionHelper.checkAnswer(userAnswer)) {
                    ioManager.write(String.format("Ответ верный!\nДля продолжения введите %s, или введите %s для завершения игры", commands.next.getCommand(), commands.end.getCommand()));
                    questionShowed = false;
                } else
                    ioManager.write(String.format("Неверный ответ!\nДля подсказки по вопросу введите: %s либо перейти к следующему вопросу: %s", commands.questionHelp.getCommand(), commands.next.getCommand()));
                continue;
            }
            switch (parsedCommand) {
                case start:
                    if (gameStarted)
                        questionHelper.restartGenerator();
                    ioManager.write(String.format("%s \n\r", questionHelper.getNextQuestion()));
                    questionShowed = true;
                    gameStarted = true;
                    break;
                case end:
                    isEnd = true;
                    break;
                case help:
                    ioManager.write(showHelp());
                    break;
                case questionHelp:
                    if (gameStarted)
                        ioManager.write(String.format("%s \n\r", questionHelper.getHelp()));
                    else
                        ioManager.write(showHelp());
                    break;
                case next:
                    if (gameStarted)
                        ioManager.write(String.format("%s \n\r", questionHelper.getNextQuestion()));
                    else
                        ioManager.write(showHelp());
                    questionShowed = true;
                    break;
                case score:
                    ioManager.write(String.valueOf(questionHelper.getScore()));
                    break;
            }
        }
    }

    protected String showHelp(){
        var botInfo = "Вы можете сыграть с ботом в игру: \"Что? Где? Когда?\"";
        var botCommandsInfo = String.format("Чтоб начать игру введите \"%s\", для завершения игры введите: \"%s\"," +
                        " Для получения справки введите: \"%s\", чтобы узнать счет введите: \"%s\"",
                commands.start.getCommand(), commands.end.getCommand(), commands.help.getCommand(),
                commands.score.getCommand());
        return String.format("Об игре:\n\r %s\n\r Команды: \n\r%s", botInfo, botCommandsInfo);
    }
}
