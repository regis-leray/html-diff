package org.rayjars.diff;


import java.io.*;

public class DiffParams {

    private final InputStream left;
    private final InputStream right;

    private final String leftName;
    private final String rightName;


    public DiffParams(InputStream left, InputStream right, String leftName, String rightName) {
        this.left = left;
        this.right = right;
        this.leftName = leftName;
        this.rightName = rightName;
    }

    public InputStream getLeft() {
        return left;
    }

    public InputStream getRight() {
        return right;
    }

    public String getLeftName() {
        return leftName;
    }

    public String getRightName() {
        return rightName;
    }

    public static final class Builder{
        private InputStream left;
        private InputStream right;

        private String leftName = "Left content";
        private String rightName = "Right content";

        public Builder leftName(String name){
            this.leftName = name;
            return this;
        }

        public Builder rightName(String name){
            this.rightName = name;
            return this;
        }

        public Builder left(String content){
            return left(new ByteArrayInputStream(content.getBytes()));
        }

        public Builder left(File content) throws FileNotFoundException {
            return left(new FileInputStream(content));
        }

        public Builder left(InputStream content){
            this.left = content;
            return this;
        }

        public Builder right(String content){
            return right(new ByteArrayInputStream(content.getBytes()));
        }

        public Builder right(File content) throws FileNotFoundException {
            return right(new FileInputStream(content));
        }

        public Builder right(InputStream content){
            this.right = content;
            return this;
        }

        public DiffParams build(){
            return new DiffParams(left, right, leftName, rightName);

        }

    }
}
