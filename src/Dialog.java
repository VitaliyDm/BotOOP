import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Dialog {
    public Dialog(){

    }

    private void StartDialog(BufferedWriter output){

    }

    private void MainDialog(BufferedReader input, BufferedWriter ourput){
        String breakWord = "stop";
        while (true){
            try {
                String userAnswer = input.readLine();
            } catch (Exception e) {
                //TODO: should throw exeption an write debug log
            }
        }
    }
}
