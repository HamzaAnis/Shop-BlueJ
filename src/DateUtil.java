import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *
 * A class DateUtil with the following methods for dealing with dates.
 *
 *    public static String  convertDateToLongString(Date date)
 *    public static String  convertDateToShortString(Date date)
 *    public static Date    convertStringToDate(String dateString)
 *    public static int     daysBetween(Date startDate, Date endDate)
 *    public static Date    incrementDate(Date date, int noOfDays)
 *    public static boolean isLeapYear(int year)
 *    public static boolean isValidDateString(String dateString)
 *    public static Date    nextDate(Date date)
 *
 * @author D E Newton
 *
 */

public class DateUtil
{
   public static SimpleDateFormat dateFormatter;
   private static String longDatePattern;
   private static String shortDatePattern;

   /**
    *    initializer block -- useful for
    *    initializing static fields
    */
   static
   {
      shortDatePattern = "dd-MM-yyyy";       // dd = day, MM = month, yyyy = year (as 2, 2 and 4 digits respectively)
      longDatePattern = "EEEE, d MMMM yyyy"; // e.g. Friday, 25 March 2016
                                             // EEEE (4 or more) = day in week, in full e.g.Friday
                                             // MMMMM (4 or more) = month name as text in full e.g. March
                                             // d = day as 1 or 2 digits e.g. 25th of month -> 25, 23rd -> 23
                                             //
                                             // some alternatives below to help understanding (see documentation for SimpleDateFormat class)
                                             // EE (3 or less) -> Tue
                                             // MMMM              = month name as text in short e.g. Oct
                                             // M (or MM)         = month as digits e.g.  1 (or 01) would correspond to January
      dateFormatter = new SimpleDateFormat(shortDatePattern); // default pattern set
      dateFormatter.setLenient(false); // default is true and impossible dates such
                                       // as 31-09-2016 would be accepted but interpreted as 01-10-2016
   }

   /**
    * Converts a Date object to a corresponding String in
    * the long date pattern style "Friday, 25 January 2016".
    *
    * @param     date  a Date object
    *
    * @return    a String, containing a long date pattern
    */
   public static String convertDateToLongString(Date date)
   {
      dateFormatter.applyPattern(longDatePattern);
      String dateString = dateFormatter.format(date);
      dateFormatter.applyPattern(shortDatePattern);  // reset pattern
      return dateString;
   }

   /**
    * Converts a Date object to a corresponding String in
    * the short date pattern style "25-03-2016".
    *
    * @param     date  a Date object
    *
    * @return    a String, containing a short date pattern
    */
   public static String convertDateToShortString(Date date)
   {
      return dateFormatter.format(date);
   }

   /**
    * Converts a string in the short date pattern style "25-03-2016"
    * to a corresponding Date object.
    *
    * Any leading or trailing spaces are first removed from the date string.
    * The String parameter that represent a date as a string must be in the
    * format dd-mm-yyy (e.g. 25-03-2016 or 25-3-2016) where dd represents
    * one or two digits representing the day in the month, similarly for
    * mm representing the month in the year and yyyy represents the four
    * digits for the year.
    *
    * A RuntimeException is thrown if the date string is not recognised as
    * a valid date. Such exceptions do not need to be caught or thrown as 
    * they are unchecked exceptions, but can be caught if necessary.
    *
    * @param     dateString  a Date object
    *
    * @return    the Date object
    */
   public static Date convertStringToDate(String dateString)
   {
      dateString = dateString.trim();

      ParsePosition posn = new ParsePosition(0);  // initialise posn to the beginning of the string to be parsed

      Date date = dateFormatter.parse(dateString, posn);
      int endIndex = posn.getIndex(); // posn after parsing

      String message = "Date string <" + dateString + "> not recognised";
      if( date==null )
      {
         // parsing failed because string not recognised
         message += ".";
         throw new RuntimeException(message);
      }
      else if( endIndex!=dateString.length() )
      {
         // parsing failed because parsing did not "consume" all the characters
         // in the string, indicating the complete string was not recognised
         message += " because it contains extra characters after a date.";
         throw new RuntimeException(message);
      }
      else
         return date;
   }

