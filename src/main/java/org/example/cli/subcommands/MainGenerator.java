package org.example.cli.subcommands;

import org.example.logging.Logger;
import org.example.thymeleaf.TestProjectGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import static picocli.CommandLine.*;

@Command(name = "generate",
        mixinStandardHelpOptions = true,
        sortOptions = false,
        headerHeading = "Использование:",
        synopsisHeading = " ",
        descriptionHeading = "%n",
        parameterListHeading = "%nПараметры:%n",
        optionListHeading = "%nОпции:%n",
        description = {"Создать Java-проект с автотестами на базе swagger", "", "Пример:",
                "      swagger-testgen generate from.json /path/to/output",
                "      swagger-testgen generate http://my-swagger.ru/config ./"
        })
public class MainGenerator implements Runnable {

    @Option(names = {"-l", "--lib-list"},
            description = "Print list of validation library.")
    public boolean libList = false;

    @Option(names = {"-h", "--help"},
            description = "Работает.")
    public boolean libList2 = false;

    @Parameters(index = "0", arity = "0..1", description = "Файл или ссылка на спецификацию Swagger.")
    private String swaggerSpec;

    @Parameters(index = "1", arity = "0..1", description = "Путь до папки с результатом генерации.")
    private String resultPath;

    @Override
    public void run() {
        if (swaggerSpec == null || swaggerSpec.isEmpty()) {
            swaggerSpec = "/resources/SwaggerSpecExample.json";
        }
        try {
            TestProjectGenerator tpg = new TestProjectGenerator();
            tpg.setTemplateResourcePath("/resources/");
            tpg.setSwaggerSpec(swaggerSpec);
            tpg.generate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private final Logger logger = new Logger("Endpoints generator");

    private void userWait(String nextStepMsg) {
        logger.out(nextStepMsg + " Press enter to continue...");
        new Scanner(System.in).nextLine();
    }
}
