package org.example.thymeleaf;

import org.json.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.example.thymeleaf.Constants.*;

public class Processor {
    TemplateEngine textTemplateEngine;

    public Processor() {
        textTemplateEngine = new TemplateEngine();
        textTemplateEngine.addTemplateResolver(new ThymeleafConfig().textTemplateResolver());
    }

    public void process(String template, String out, Context context) throws IOException {
        String result = textTemplateEngine.process(template, context);
        if (!out.startsWith(outFolder)) {
            out = outFolder + out;
        }
        writeStringToFile(new File(out), result, "UTF-8");
    }

    public void process(String template, String out, Map<String, Object> contextMap) throws IOException {
        Context context = new Context();
        context.setVariables(contextMap);
        process(template, out, context);
    }

    public void process(String template, String out, JSONObject contextJsonObject) throws IOException {
        process(template, out, contextJsonObject.toMap());
    }

    public void processToJava(String template, Map<String, Object> contextMap) throws IOException {
        Context context = new Context();
        context.setVariables(contextMap);
        String out = template.replace(".txt", ".java");
        process(templateCodePath + template, outCodePath + out, context);
    }

    public void processToJava(String template, String outName, Map<String, Object> contextMap) throws IOException {
        Context context = new Context();
        context.setVariables(contextMap);
        process(templateCodePath + template, outCodePath + outName, context);
    }

    public void processToJava(String template, JSONObject contextJsonObject) throws IOException {
        String out = template.replace(".txt", ".java");
        process(templateCodePath + template, outCodePath + out, contextJsonObject.toMap());
    }
}
