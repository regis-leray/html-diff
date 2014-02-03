package org.rayjars.diff;

import org.junit.Test;
import org.rayjars.diff.builder.DiffBuilder;

import java.io.File;
import java.io.FileOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

/**
 * HtmlViewTest
 * <p/>
 * Author:regis
 * Date: 14-02-02 15:46
 */
public class HtmlViewTest {


    @Test
    public void testToHtml() throws Exception {
        DiffBuilder view = new DiffBuilder();

        File outputFile = File.createTempFile("diff", ".html");
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        view.build(outputStream, new SideBySideView("left", "right").startTime(1000l));


        assertThat(outputFile.length(), greaterThan(0l));
    }
}
