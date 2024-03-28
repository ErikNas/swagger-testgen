package org.example.subcommands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DataManager;
import org.example.model.swagger.Root;
import org.json.JSONArray;
import org.json.JSONObject;
import org.example.model.swagger.Operation;
import org.example.output.Msg;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static picocli.CommandLine.*;

@Command(name = "endpoints",
        mixinStandardHelpOptions = true,
        sortOptions = false,
        headerHeading = "Использование:",
        synopsisHeading = " ",
        descriptionHeading = "%n",
        parameterListHeading = "%nПараметры:%n",
        optionListHeading = "%nОпции:%n",
        description = {"Создать Java-класс с описанием всех endpoints", "", "Пример:",
                "      swagger-testgen endpoints from.json to.java",
                "      swagger-testgen endpoints http://my-swagger.ru/config to.java"
        })
public class EndpointsGenerator implements Runnable {

    @Option(names = {"-l", "--lib-list"},
            description = "Print list of validation library.")
    public boolean libList = false;


    @Parameters(index = "0", arity = "0..1", description = "Файл или ссылка на спецификацию Swagger.")
    private String swaggerSpec;

    @Parameters(index = "1", arity = "0..1", description = "Результат генерации Endpoints.")
    private String resultFilePath;

    @Override
    public void run() {
        generate();
    }

    private final Msg msg = new Msg("Endpoints generator");

    private void userWait(String nextStepMsg) {
        msg.out(nextStepMsg + " Press enter to continue...");
        new Scanner(System.in).nextLine();
    }

    private void generate() {
        String jsonString = DataManager.getJsonString(swaggerSpec);
        Map<String, Set<String>> endpoints = extractEndpointsByPOJO(jsonString);
//       Map<String, Set<String>> endpoints = extractEndpointsByJSONObject(jsonString);
        writeEndpointsToFile(endpoints);

        msg.add("Файл с endpoints успешно сгенерирован.").out();
    }

    private Map<String, Set<String>> extractEndpointsByJSONObject(String jsonString) {

        Map<String, Set<String>> endpoints = new LinkedHashMap<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject paths = jsonObject.getJSONObject("paths");
        int pathsLength = paths.names().length();
        for (int i = 0; i < pathsLength; i++) {
            String pathKey = paths.names().getString(i);
            JSONObject pathJO = paths.getJSONObject(pathKey);

            int length = pathJO.names().length();
            for (int j = 0; j < length; j++) {
                String methodKey = pathJO.names().getString(j);
                JSONArray tags = pathJO.getJSONObject(methodKey).getJSONArray("tags");
                for (Object tagObject : tags) {
                    String tag = tagObject.toString();
                    endpoints.computeIfAbsent(tag, t -> new HashSet<>());
                    endpoints.get(tag).add(pathKey);
                }
            }
        }
        return endpoints;
    }

    private Map<String, Set<String>> extractEndpointsByPOJO(String jsonString) {
        Map<String, Set<String>> endpoints = new LinkedHashMap<>();
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            Root root = om.readValue(jsonString, Root.class);
            Map<String, Map<String, Operation>> paths = root.paths;
            for (Map.Entry<String, Map<String, Operation>> path : paths.entrySet()) {
                for (Map.Entry<String, Operation> op : path.getValue().entrySet()) {
                    for (String tag : op.getValue().tags) {
                        endpoints.computeIfAbsent(tag, t -> new HashSet<>());
                        endpoints.get(tag).add(path.getKey());
                    }
                }
            }
        } catch (JsonProcessingException e) {
            msg.add("Ошибка при попытке преобразования SPEC в POJO");
        }
        return endpoints;
    }

    private void writeEndpointsToFile(Map<String, Set<String>> endpoints) {
        String fileName = resultFilePath.isEmpty() ? resultFilePath : "Endpoints.txt";
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write("public class Endpoints {\n");
            Set<String> comments = endpoints.keySet();
            for (String comment : comments) {
                writer.append('\n');
                writer.write("// " + comment + "\n");

                Set<String> pathStrings = endpoints.get(comment);
                for (String pathString : pathStrings) {
                    String varName = pathString.toUpperCase()
                            .replace("/", "_")
                            .replace("-", "_")
                            .replace("{", "")
                            .replace("}", "");
                    if (varName.startsWith("_")) {
                        varName = varName.substring(1);
                    }
                    writer.write("public static final String " + varName + " = \""
                            + pathString + "\";\n");
                }
            }
            writer.write("}");
            writer.flush();
        } catch (IOException ex) {
            msg.add("Ошибка при попытке записи файла с результатом");
        }
    }
}
