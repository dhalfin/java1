package ru.progwards.java1.lessons.io2;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Censor {

    static class CensorException extends Exception {
        public String message;
        public String fileName;

        @Override
        public String toString() {
            return fileName + ":" + message;
        }

        public CensorException(String message, String fileName) {
            super();
            this.message = message;
            this.fileName = fileName;
        }
    }

    public static void censorFile(String inoutFileName, String[] obscene) throws CensorException {

        String outputText = "";
        String changedWord = "";

        try (FileReader fileReader = new FileReader(inoutFileName)) {
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String string = scanner.nextLine();
                for (String word : string.split(" ")) {
                    for (String arr : obscene) {
                        if (word.indexOf(arr) > -1) {
                            for (int i = 1; i <= arr.length(); i++) {
                                changedWord += "*";
                            }
                            word = word.replaceAll(arr, changedWord);
                            changedWord = "";
                        }
                    }
                    outputText += word + " ";
                }
                outputText = outputText.trim();
            }
            try (FileWriter fileWriter = new FileWriter(inoutFileName)) {
                fileWriter.write(outputText);
            } catch (Exception e) {
                throw new CensorException(e.getMessage(), inoutFileName);
            }
        } catch (Exception e) {
            throw new CensorException(e.getMessage(), inoutFileName);
        }
    }

}
