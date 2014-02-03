package org.rayjars.diff.utils;

public final class TimeElapsedUtils {
	private static String format = String.format("%%0%dd", 2);
	
	private TimeElapsedUtils(){
	}
	
	public static String formatAsNano(long nano) {
		StringBuilder buff = new StringBuilder(format(nano/1000000));
		
		if(nano % 1000 > 0){
        	add(buff, String.format(format, nano % 1000), "µs");
        }
		return buff.toString();
	}
	
	
	/**
	 * Only display the hours / minutes / seconds / milliseconds 
	 * 
	 * @param milliseconds : elapsed time in milliseconds
	 * 
	 * @return String formatted
	 */
	public static String format(long milliseconds) {
		
		StringBuilder buff = new StringBuilder();
		
		
        long elapsedTime = milliseconds / 1000;
        
        if(elapsedTime / 3600 > 0){
        	add(buff, String.format(format, elapsedTime / 3600), "h");
        }

        if((elapsedTime % 3600) / 60 > 0){
        	add(buff, String.format(format, (elapsedTime % 3600) / 60), "m");
        }
        
        if((elapsedTime % 60) > 0){
        	add(buff, String.format(format, elapsedTime % 60), "s");
        }
        
        if(milliseconds % 1000 > 0){
        	add(buff, String.format(format, milliseconds % 1000), "ms");
        }
        
        return buff.toString();
    }
	
	private static StringBuilder add(StringBuilder buff, String number, String scale){
		if(buff.length() > 0){
			buff.append(" ");
		}
		
		return buff.append(number).append(scale);
	}
	
}
