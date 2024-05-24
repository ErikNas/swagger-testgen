package org.example.model.inner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Endpoint {

    public String path;
    public String varName;

    public Endpoint(String path) {
        this.path = path;
        this.varName = pathToVarName(path);
    }

    public static Map<String, Tag> generate(JSONObject swaggerJson) {
        Map<String, Tag> result = new LinkedHashMap<>();
        List<String> methodNames = List.of("get", "post", "put", "patch", "delete");
        JSONObject paths = swaggerJson.getJSONObject("paths");
        for (String endpointName : paths.keySet()) {
            JSONObject endpoint = paths.getJSONObject(endpointName);

            for (String methodName : methodNames) {
                if (!endpoint.has(methodName)) {
                    continue;
                }

                JSONObject curMethod = endpoint.getJSONObject(methodName);
                JSONArray tags = curMethod.getJSONArray("tags");

                for (Object tagName : tags) {
                    String name = tagName.toString();
                    result.putIfAbsent(name, new Tag(name));
                    result.get(name).endpoints.add(new Endpoint(endpointName));
                }
            }
        }

        return result;
    }

    public static String pathToVarName(String pathString) {
        String normEndpointName = pathString;
        if (pathString.startsWith("/")) {
            normEndpointName = pathString.substring(1);
        }
        return normEndpointName.toUpperCase()
                .replace("/{", "_BY_")
                .replace("}", "")
                .replace("-", "_")
                .replace("/", "_");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return Objects.equals(path, endpoint.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
