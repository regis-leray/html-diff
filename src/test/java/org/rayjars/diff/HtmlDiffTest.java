package org.rayjars.diff;

import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;


import static org.hamcrest.MatcherAssert.assertThat;

public class HtmlDiffTest {

    @Test
    public void shouldCreateDiffWithString() throws IOException {

        DiffParams params = new DiffParams.Builder()
                .left("My Name is Html Diff")
                .right("My name is Diff")
                .build();

        FileOutputStream outputStream = (FileOutputStream) new HtmlDiff().diff(params);

        assertThat(outputStream, org.hamcrest.core.IsNull.notNullValue());
    }

    @Test
    public void shouldCreateDiffWithFile() throws Exception {
        File left = getFileFromClasspath("1.txt");
        File right = getFileFromClasspath("2.txt");

        DiffParams params = new DiffParams.Builder()
                .left(left)
                .leftName("old.txt")
                .right(right)
                .rightName("new.txt")
                .build();


        HtmlDiff diff = new HtmlDiff();
        diff.diff(params);
    }

    @Test
    public void shouldCreateDiffWithOldAndNewFile() throws Exception {
        File left = getFileFromClasspath("old.txt");
        File right = getFileFromClasspath("new.txt");

        DiffParams params = new DiffParams.Builder()
                .left(left)
                .right(right)
                .build();

        HtmlDiff diff = new HtmlDiff();
        diff.diff(params);
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
