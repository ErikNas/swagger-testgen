package org.example;

import org.example.cli.CliRunner;
import picocli.CommandLine;

public class App {

    public static void main(String[] args) {
        new CommandLine(new CliRunner()).execute(args);
    }

}
