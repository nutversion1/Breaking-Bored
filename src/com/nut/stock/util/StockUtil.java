package com.nut.stock.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.Locale;

import javafx.collections.ObservableList;
import javafx.util.Pair;

public class StockUtil {

	public static String getTimeString(int hour, int minute){
		String hourStr = Integer.toString(hour);
		if(hourStr.length() == 1){
			hourStr = "0"+hourStr;
		}

		String minuteStr = Integer.toString(minute);
		if(minuteStr.length() == 1){
			minuteStr = "0"+minuteStr;;
		}

		return hourStr + ":" + minuteStr + ":" +"00";

	}

	public static int getHourInt(String timeStr){
		String hourStr = timeStr.toString().substring(0,2);
		if(hourStr.charAt(0) == '0'){
			hourStr = hourStr.substring(1);
		}

		return Integer.parseInt(hourStr);

	}

	public static int getMinuteInt(String timeStr){
		String minuteStr = timeStr.toString().substring(3,5);
		if(minuteStr.charAt(0) == '0'){
			minuteStr = minuteStr.substring(1);
		}

		return Integer.parseInt(minuteStr);

	}

	public static int getMonthInt(String monthStr){
		if(monthStr.charAt(0) == '0'){
			monthStr = monthStr.substring(1);
		}

		return Integer.parseInt(monthStr);
	}

	public static String getDayOfWeek(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter); // LocalDate = 2010-02-23
		DayOfWeek dow = localDate.getDayOfWeek();  // Extracts a `DayOfWeek` enum object.
		String output = dow.getDisplayName(TextStyle.SHORT, Locale.US); // String = Tue

		return output;
	}



}
