package org.example;

import org.example.logging.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataManager {

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    private static boolean isUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String readURLToString(String url) {

        String data = "";
        try {
            URL urlObject = new URL(url);
            URLConnection urlConnection = urlObject.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            data = readFromInputStream(inputStream);
        } catch (IOException e) {
            Logger.err(e);
        }

        return data;
    }

    public static String readFileToString(String fileName) {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (NoSuchFileException e) {
            Logger.err("Unable to read file " + fileName);
        } catch (IOException e) {
            Logger.err(e);
        }
        return data;
    }

    public static String readFileToString(File file) {
        String data = "";
        try {
            data = new String(Files.readAllBytes(file.toPath()));
        } catch (NoSuchFileException e) {
            Logger.err("Unable to read file " + file.getPath());
        } catch (IOException e) {
            Logger.err(e);
        }
        return data;
    }

    public static String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    public static boolean isJson(String fileName) {
        String ext = getExtension(fileName);
        return ext.equalsIgnoreCase("json");
    }

    public static boolean isYaml(String fileName) {
        String ext = getExtension(fileName);
        return ext.equalsIgnoreCase("yml") || ext.equalsIgnoreCase("yaml");
    }

    private static boolean isSchema(String fileName) {
        return true;
    }

    public static String getJsonString(String path) {
        String json;
        if (isUrl(path)) {
            json = getJson(path, readURLToString(path));
        } else {
            json = getJson(path, readFileToString(path));
        }
        return json;
    }

    public static String getJsonString(File file) {
        return getJson(file.getName(), readFileToString(file));
    }

    public static String getJson(String fileName, String rawString) {
        String json = "";

        if (isYaml(fileName)) {
            // todo
        } else if (isJson(fileName)) {
            json = rawString;
        }

        return json;
    }

    public static String getSchemaString(String path) {
        String schema;
        if (isUrl(path)) {
            schema = getSchema(path, readURLToString(path));
        } else {
            schema = getSchema(path, readFileToString(path));
        }
        return schema;
    }

    public static String getSchemaString(File file) {
        return getSchema(file.getName(), readFileToString(file));
    }

    public static String getSchema(String fileName, String rawString) {
        String schema = "";

        if (isYaml(fileName)) {
            // todo
        } else if (isJson(fileName)) {
            // todo
        } else if (isSchema(fileName)) {
            schema = rawString;
        }

        return schema;
    }

    public static List<List<String>> getJSONObjectFromJSON(String fileName) {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromCSVLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            Logger.err(e);
        }

        return records;
    }

    private static List<String> getRecordFromCSVLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(";");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next().trim());
            }
        }
        return values;
    }
}
