package org.example.output;

import java.util.ArrayList;
import java.util.List;

public class Msg {

    private List<Object> stack;
    private final String prefix;

    public Msg() {
        prefix = "";
        stack = new ArrayList<>();
    }

    public Msg(String prefixText) {
        prefix = "[" + prefixText + "] ";
        stack = new ArrayList<>();
    }

    public Msg add(String msgText) {
        stack.add(msgText);
        return this;
    }

    public Msg separate() {
        stack.add(System.lineSeparator());
        return this;
    }

    public Msg line() {
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
