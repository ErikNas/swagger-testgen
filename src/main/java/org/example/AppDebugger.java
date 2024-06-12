package org.example;

import org.example.thymeleaf.TestProjectGenerator;

import java.io.IOException;

public class AppDebugger {
    public static void main(String[] args) throws IOException {
        new TestProjectGenerator().generate();
    }
}
