package org.rayjars.diff;


import difflib.DiffRow;
import difflib.DiffRowGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HtmlDiff {

    public static final int DEFAULT_COLUMN_WITH = 120;

    private Boolean ignoreBlankLines = Boolean.FALSE;

    private Boolean ignoreWhitespaces = Boolean.FALSE;

    private Boolean showInlineDiffs = Boolean.FALSE;

    private int columnWidth = DEFAULT_COLUMN_WITH;

    private OutputStream output = null;

    private Logger logger = LoggerFactory.getLogger(HtmlDiff.class);

    public HtmlDiff(){
        try {
            output = new FileOutputStream(File.createTempFile("diff", ".html"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public HtmlDiff setOutput(OutputStream output) {
        this.output = output;
        return this;
    }

    public Boolean isIgnoreBlankLines() {
        return ignoreBlankLines;
    }

    public HtmlDiff setIgnoreBlankLines(Boolean ignoreBlankLines) {
        this.ignoreBlankLines = ignoreBlankLines;
        return this;
    }

    public Boolean isIgnoreWhitespaces() {
        return ignoreWhitespaces;
    }

    public HtmlDiff setIgnoreWhitespaces(Boolean ignoreWhitespaces) {
        this.ignoreWhitespaces = ignoreWhitespaces;
        return this;
    }

    public Boolean isShowInlineDiffs() {
        return showInlineDiffs;
    }

    public HtmlDiff setShowInlineDiffs(Boolean showInlineDiffs) {
        this.showInlineDiffs = showInlineDiffs;
        return this;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public HtmlDiff setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
        return this;
    }


    public OutputStream diff(DiffParams params) throws IOException {
        final long start = System.currentTimeMillis();

        List<String> referenceContent = IOUtils.readLines(params.getLeft());
        List<String> compareContent =  IOUtils.readLines(params.getRight());

        List<DiffRow> diffRows = diff(referenceContent, compareContent, start);

        SideBySideView view = new SideBySideView(params.getLeftName(), params.getRightName())
                .build(diffRows);
        view.toHtml(output, System.currentTimeMillis() - start);

        return output;
    }



    private List<DiffRow> diff(List<String> referenceContent, List<String> compareContent, long start) {
        List<DiffRow> diffRows = new ArrayList<DiffRow>();

        if(!referenceContent.isEmpty() || !compareContent.isEmpty()){
            DiffRowGenerator diffRowGenerator = new DiffRowGenerator.Builder()
                    .ignoreBlankLines(isIgnoreBlankLines())
                    .showInlineDiffs(isShowInlineDiffs())
                    .columnWidth(getColumnWidth())
                    .ignoreWhiteSpaces(isIgnoreWhitespaces()).build();

            diffRows = diffRowGenerator.generateDiffRows(referenceContent, compareContent);
            logger.debug("executed time diff : {}",TimeElapsedUtils.format(System.currentTimeMillis() - start));
        }
        return diffRows;
    }





}
