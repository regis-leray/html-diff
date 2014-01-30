html-diff
=========

Create a html diff of text file / side by side

```
DiffParams params = new DiffParams.Builder()
                .left(new File("old.txt"))
                .right(new File("new.txt"))
                .build();

new HtmlDiff().diff(params);
```

By default it generate a diff html file in your tmp directory by using the system property java.io.tmpdir.

You can specify an output file
```
new HtmlDiff()
    .setOutput(new File("/home/scott/test.html"))
    .diff(params);
```

![alt tag](https://raw.github.com/regis-leray/html-diff/master/screenshot.png)


