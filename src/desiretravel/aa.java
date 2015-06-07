package desiretravel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class aa {
	public static void main(String [] args){
		String input = "06/25/2015";
		LocalDate localDate = LocalDate.parse(input, DateTimeFormatter
		            .ofPattern("MM/dd/yyyy").withLocale(Locale.ENGLISH));
		System.out.println(localDate);
	}
}
