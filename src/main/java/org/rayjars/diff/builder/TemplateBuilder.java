package org.rayjars.diff.builder;


import org.rayjars.diff.SideBySideView;
import org.rayjars.diff.TemplateLoader;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class TemplateBuilder {

    private static final String PREFIX = "@";
    private static final String SUFFIX = "@";

    protected final String prefix;
    protected final String suffix;

    protected TemplateLoader templateLoader;

    protected TemplateBuilder() {
        this(PREFIX, SUFFIX);
    }

    public TemplateBuilder(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        setTemplateLoader(new TemplateLoader());
    }

    public void setTemplateLoader(TemplateLoader templateLoader) {
        this.templateLoader = templateLoader;
    }

    protected abstract void build(BufferedWriter writer, SideBySideView view) throws IOException ;

}
