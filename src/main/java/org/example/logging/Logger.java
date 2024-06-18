package org.example.logging;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private List<Object> stack;
    private final String prefix;

    public Logger() {
        prefix = "";
        stack = new ArrayList<>();
    }

    public Logger(String prefixText) {
        prefix = "[" + prefixText + "] ";
        stack = new ArrayList<>();
    }

    public Logger add(String msgText) {
        stack.add(msgText);
        return this;
    }

    public Logger separate() {
        stack.add(System.lineSeparator());
        return this;
    }

    public Logger line() {
        stack.add("--------------------------------------------");
        return this;
    }

    public void out() {
        for (Object msg : stack) {
            System.out.println(prefix + msg);
        }
        stack.clear();
    }

    public void out(String msgText) {
        for (Object msg : stack) {
            if (msg.equals(System.lineSeparator())) {
                System.out.println(msg);
            } else {
                System.out.println(prefix + msg);
            }
        }
        System.out.println(prefix + msgText);
        stack.clear();
    }

    public static void err(String msgText) {
        System.out.println(msgText);
    }

    public static void err(Exception e) {
        System.out.println(e.getMessage());
    }

}
