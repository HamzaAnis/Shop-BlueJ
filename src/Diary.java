import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * A class Diary that represents a "diary" of shop item reservations.
 * 
 * The diary is structured as a Map of entries in which each entry corresponds 
 * to a specific day.  As the map is not accessed in a sequential fashion, it
 * doesn't matter whether the actual map class is a TreeMap or a HashMap.
 *
 * 
 * 
 */
public class Diary
{
   private Map<Date, DayInDiary> dayInDiaryMap;

   /**
    * Constructor for objects of class Diary
    */
   public Diary()
   {
      // create diary as a map
      dayInDiaryMap = new HashMap<Date, DayInDiary>();
   }
   
   /**
    * Method for adding a reservation to the diary. 
    *
    * @parameter     shopItemReservation, of type ShopItemReservation 
    */
   public void addItemReservation(ShopItemReservation shopItemReservation)
   {
      Date date = shopItemReservation.getStartDate();
      for(int day=1; day<=shopItemReservation.getNoOfDays(); day++)
      {
         if( !dayInDiaryMap.containsKey(date) )
         {
            // add new day to diary if no previous entries for this day
            dayInDiaryMap.put(date, new DayInDiary(date));
         }
         
         dayInDiaryMap.get(date).addEntry(shopItemReservation, day);
         date = DateUtil.nextDate(date);
      }
   }
  
   /**
    * Method for displaying the reservations between specified dates. 
    *
    * @parameter     startDate, of type Date
    * @parameter     endDate, of type Date
    */
   public void printEntries(Date startDate, Date endDate)
   {
      if( DateUtil.daysBetween(startDate, endDate)<0 )  
      {
         // startDate after endDate 
         System.out.println("*** Error in method displayEntries(): The specified end date is before the start date ***");
      }
      else
      {
         System.out.println("\n\nDiary: Reservations for period " + DateUtil.convertDateToShortString(startDate)
                            + " to " + DateUtil.convertDateToShortString(endDate) + " inclusive");
         System.out.println("=================================================================");
         for(Date date=startDate; date.compareTo(endDate)<=0; date = DateUtil.nextDate(date))
         {
            String longDate = DateUtil.convertDateToLongString(date); 
            System.out.print(longDate + ":");
            if( dayInDiaryMap.containsKey(date) )
            {
               DayInDiary dayInDiary = dayInDiaryMap.get(date);
               dayInDiary.printEntries();
            }
            else
               System.out.println(" No reservations\n");
         }
      }
   }

   /**
    * Method for deleting a reservation from the diary. 
    *
    * @parameter     shopItemReservation, of type ShopItemReservation 
    */   
   public void deleteItemReservation(ShopItemReservation shopItemReservation)
   {
      Date date = shopItemReservation.getStartDate();
      for(int day=1; day<=shopItemReservation.getNoOfDays(); day++)
      {
         DayInDiary dayInDiary = dayInDiaryMap.get(date);
         dayInDiary.deleteEntry(shopItemReservation);

         if( dayInDiary.getDaysEntries().size()==0 )
            dayInDiaryMap.remove(date);

         date = DateUtil.nextDate(date);
      }
   }   
      
   /**
    * Accessor method for returning all reservations that have entries 
    * in the diary for a particular date. 
    *
    * @parameter     date, of type Date 
    * @return        an array of reservations, or null if no entry for that date
    */   
   public ShopItemReservation[] getItemReservations(Date date)
   {
      DayInDiary dayinDiary = dayInDiaryMap.get(date);
      if( dayinDiary==null )
         return null;
      else
         return dayinDiary.getReservations();
   }
   
   // inner class, only needed in the Diary class
   private class DayInDiary
   {
      // reservations for the day
      private ArrayList<Entry> daysEntries;
      private Date date;
   
      /*
       * Constructor for objects of class DayInDiary
       */
      private DayInDiary(Date date)
      {
         this.date = date;
         daysEntries = new ArrayList<Entry>();
      }
 
      private ArrayList<Entry> getDaysEntries()
      {
         return daysEntries;   
      }
      
      private ShopItemReservation[] getReservations()
      {
         int noOfEntries = daysEntries.size();
         ShopItemReservation[] shopItemReservations = new ShopItemReservation[noOfEntries];
         for(int i=0; i<noOfEntries; i++)
            shopItemReservations[i] = daysEntries.get(i).getReservation();
         return shopItemReservations;
      }
    
      private void addEntry(ShopItemReservation shopItemReservation, int dayNo)
      {
         daysEntries.add(new Entry(shopItemReservation, dayNo));
      }
   
      private void deleteEntry(ShopItemReservation shopItemReservation)
      {
         // find the entry for this reservation and delete it
         Entry toBeDeletedEntry = null;
         for(Entry entry : daysEntries)
         {
            if( entry.getReservation()==shopItemReservation )  // in this situation, this is better than using "equals()"
            {
               toBeDeletedEntry = entry;
               break;
            }
         }
         daysEntries.remove(toBeDeletedEntry);
      }
   
      private void printEntries()
      {
         int size = daysEntries.size();
         if( size>0 )
         {
            System.out.println(" " + size + " reservation(s)");
            for( Entry entry: daysEntries )
            {
               ShopItemReservation shopItemReservation = entry.getReservation();
               int reservationDay = DateUtil.daysBetween(shopItemReservation.getStartDate(), date) + 1;;
               System.out.println("    " + shopItemReservation + ", day " + reservationDay + " of " + shopItemReservation.getNoOfDays());
            }
         }
         else
         {
            System.out.println("*** Unexpected error in displayEntries() ***");
            System.out.println("*** No entries for this date so it should not be in the diary  ***");
         }
         System.out.println();
      }
   
      // an inner class of the DaysDiary class
      private class Entry
      {
         private ShopItemReservation shopItemReservation;
         private int reservationDay;  // e.g. second day is day 2 of 4 for a reservation spanning 4 days
   
         private Entry(ShopItemReservation shopItemReservation,  int reservationDay)
         {
            this.shopItemReservation = shopItemReservation;
            this.reservationDay = reservationDay;
         }
   
         private ShopItemReservation getReservation()
         {
            return shopItemReservation;  
         }         
      }
   }   
}
