package org.example.cli;

import org.example.cli.subcommands.MainGenerator;

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
        subcommands = {MainGenerator.class //, SomeClass.class, SomeClass2.class
        })
public class CliRunner {

    @Option(names = {"-d", "--debug"}, description = "Enable debug mode.", hidden = true)
    public boolean debug;

    @Option(names = {"-h", "--help"},
            description = "Выводит эту справку на экран")
    public boolean libList2 = false;

}
