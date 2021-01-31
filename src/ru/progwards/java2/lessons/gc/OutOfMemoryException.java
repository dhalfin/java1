package ru.progwards.java2.lessons.gc;

public class OutOfMemoryException extends Exception{
    @Override
    public String getMessage() {
        return "Out Of Memory Exception.";
    }
}
