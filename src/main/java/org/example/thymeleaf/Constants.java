package org.example.thymeleaf;

public class Constants {
    public static String projectName = "yourservice";

    public static String packageName = "ru.ispovedalnya." + projectName;
    public static String outFolder = "out/" + projectName + "/";
    public static String templateFolder = "./src/main/resources/templates-thymeleaf";

    public static String templateCodePath = "./src/test/java/ru/rtkit/yourservice/";
    public static String outCodePath = outFolder + "src/test/java/" + packageName.replace(".", "/") + "/";;

    public static String templateResourcePath = "./src/test/resources/";
    public static String outResourcePath = outFolder + "/src/test/resources/";
}
