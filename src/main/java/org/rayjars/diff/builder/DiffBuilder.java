package org.rayjars.diff.builder;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.rayjars.diff.SideBySideView;
import org.rayjars.diff.TemplateLoader;
import org.rayjars.diff.utils.IOUtils;
import org.rayjars.diff.utils.TimeElapsedUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffBuilder extends TemplateBuilder{

	private static final String RIGHT_FILE_PATH = "rightFilePath";
	private static final String LEFT_FILE_PATH = "leftFilePath";
	private static final String GENERATED_TIME = "generatedTime";
	private static final String COUNTER_DIFFERENCE = "counterDifference";

	private static final String ROWS = "rows";
	
    private DiffRowBuilder rowBuilder;

    public DiffBuilder(){
        setRowBuilder(new DiffRowBuilder());
    }

    public void build(OutputStream output, SideBySideView view) throws IOException{
        BufferedWriter writer = null;

        try{
            writer = new BufferedWriter(new OutputStreamWriter(output));
            build(writer, view);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    @Override
    protected void build(BufferedWriter writer, SideBySideView view) throws IOException {
        Map<String, String> variables = buildVariables(view);
        final List<String> lines = templateLoader.loadTemplateAsLines(TemplateLoader.MAIN_TEMPLATE);
        final StrSubstitutor sub = new StrSubstitutor(variables, prefix, suffix);

        for (String line : lines) {
            if(line.contains(getRowsVariable())){
                rowBuilder.build(writer, view);
            }else {
                String content = sub.replace(line);
                writer.write(content);
                writer.write("\n");
            }
        }
    }

    private Map<String, String> buildVariables(SideBySideView view) {
        Map<String, String> variables = new HashMap<String, String>();
        variables.put(RIGHT_FILE_PATH, view.getRightName());
        variables.put(LEFT_FILE_PATH, view.getLeftName());
        variables.put(COUNTER_DIFFERENCE, String.valueOf(view.getCounterDifferences()));
        variables.put(GENERATED_TIME, TimeElapsedUtils.format(System.currentTimeMillis() - view.getStartTime()));
        return variables;
    }

    private String getRowsVariable(){
       return prefix+ROWS+suffix;
    }

    public void setRowBuilder(DiffRowBuilder rowBuilder) {
        this.rowBuilder = rowBuilder;
    }
}