   /**
    * Calculates the number of days between two given dates, startDate and endDate.
    *
    * If startDate is after endDate then the number of days returned will be negative.
    *
    * @param     startDate   a Date object
    * @param     endDate     a Date object
    *
    * @return    an int, number of days between the dates
    */
   public static int daysBetween(Date startDate, Date endDate)
   {
      // first check that startDate is not after endDate
      boolean outOfOrder;
      Date temp;

      if( startDate.compareTo(endDate)<=0 )
      {
         outOfOrder = false;
      }
      else
      {
         outOfOrder = true;
         temp = startDate;
         startDate = endDate;
         endDate = temp;
      }

      int daysBetween = 0;

      Calendar calendar = new GregorianCalendar();
      calendar.setTime(startDate);  // initialised at start date

      Calendar calendarEndDate = new GregorianCalendar();
      calendarEndDate.setTime(endDate);

      // First advance calendar a year at a time without advancing past the end date.
      // This is much quicker, for dates that are years apart, than simply relying  on the
      // later "day at a time" loop. Advancing "a month at a time" before the "day at a
      // time" loop would be even better but complex because of the varying month lengths.

      Calendar prevYearCalendar = (Calendar) calendar.clone();
      calendar.add(Calendar.YEAR, 1);
      // advance calendar a year at a time until end date reached
      while( !calendar.getTime().after(endDate) )
      {
         if( isLeapYear(prevYearCalendar.get(Calendar.YEAR)) &&
             prevYearCalendar.get(Calendar.MONTH)<Calendar.MARCH )
            // advancing past a leap year day
            daysBetween += 366;
         else
            daysBetween += 365;

         prevYearCalendar = (Calendar) calendar.clone();
         calendar.add(Calendar.YEAR, 1);
      }
      calendar = prevYearCalendar; // calendar always advances too far, so need to correct

      // now advance calendar a day at a time until end date reached
      while( calendar.getTime().before(endDate) )
      {
         calendar.add(Calendar.DAY_OF_MONTH, 1);
         daysBetween++;
      }

      if( outOfOrder )
         daysBetween = -daysBetween;

      return daysBetween;
   }

   /**
    * Given date, a Date object, and noOfDays, an int, the method returns
    * a Date object corresponding to noOfDays later than date.
    *
    * If noOfDays is negative, the resulting Date object will be before date.
    *
    * @param     date      a Date object
    * @param     noOfDays  an int
    *
    * @return    a Date
    */
   public static Date incrementDate(Date date, int noOfDays)
   {
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(date);
      calendar.add(Calendar.DAY_OF_MONTH, noOfDays);
      return  calendar.getTime();
   }

   /**
    * Given year, an int, the method checks to see if the year
    * is a leap year.
    *
    * @param     year,  an int
    *
    * @return    a boolean, true only if the year is a leap year.
    */
   public static boolean isLeapYear(int year)
   {
      GregorianCalendar calendar = new GregorianCalendar();
      return calendar.isLeapYear(year);
   }

   /**
    * Given dateString, a String, the method checks to see if string
    * corresponds to a valid shortDatePattern. 
    *
    * @param     dateString, a String
    *
    * @return    a boolean, true only if the dateString is a valid pattern
    */
   public static boolean isValidDateString(String dateString)
   {
      try
      {
         Date d = DateUtil.convertStringToDate(dateString);
         return true;
      }
      catch(RuntimeException ex)
      {
         return false;
      }      
   }
   
   /**
    * Given date, a Date object, the method returns
    * a Date object corresponding to the next day.
    *
    * @param     noOfDays  an int
    *
    * @return    a Date
    */
   public static Date nextDate(Date date)
   {
      return incrementDate(date, 1);
   }
}
