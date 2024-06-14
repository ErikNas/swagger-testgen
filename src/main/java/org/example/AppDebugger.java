package org.example;

import org.example.thymeleaf.TestProjectGenerator;

import java.io.IOException;
import java.net.URISyntaxException;

public class AppDebugger {
    public static void main(String[] args) throws IOException, URISyntaxException {
        new TestProjectGenerator().generate();
    }
}
