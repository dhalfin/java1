package ru.progwards.java1.lessons.io1;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;

public class Coder {

    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {

        try {
            FileReader readFile = new FileReader(inFileName);
            FileWriter writeFile = new FileWriter(outFileName);

            try {
                int symbol = readFile.read();
                while (symbol != -1) {
                    writeFile.write(code[symbol]);
                    symbol = readFile.read();
                }
            } finally {
                readFile.close();
                writeFile.close();
            }
        } catch (Exception e) {
            try {
                PrintStream out = new PrintStream(new FileOutputStream(logName));
                System.setOut(out);
                System.out.println(e.getMessage());
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
