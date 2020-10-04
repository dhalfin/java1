package ru.progwards.java1.lessons.io2;

public class PhoneNumber {

    public static String format(String phone) throws Exception {
        String formattedPhone = "";
        for (char ch : phone.toCharArray()) {
            if (Character.isDigit(ch)) {
                formattedPhone += ch;
            }
        }
        if (formattedPhone.toCharArray().length > 11 || formattedPhone.toCharArray().length < 10) {
            throw new Exception();
        }
        if (formattedPhone.toCharArray().length == 11) {
            formattedPhone = formattedPhone.substring(1);
        }
        formattedPhone = "+7(" + formattedPhone.substring(0, 3) + ")" + formattedPhone.substring(3, 6) + "-" +
                formattedPhone.substring(6);
        return formattedPhone;
    }
}
