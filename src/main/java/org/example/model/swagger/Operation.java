package org.example.model.swagger;

import java.util.List;
import java.util.Map;

public class Operation {

    public List<String> tags;
    public String summary;
    public String operationId;
    public List<Parameter> parameters;
    public RequestBody requestBody;
    public Map<String, ApiResponse> responses;
    public List<Map<String, List<String>>> security;
}
