package org.rayjars.diff;


import difflib.DiffRow;
import difflib.DiffRow.Tag;
import org.rayjars.diff.utils.TimeElapsedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SideBySideView {

	private final List<Line> lines;

    private final String leftName;

    private final String rightName;

    private int counterDifference = 0;

    private long startTime = 0l;

	private Logger logger = LoggerFactory.getLogger(SideBySideView.class);
    
    public SideBySideView(String leftName, String rightName) {
    	this.leftName = leftName;
    	this.rightName = rightName;
    	lines = new ArrayList<Line>();
    }
    
    public void addLine(Line line) {
        lines.add(line);
    }

    public SideBySideView startTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long getStartTime() {
        return startTime;
    }

    public List<Line> getLines(){
    	return lines;
    }
    
    public String getLeftName() {
		return leftName;
	}

	public String getRightName() {
		return rightName;
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
            	counterDifference++;
            }
            
            addLine(line);
        }
        
        logger.debug("executed time view : {}", TimeElapsedUtils.formatAsNano(System.nanoTime() - start));
        
        return this;
    }

    public int getCounterDifferences() {
		return counterDifference;
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
