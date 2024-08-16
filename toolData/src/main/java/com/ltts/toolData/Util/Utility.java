package com.ltts.toolData.Util;

public class Utility {
	
	 public static String parseDuration(String input) {
	        String[] timeUnits = {"year", "month", "week", "day", "hour", "minute", "second"};
	        String[] isoDesignators = {"Y", "M", "W", "D", "H", "M", "S"};
	        
	        StringBuilder duration = new StringBuilder("P");

	        input = input.toLowerCase().replaceAll(",", "");
	        String[] parts = input.split(" and ");

	        boolean timePartStarted = false;

	        for (String part : parts) {
	            for (int i = 0; i < timeUnits.length; i++) {
	                String unit = timeUnits[i];
	                if (part.contains(unit)) {
	                    String[] split = part.split(" ");
	                    int value = Integer.parseInt(split[0]);
	                    String designator = isoDesignators[i];

	                    if (i >= 4 && !timePartStarted) {
	                        duration.append("T");
	                        timePartStarted = true;
	                    }

	                    duration.append(value).append(designator);
	                }
	            }
	        }

	        return duration.toString();
	    }
	 
	 public static String parseToCronExpression(String input) {
	        // Normalize the input
	        String normalizedInput = input.toLowerCase().trim();

	        // Add parsing logic for different input formats
	        if (normalizedInput.matches("every hour on the hour from 9-5 p.m. utc monday-friday")) {
	            return "0 0 9-17 * * MON-FRI";
	        }

	        if (normalizedInput.matches("at \\d{1,2}:\\d{2} (am|pm) every (monday|tuesday|wednesday|thursday|friday|saturday|sunday)")) {
	            return convertSingleDay(normalizedInput);
	        }

	        if (normalizedInput.matches("at \\d{1,2}:\\d{2} (am|pm) on (monday|tuesday|wednesday|thursday|friday|saturday|sunday)s?(, (monday|tuesday|wednesday|thursday|friday|saturday|sunday)s?)*")) {
	            return convertMultipleDays(normalizedInput);
	        }

	        if (normalizedInput.matches("at \\d{1,2}:\\d{2} (am|pm) on (monday|tuesday|wednesday|thursday|friday|saturday|sunday)s? in (january|february|march|april|may|june|july|august|september|october|november|december)")) {
	            return convertDayAndMonth(normalizedInput);
	        }

	        return "Invalid input format";
	    }

	    public static String convertSingleDay(String input) {
	        String[] parts = input.split(" ");
	        String time = parts[1];
	        String period = parts[2];
	        String day = parts[4].toUpperCase();

	        int hour = Integer.parseInt(time.split(":")[0]);
	        int minute = Integer.parseInt(time.split(":")[1]);
	        if (period.equals("pm") && hour < 12) {
	            hour += 12;
	        } else if (period.equals("am") && hour == 12) {
	            hour = 0;
	        }

	        return String.format("0 %d %d * * %s", minute, hour, day.substring(0, 3).toUpperCase());
	    }

	    public static String convertMultipleDays(String input) {
	        String[] parts = input.split(" ");
	        String time = parts[1];
	        String period = parts[2];
	        String[] days = parts[4].split(", ");

	        int hour = Integer.parseInt(time.split(":")[0]);
	        int minute = Integer.parseInt(time.split(":")[1]);
	        if (period.equals("pm") && hour < 12) {
	            hour += 12;
	        } else if (period.equals("am") && hour == 12) {
	            hour = 0;
	        }

	        StringBuilder cronDays = new StringBuilder();
	        for (String day : days) {
	            if (cronDays.length() > 0) {
	                cronDays.append(",");
	            }
	            cronDays.append(day.substring(0, 3).toUpperCase());
	        }

	        return String.format("0 %d %d * * %s", minute, hour, cronDays.toString());
	    }

	    public static String convertDayAndMonth(String input) {
	        String[] parts = input.split(" ");
	        String time = parts[1];
	        String period = parts[2];
	        String[] days = parts[4].split(", ");
	        String month = convertMonthToCron(parts[6]);

	        int hour = Integer.parseInt(time.split(":")[0]);
	        int minute = Integer.parseInt(time.split(":")[1]);
	        if (period.equals("pm") && hour < 12) {
	            hour += 12;
	        } else if (period.equals("am") && hour == 12) {
	            hour = 0;
	        }

	        StringBuilder cronDays = new StringBuilder();
	        for (String day : days) {
	            if (cronDays.length() > 0) {
	                cronDays.append(",");
	            }
	            cronDays.append(day.substring(0, 3).toUpperCase());
	        }

	        return String.format("0 %d %d ? %s %s", minute, hour, month, cronDays.toString());
	    }

	    public static String convertMonthToCron(String month) {
	        switch (month) {
	            case "january": return "1";
	            case "february": return "2";
	            case "march": return "3";
	            case "april": return "4";
	            case "may": return "5";
	            case "june": return "6";
	            case "july": return "7";
	            case "august": return "8";
	            case "september": return "9";
	            case "october": return "10";
	            case "november": return "11";
	            case "december": return "12";
	            default: return ""; // Handle invalid month
	        }
	    }
	            


	 
}