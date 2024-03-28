package org.example.model.swagger;

import java.util.List;

public class Schema {
    public String type;
    public String format;
    public String description;
    public Integer minimum;
    public Boolean uniqueItems;
    public List<Schema> items;
}
