package org.rayjars.diff;

import org.rayjars.diff.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TemplateLoader {

    public static final String MAIN_TEMPLATE = "/template.html";
    public static final String ROW_TEMPLATE = "/row.html";

    public List<String> loadTemplateAsLines(String templateName) {
        try {
            return IOUtils.readLines(getTemplate(templateName));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read the file template html file '" + templateName + "' from classpath", e);
        }
    }

    public String loadTemplateAsString(String templateName) {
       return IOUtils.convertStreamToString(getTemplate(templateName));
    }

    public InputStream getTemplate(String name) {
        return TemplateLoader.class.getResourceAsStream(name);
    }
}