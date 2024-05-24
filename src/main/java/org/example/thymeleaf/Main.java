package org.example.thymeleaf;

import org.example.DataManager;
import org.example.model.inner.Endpoint;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.homyakin.iuliia.Schemas;
import ru.homyakin.iuliia.Translator;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.apache.commons.io.FileUtils.copyDirectory;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.example.thymeleaf.Constants.*;

public class Main {
    static String swaggerSpec = "./src/main/resources/SwaggerSpecExample.json";
    static Translator translator = new Translator(Schemas.ICAO_DOC_9303);

    public static void main(String[] args) throws IOException {
        Processor proc = new Processor();
        BaseTemplateDataSet baseDataSet = new BaseTemplateDataSet();

        copyFile(new File(templateFolder + "/.gitignore"),
                new File(outFolder + "/.gitignore"));
        copyFile(new File(templateFolder + "/gradlew"),
                new File(outFolder + "/gradlew"));
        copyFile(new File(templateFolder + "/gradlew.bat"),
                new File(outFolder + "/gradlew.bat"));
        copyFile(new File(templateFolder + "/src/test/resources/allure.properties"),
                new File(outFolder + "/src/test/resources/allure.properties"));
        copyFile(new File(templateFolder + "/src/test/resources/stage.properties"),
                new File(outFolder + "/src/test/resources/stage.properties"));
        copyFile(new File(templateFolder + "/src/test/resources/demo.properties"),
                new File(outFolder + "/src/test/resources/demo.properties"));

        copyFile(new File(templateFolder + "/build.txt"), new File(outFolder + "/build.gradle"));
        proc.process("settings.txt", "settings.gradle", baseDataSet.get());
        copyDirectory(new File(templateFolder + "/gradle"), new File(outFolder + "/gradle"));

        proc.processToJava("config/AppConfig.txt", baseDataSet.get());
        proc.processToJava("config/AppConfigProvider.txt", baseDataSet.get());
        proc.processToJava("helpers/allure/AllureAssertions.txt", baseDataSet.get());
        proc.processToJava("helpers/allure/AllureHelper.txt", baseDataSet.get());
        proc.processToJava("helpers/api/ApiHelper.txt", baseDataSet.get());
        proc.processToJava("helpers/api/BackApiHelper.txt", baseDataSet.get());
        proc.processToJava("helpers/db/DbHelper.txt", baseDataSet.get());
        proc.processToJava("helpers/GenerationHelper.txt", baseDataSet.get());
        proc.processToJava("listeners/SeverityToDisplayName.txt", baseDataSet.get());
        proc.processToJava("listeners/TmsLinkToDisplayName.txt", baseDataSet.get());
        proc.processToJava("listeners/CleaningListener.txt", baseDataSet.get());
        proc.processToJava("junitEngine/CustomParallelExecutionConfiguration.txt", baseDataSet.get());
        proc.processToJava("junitEngine/CustomParallelExecutionStrategy.txt", baseDataSet.get());
        proc.processToJava("models/db/TblModel1Db.txt", baseDataSet.get());
        proc.processToJava("models/db/TblModel2Db.txt", baseDataSet.get());

        String jsonString = DataManager.getJsonString(swaggerSpec);
        JSONObject swaggerJson = new JSONObject(jsonString);

        JSONObject pathsJson = swaggerJson.getJSONObject("paths");
        Set<String> paths = pathsJson.keySet();
        for (String path : paths) {

            JSONObject endpointInfo = pathsJson.getJSONObject(path);
            endpointInfo.put("endpoint", path);
            endpointInfo.put("endpointVarName", Endpoint.pathToVarName(path));
            endpointInfo.put("packageName", packageName);
            String tagToPackage = tagToPackageName(endpointInfo);
            endpointInfo.put("tag", tagToPackage);
//            GET
            if (pathsJson.getJSONObject(path).has("get")) {
                endpointInfo.put("testName", "Get" + generateTestName(path));
                addInfoAboutQueryParams(endpointInfo, "get");
                addInfoAboutPathParams(endpointInfo, "get");
                proc.processToJava(
                        "tests/GetTest.txt",
                        String.format("tests/%s/Get%s.java", tagToPackage, generateTestName(path)),
                        endpointInfo.toMap());
            }
//            POST
            if (pathsJson.getJSONObject(path).has("post")) {
                endpointInfo.put("testName", "Post" + generateTestName(path));
                addInfoAboutQueryParams(endpointInfo, "post");
                addInfoAboutPathParams(endpointInfo, "post");
                proc.processToJava(
                        "tests/PostTest.txt",
                        String.format("tests/%s/Post%s.java", tagToPackage, generateTestName(path)),
                        endpointInfo.toMap());
            }
//            PUT
            if (pathsJson.getJSONObject(path).has("put")) {
                endpointInfo.put("testName", "Put" + generateTestName(path));
                addInfoAboutQueryParams(endpointInfo, "put");
                addInfoAboutPathParams(endpointInfo, "put");
                proc.processToJava(
                        "tests/PutTest.txt",
                        String.format("tests/%s/Put%s.java", tagToPackage, generateTestName(path)),
                        endpointInfo.toMap());
            }
//            PATCH
            if (pathsJson.getJSONObject(path).has("patch")) {
                endpointInfo.put("testName", "Patch" + generateTestName(path));
                addInfoAboutQueryParams(endpointInfo, "patch");
                addInfoAboutPathParams(endpointInfo, "patch");
                proc.processToJava(
                        "tests/PatchTest.txt",
                        String.format("tests/%s/Patch%s.java", tagToPackage, generateTestName(path)),
                        endpointInfo.toMap());
            }
//            DELETE
            if (pathsJson.getJSONObject(path).has("delete")) {
                endpointInfo.put("testName", "Delete" + generateTestName(path));
                addInfoAboutQueryParams(endpointInfo, "delete");
                addInfoAboutPathParams(endpointInfo, "delete");
                proc.processToJava(
                        "tests/DeleteTest.txt",
                        String.format("tests/%s/Delete%s.java", tagToPackage, generateTestName(path)),
                        endpointInfo.toMap());
            }
        }

        swaggerJson.put("packageName", packageName);

        proc.processToJava("helpers/api/Endpoints.txt", Endpoint.generate(swaggerJson), packageName);

        proc.processToJava("tests/BaseTest.txt", swaggerJson.toMap());

        proc.process("./src/test/resources/META-INF/services/org.junit.jupiter.api.extension.Extension.txt",
                "/src/test/resources/META-INF/services/org.junit.jupiter.api.extension.Extension",
                baseDataSet.get());
        proc.process("./src/test/resources/META-INF/services/org.junit.platform.launcher.TestExecutionListener.txt",
                "/src/test/resources/META-INF/services/org.junit.platform.launcher.TestExecutionListener",
                baseDataSet.get());
        proc.process("./src/test/resources/META-INF/persistence.txt",
                "/src/test/resources/META-INF/persistence.xml",
                baseDataSet.get());
        proc.process("./src/test/resources/junit-platform.properties.txt",
                "/src/test/resources/junit-platform.properties.properties",
                baseDataSet.get());
    }

