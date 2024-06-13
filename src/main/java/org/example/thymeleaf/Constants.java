package org.example.thymeleaf;

public class Constants {
    public static String templateResourcePath = "./src/main/resources/";
    public static String projectName = "yourservice";

    public static String packageName = "ru.ispovedalnya." + projectName;
    public static String outFolder = "out/" + projectName + "/";
    public static String templateFolder = templateResourcePath + "templates-thymeleaf";

    public static String templateCodePath = "./src/test/java/ru/yourcompany/yourservice/";
    public static String outCodePath = outFolder + "src/test/java/" + packageName.replace(".", "/") + "/";

    public static String templateResourcePath2 = "./src/test/resources/";
    public static String outResourcePath = outFolder + "/src/test/resources/";
}
