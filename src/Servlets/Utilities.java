package Servlets;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

	public static String convertLongToDate(long dateCreated){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm");
		Date resultdate = new Date(dateCreated);
		return sdf.format(resultdate);
	}
	public static String convertLongToTime(long time){
		long second = (time / 1000) % 60;
		long minute = (time / (1000 * 60)) % 60;
		long hour = (time / (1000 * 60 * 60)) % 24;
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}
}