    private static void addInfoAboutPathParams(JSONObject endpointInfo, String methodName) {
        JSONObject methodInfo = endpointInfo.getJSONObject(methodName);
        if (methodInfo.has("parameters")) {
            JSONArray parameters = methodInfo.getJSONArray("parameters");
            JSONArray pathParams = new JSONArray();
            for (Object parameter : parameters) {
                JSONObject parameterJson = (JSONObject) parameter;
//                полагается, что все path_params являются обязательными
                if ("path".equals(parameterJson.getString("in"))) {
                    pathParams.put(parameterJson);
                }
            }
            methodInfo.put("path_parameters", pathParams);
        }
    }

    private static void addInfoAboutQueryParams(JSONObject endpointInfo, String methodName) {
        JSONObject methodInfo = endpointInfo.getJSONObject(methodName);
        if (methodInfo.has("parameters")) {
            JSONArray parameters = methodInfo.getJSONArray("parameters");
            JSONArray obligatoryQueryParams = new JSONArray();
            JSONArray queryParams = new JSONArray();
            for (Object parameter : parameters) {
                JSONObject parameterJson = (JSONObject) parameter;
                if ("query".equals(parameterJson.getString("in"))) {
                    queryParams.put(parameterJson);
                    if (!parameterJson.has("required") || parameterJson.getBoolean("required")) {
                        obligatoryQueryParams.put(parameterJson);
                    }
                }
            }
            methodInfo.put("obligatory_query_parameters", obligatoryQueryParams);
            methodInfo.put("query_parameters", queryParams);
        }
    }

    private static String tagToPackageName(JSONObject endpointInfo) {
        String method;
        if (endpointInfo.has("post")) {
            method = "post";
        } else if (endpointInfo.has("get")) {
            method = "get";
        } else if (endpointInfo.has("patch")) {
            method = "patch";
        } else if (endpointInfo.has("put")) {
            method = "put";
        } else {
            method = "delete";
        }
        String tag = endpointInfo.getJSONObject(method)
                .getJSONArray("tags")
                .getString(0);
        return translator.translate(tag)
                .toLowerCase()
                .replace(" ", "_")
                .replace("-", "_");
    }

    private static String generateTestName(String path) {
        String[] strings = path.replace("{", "by/")
                .replace("}", "")
                .replace("-", "/")
                .replace("_", "/")
                .split("/");
        StringBuilder testName = new StringBuilder();
        for (String endpointPart : strings) {
            if (!"".equals(endpointPart)) {
                testName.append(endpointPart.substring(0, 1).toUpperCase())
                        .append(endpointPart, 1, endpointPart.length());
            }
        }
        testName.append("Test");
        return testName.toString();
    }
}
