package org.rayjars.diff.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * IOUtils
 * <p/>
 * Author:regis
 * Date: 14-02-02 18:41
 */
public class IOUtils {
    public static void closeQuietly(java.io.Closeable writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    public static List readLines(InputStream input) throws IOException {
        InputStreamReader reader = new InputStreamReader(input);
        return readLines(reader);
    }

    public static List readLines(Reader input) throws IOException {
        BufferedReader reader = new BufferedReader(input);
        List list = new ArrayList();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }

        closeQuietly(reader);
        return list;
    }

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = null;
        try{
            s = new java.util.Scanner(is).useDelimiter("\\A");

            return s.hasNext() ? s.next() : "";
        }finally{
            s.close();
        }
    }
}
