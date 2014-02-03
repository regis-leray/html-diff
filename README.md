Html-diff [![Build Status](https://travis-ci.org/regis-leray/html-diff.png?branch=master)](https://travis-ci.org/regis-leray/html-diff)
=========

The text comparaison file / Side by Side

How to use
==========

By default it generate a diff html file in your tmp directory by using the system property java.io.tmpdir.
```
DiffParams params = new DiffParams.Builder()
                .left(new File("old.txt"))
                .right(new File("new.txt"))
                .build();

new HtmlDiff().diff(params);
```

You can specify an output file :
```
new HtmlDiff()
    .setOutput(new File("/home/scott/test.html"))
    .diff(params);
```

Here an example :

![alt tag](https://raw.github.com/regis-leray/html-diff/master/screenshot.png)


