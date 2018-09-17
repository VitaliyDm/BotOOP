import java.io.BufferedInputStream;
import java.io.*;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        var scanner = new Scanner(System.in);
        System.out.println(multiple(scanner.nextDouble(), scanner.nextDouble()));
    }

    public static double multiple(double num1, double num2){
        return  num1 * num2;
    }
}