package org.example;

import org.example.subcommands.EndpointsGenerator;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(name = "swagger-testgen",
        sortOptions = false,
        version = "swagger-testgen 0.1 beta",
        mixinStandardHelpOptions = true,
        headerHeading = "Использование:",
        synopsisHeading = " ",
        descriptionHeading = "%n",
        description = {"Утилита позволяет генерировать тестовые артефакты на базе описания из swagger.",
                "Введите 'swagger-testgen <command> --help' чтобы получить информацию по команде."},
        parameterListHeading = "%nПараметры команд:%n",
        optionListHeading = "%nОпции:%n",
        commandListHeading = "%nКоманды:%n",
        subcommands = {EndpointsGenerator.class})
public class Testgen {

    @Option(names = {"-d", "--debug"}, description = "Enable debug mode.", hidden = true)
    public boolean debug;

}
