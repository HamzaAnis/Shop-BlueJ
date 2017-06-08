import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * 
 * Enables customers to hire shop items.
 * 
 * generateReservationNo()
 * Called upon by makeItemReservation() method inside Shop 
 * Class. Random class used to obtain int variable, stored
 * as reservation0 which is converted to a String and stored
 * as reservation1. The lenght of this reservation is stored
 * as length of Res. If lenght  is greater than six do nothing.
 * However, if lenght is below and matches any in the else if statements
 * then add padding to it and combine with the previous
 *
 * printItemReservations() 
 * Outputs value of all fields within this class 
 * via. accessor methods
 * 
 */
public class ShopItemReservation 
{
	private String reservationNo;
	private String itemID;
	private String customerID;
	private Date startDate ;
	private int noOfDays;
	
	public ShopItemReservation(String itemID, String customerID, String startDate, int noOfDays)
	{
		this.itemID = itemID;
		this.customerID = customerID;
		this.startDate = DateUtil.convertStringToDate(startDate);
		this.noOfDays = noOfDays;
	}
	
	/**
	 * No parameter constructor
	 */
	public ShopItemReservation()
	{
	   
	}
	public void readReservationData(String filePath)
  {

  	if (filePath == null) 
      {
          return;
      } 
      else 
      {
      	File inFile = new File(filePath);
      	try 
          {
              int counter=0;
      	  Scanner scanner = new Scanner(inFile);
          while (scanner.hasNextLine()) 
          {counter=1;
               String lineOfText = scanner.nextLine();
               Scanner sc = new Scanner(lineOfText.trim());
               
              if(!(lineOfText.startsWith("//")) && !lineOfText.isEmpty() && lineOfText!="" )
              {
                  sc.useDelimiter(",");
                  ShopItemReservation s = new ShopItemReservation();
                  s.extractTokens(sc);
              
                  s.printItemReservations();      
                   
              }
              else 
              {
            System.out.println("!!NO customer record found!!");
              }
          }
          if(counter==0){
                          System.out.println("!!NO customer record found!!");

          }
          
      } catch (FileNotFoundException e) 
      {
          e.printStackTrace();
      } 
      }       
  }
  
    /**
     * Outputs value of all fields within this class
     * via. accessor methods
     */
    public void printItemReservations() 
    {
       System.out.println("-----" + "\n" + "Reservation No: " + " " + getReservationNo() + "\n " + " \n" + "Item ID : " + " " + getItemID()
        + "\n" + "customer ID : " + " " + getCustomerID()+ "\n" + "No of Days : " + " " + getNoOfDays()+"\n"+"start Date : " + " " + getStartDate());
    } 
	

	public String generateReservationNo()
	{
		int reservation0 = (int) Math.random();
		String reservation1 = Integer.toString(reservation0); //convert into to String
		int lengthOfRes = reservation1.length(); 
		if (lengthOfRes > 6){}
		//padding
		else if (lengthOfRes == 0){ //if match add padding
			reservation1 = "000000";
		}
		else if (lengthOfRes == 1){
			reservation1 = "00000"+reservation1; 
		}
		else if (lengthOfRes == 2){
			reservation1 = "0000"+reservation1;
		}
		else if (lengthOfRes == 3){
			reservation1 = "000"+reservation1;
		}
		else if (lengthOfRes == 4){
			reservation1 = "00"+reservation1;
		}
		else if (lengthOfRes == 5){
			reservation1 = "0"+reservation1;
		}
		else if (lengthOfRes == 6){
			reservation1 = reservation1;
		}
		reservationNo = reservation1; 
                return reservationNo;
	}
	
	public void extractTokens (Scanner toolScan) 
    {
		reservationNo = toolScan.next();
        itemID = toolScan.next();
        customerID = toolScan.next();
       noOfDays = toolScan.nextInt();
   
        startDate = DateUtil.convertStringToDate(toolScan.next());
       }
    
    public void printDetails(HashMap<String, ShopItemReservation> itemReservationMap) 
    {
		if(itemReservationMap.isEmpty())
		{
            return;   
        }
        Set set = itemReservationMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) 
        {
           Map.Entry mentry = (Map.Entry)iterator.next();
           System.out.println("Reservation No: "+ mentry.getKey());
           ShopItemReservation c = (ShopItemReservation) mentry.getValue();
           System.out.println("\n " + " \n" + "Item ID : " + " " + c.getItemID() + "\n" + "customer ID : " + " " + c.getCustomerID()+ "\n" + "No of Days : " + " " + c.getNoOfDays());
        }
    } 
	
    
    /**
	 *  Accessor and Mutator methods for all fields
	 *  within this Class
	 */
	public String getReservationNo() 
	{
		return reservationNo;
	}

	public void setReservationNo(String reservationNo) 
	{
		this.reservationNo = reservationNo;
	}
	
	public String getItemID() 
	{
		return itemID;
	}
	
	public void setItemID(String itemID) 
	{
		this.itemID = itemID;
	}
	
	public String getCustomerID() 
	{
		return customerID;
	}

    public int getNoOfDays() 
	{
		return noOfDays;
	}
	
	public void setNoOfDays(int noOfDays) 
	{
		this.noOfDays = noOfDays;
	}
	
	public void setCustomerID(String customerID) 
	{
		this.customerID = customerID;
	}

	public Date getStartDate() 
	{
		return startDate;
	}
	
	public void setStartDate(Date startDate) 
	{
		this.startDate = startDate;
	}
}
