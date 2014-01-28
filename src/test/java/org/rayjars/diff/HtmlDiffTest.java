package org.rayjars.diff;

import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;


import static org.hamcrest.MatcherAssert.assertThat;

public class HtmlDiffTest {




    @Test
    public void shouldCreateDiffWithString() throws FileNotFoundException {
        HtmlDiff diff = new HtmlDiff();
        diff.diff("My Name is Html Diff", "My name is Diff");
    }

    @Test
    public void shouldCreateDiffWithFile() throws Exception {
        File left = getFileFromClasspath("1.txt");
        File right = getFileFromClasspath("2.txt");

        HtmlDiff diff = new HtmlDiff();
        diff.diff(left, right);
    }


    @Test
    public void shouldHaveIgnoreBlankLineToTrue(){
        HtmlDiff diff = new HtmlDiff();
        diff.setIgnoreBlankLines(true);

        assertThat(diff.isIgnoreBlankLines(), org.hamcrest.core.Is.is(true));
    }

    @Test
    public void shouldHaveIgnoreWhitespaceToTrue(){
        HtmlDiff diff = new HtmlDiff();
        diff.setIgnoreWhitespaces(true);

        assertThat(diff.isIgnoreWhitespaces(), org.hamcrest.core.Is.is(true));
    }

    @Test
    public void shouldHaveShowInlineDiffsToTrue(){
        HtmlDiff diff = new HtmlDiff();
        diff.setShowInlineDiffs(true);

        assertThat(diff.isShowInlineDiffs(), org.hamcrest.core.Is.is(true));
    }

    @Test
    public void shouldHaveColumnWith120Colons(){
        HtmlDiff diff = new HtmlDiff();
        diff.setColumnWidth(120);

        assertThat(diff.getColumnWidth(), org.hamcrest.core.Is.is(120));
    }


    private File getFileFromClasspath(String fileName) throws URISyntaxException {
        URL urlFile = HtmlDiffTest.class.getClassLoader().getResource(fileName);
        return new File(urlFile.toURI());
    }
}
