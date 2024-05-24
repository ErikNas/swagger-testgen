package org.example.thymeleaf;

import java.util.Map;

import static org.example.thymeleaf.Constants.packageName;
import static org.example.thymeleaf.Constants.projectName;

public class BaseTemplateDataSet {
    public Map<String, Object> get() {
        return Map.of(
                "packageName", packageName,
                "projectName", projectName
        );
    }
}
