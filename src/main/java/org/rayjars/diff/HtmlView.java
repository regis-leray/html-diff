package org.rayjars.diff;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlView {
	
	private Logger logger = LoggerFactory.getLogger(HtmlView.class);
	
	private static final String PREFIX = "@";
	private static final String SUFFIX = "@";

	private static final String STATUS = "status";
	private static final String RIGHT_FILE_PATH = "rightFilePath";
	private static final String LEFT_FILE_PATH = "leftFilePath";
	private static final String GENERATED_TIME = "generatedTime";
	private static final String COUNTER_DIFFERENCE = "counterDifference";

	private static final String ROWS = "@rows@";
	
	private static final String RIGHT_CONTENT = "rightContent";
	private static final String RIGHT_CSS = "rightCss";
	private static final String RIGHT_LINE_NUMBER = "rightLineNumber";
	private static final String LEFT_CONTENT = "leftContent";
	private static final String LEFT_CSS = "leftCss";
	private static final String LEFTLINE_NUMBER = "leftlineNumber";
	
	private static final String TEMPLATE_HTML = "/template.html";
	private static final String ROW_HTML = "/row.html";

	public void toHtml(File outputFile, long elpasedTime, SideBySideView view) {
		long start = System.currentTimeMillis();

        Map<String, String> variables = buildVariables(view, elpasedTime);
		List<String> linesTemplate = getTemplateAsLines();
		final String rowTemplate = getRowTemplateAsString();
		final StrSubstitutor sub = new StrSubstitutor(variables, PREFIX, SUFFIX);
		
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(outputFile));

			for (String lineTemplate : linesTemplate) {
				String content = lineTemplate;
				
				if (StringUtils.contains(content, ROWS)) {
					if(view.getLines().isEmpty()){
						view.getLines().add(new SideBySideView.Line());
					}
					
					for (SideBySideView.Line line : view.getLines()) {
						br.write(row(rowTemplate, line));
					}
					
					
					continue;
				}else{
					content = sub.replace(lineTemplate);// propertyPlaceholderHelper.replacePlaceholders(lineTemplate, variables);
				}

				br.write(content);
			}
		} catch (Exception e) {
			throw new RuntimeException("Cannot write output file " + outputFile.getAbsolutePath(), e);
		} finally {
			IOUtils.closeQuietly(br);
		}
		
		logger.debug("time elapsed tohtml : {}",TimeElapsedUtils.format(System.currentTimeMillis() - start));
	}
	
	private Map<String, String> buildVariables(SideBySideView view, long elpasedTime) {
		Map<String, String> variables = new HashMap<String, String>();
		variables.put(STATUS, view.status());
		variables.put(RIGHT_FILE_PATH, view.getRightFilePath());
		variables.put(LEFT_FILE_PATH, view.getLeftFilePath());
		variables.put(COUNTER_DIFFERENCE, String.valueOf(view.getCounterDifferences()));
		variables.put(GENERATED_TIME, TimeElapsedUtils.format(elpasedTime));
		return variables;
	}

	private String row(String rowTemplate, SideBySideView.Line line) {
		
		Map<String, String> values = new HashMap<String, String>();
		values.put(LEFTLINE_NUMBER, line.left.getNumber());
		values.put(LEFT_CSS, line.left.cssClass);
		values.put(LEFT_CONTENT,  line.left.text);
		
		values.put(RIGHT_LINE_NUMBER, line.right.getNumber());
		values.put(RIGHT_CSS, line.right.cssClass);
		values.put(RIGHT_CONTENT,  line.right.text);
				
		StrSubstitutor sub = new StrSubstitutor(values, PREFIX, SUFFIX);
		return sub.replace(rowTemplate);
	}

	private List<String> getTemplateAsLines() {
		try {
			return IOUtils.readLines(getTemplate());
		} catch (IOException e) {
			throw new RuntimeException("Cannot read the file template html file '" + TEMPLATE_HTML + "' from classpath", e);
		}
	}

	private InputStream getTemplate() {
		return HtmlView.class.getResourceAsStream(TEMPLATE_HTML);
	}

	private String getRowTemplateAsString() {
		try {
			return IOUtils.toString(getRowTemplate());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private InputStream getRowTemplate() {
		return HtmlView.class.getResourceAsStream(ROW_HTML);
	}
}