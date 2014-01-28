package org.rayjars.diff;


import difflib.DiffRow;
import difflib.DiffRow.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SideBySideView {
    private static final String NOT_EQUALS = "NOT_EQUALS";
	private static final String EQUALS = "EQUALS";
	private final List<Line> lines;
    private final String leftFilePath;
    private final String rightFilePath;
    private boolean isEquals = true;
    private int counterDifference = 0;
    
	private Logger logger = LoggerFactory.getLogger(SideBySideView.class);
    
    public SideBySideView(String leftFilePath, String rightFilePath) {
    	this.leftFilePath = leftFilePath;
    	this.rightFilePath = rightFilePath;
    	lines = new ArrayList<Line>();
    }
    
    public void addLine(Line line) {
        lines.add(line);
    }

	public List<Line> getLines(){
    	return lines;
    }
    
    public String getLeftFilePath() {
		return leftFilePath;
	}

	public String getRightFilePath() {
		return rightFilePath;
	}

	public SideBySideView build(List<DiffRow> diffRows){
    	int leftPos = 1;
        int rightPos = 1;
        long start = System.nanoTime();
        
        for (DiffRow row : diffRows) {
            SideBySideView.Line line = new SideBySideView.Line();

            Tag tag = row.getTag();
            if (tag == Tag.INSERT) {
                line.left.cssClass = "empty";

                line.right.number = rightPos;
                line.right.text = row.getNewLine();
                line.right.cssClass = "insert";
                rightPos++;

            } else if (tag == Tag.CHANGE) {
                line.left.number = leftPos;
                line.left.text = row.getOldLine();
                line.right.cssClass = "change";
                leftPos++;

                line.right.number = rightPos;
                line.right.text = row.getNewLine();
                line.right.cssClass = "change";
                rightPos++;

            } else if (tag == Tag.DELETE) {
                line.left.number = leftPos;
                line.left.text = row.getOldLine();
                line.left.cssClass = "delete";
                leftPos++;

                line.right.cssClass = "empty";

            } else if (tag == Tag.EQUAL) {
                line.left.number = leftPos;
                line.left.text = row.getOldLine();
                line.left.cssClass = "equal";
                leftPos++;

                line.right.number = rightPos;
                line.right.text = row.getNewLine();
                line.right.cssClass = "equal";
                rightPos++;

            } else {
                throw new IllegalStateException("Unknown pattern tag: " + tag);
            }
            
            if (tag != Tag.EQUAL){
            	isEquals = false;
            	counterDifference++;
            }
            
            addLine(line);
        }
        
        logger.debug("executed time view : {}",TimeElapsedUtils.formatAsNano(System.nanoTime() - start));
        
        return this;
    }
    
    public boolean isEquals() {
		return isEquals;
	}
    
    public String status(){
    	return isEquals() ? EQUALS : NOT_EQUALS;
    }
    

    public int getCounterDifferences() {
		return counterDifference;
	}
    
    public void toHtml(File outputFile, long elpasedTime){
    	
    	new HtmlView().toHtml(outputFile, elpasedTime, this);
    }
    
	public static class Line {
    	public Item left = new Item();
    	public Item right = new Item();
    	
        public static class Item {
        	public Integer number;
            public String text = "";
            public String cssClass = "";

            public String getNumber() {
                return (number == null) ? "" : String.valueOf(number);
            }
        }
    }
	
}
