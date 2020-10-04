package ru.progwards.java1.lessons.io2;

public class Translator {

    private String[] inLang;
    private String[] outLang;

    Translator(String[] inLang, String[] outLang) {
        this.inLang = inLang;
        this.outLang = outLang;
    }

    public String translate(String sentence) {
        String[] arrSent = sentence.split(" ");
        String outputStr = "";

        for (int i = 0; i < arrSent.length; i++) {
            String str = "";
            for (char ch : arrSent[i].toCharArray()) {
                if (Character.isAlphabetic(ch)) {
                    str += ch;
                }
            }
            str = str.toLowerCase();
            if (str.equals("")) {
                outputStr += arrSent[i] + " ";
            } else {
                for (int j = 0; j < inLang.length; j++) {
                    if (str.equals(inLang[j])) {
                        String in = "";

                        if (arrSent[i].substring(0, 1).equals(arrSent[i].substring(0, 1).toUpperCase())) {
                            in = outLang[j].substring(0, 1).toUpperCase() + outLang[j].substring(1);
                        } else {
                            in = outLang[j];
                        }

                        if (str.length() == arrSent[i].length()) {
                            outputStr = outputStr + in + " ";
                        } else {
                            int substr = arrSent[i].length() - (arrSent[i].length() - str.length());
                            outputStr = outputStr + in + arrSent[i].substring(substr) + " ";
                        }
                    }
                }
            }
        }
        outputStr = outputStr.trim();
        return outputStr;
    }

}
