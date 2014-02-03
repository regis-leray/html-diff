package org.rayjars.diff.builder;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.rayjars.diff.SideBySideView;
import org.rayjars.diff.TemplateLoader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * RowBuilder
 * <p/>
 * Author:regis
 * Date: 14-02-02 16:10
 */
public class DiffRowBuilder extends TemplateBuilder {

    private static final String RIGHT_CONTENT = "rightContent";
    private static final String RIGHT_CSS = "rightCss";
    private static final String RIGHT_LINE_NUMBER = "rightLineNumber";
    private static final String LEFT_CONTENT = "leftContent";
    private static final String LEFT_CSS = "leftCss";
    private static final String LEFTLINE_NUMBER = "leftlineNumber";

    @Override
    protected void build(BufferedWriter writer, SideBySideView view) throws IOException {
        final String rowTemplate = templateLoader.loadTemplateAsString(TemplateLoader.ROW_TEMPLATE);

        if(view.getLines().isEmpty()){
            view.getLines().add(new SideBySideView.Line());
        }

        for (SideBySideView.Line line : view.getLines()) {
            writer.write(row(rowTemplate, line));
        }
    }

    private String row(String rowTemplate, SideBySideView.Line line) {
        Map<String, String> values = new HashMap<String, String>();
        values.put(LEFTLINE_NUMBER, line.left.getNumber());
        values.put(LEFT_CSS, line.left.cssClass);
        values.put(LEFT_CONTENT,  line.left.text);

        values.put(RIGHT_LINE_NUMBER, line.right.getNumber());
        values.put(RIGHT_CSS, line.right.cssClass);
        values.put(RIGHT_CONTENT,  line.right.text);

        StrSubstitutor sub = new StrSubstitutor(values, prefix, suffix);
        return sub.replace(rowTemplate);
    }
}
