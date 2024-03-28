package org.example;

import picocli.CommandLine;

public class App {

    public static void main(String[] args) {
        new CommandLine(new Testgen()).execute(args);
    }

}
