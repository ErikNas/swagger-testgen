package org.example.model.swagger;

import java.util.Map;

public class PathItem {

    public Operation get;
    public Operation post;
    public Operation put;
    public Operation patch;
    public Operation delete;

    public Map<String, Operation> operations;

}
