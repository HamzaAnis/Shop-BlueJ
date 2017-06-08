import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Shop
{
   HashMap<String, ShopItem> itemsMap;
   HashMap<String, Customer> customerMap;
   HashMap<String, ShopItemReservation> itemReservationMap; //stores reservations
   Diary diary;
   String dumpCustomerDataFileName;
   String dumpItemReservationDataFileName;

   /**
    * Constructor, creates two ArrayList of type toolsList
    * and of type accessoryList
    */
   public Shop(String name)
   {
      itemsMap = new HashMap<String, ShopItem>();
      customerMap = new HashMap<String, Customer>();
      itemReservationMap = new HashMap<String, ShopItemReservation>();
      diary = new Diary();

   
   }

   /**
    * This method is designed to add Tool objects into the arrayList. As this
    * is polymorphic, HandTool and ElectricTool objects can also be added.
    */
   public void storeShopItem(ShopItem item)
   {
      itemsMap.put(item.getItemCode(), item);
   }
    
    /**
     * Passed a ShopItemReservation object, stored in itemsReservationMap
     * alongside its unique reservation number. 
     */
   public void storeItemReservation(ShopItemReservation item)
   {
	   itemReservationMap.put(item.getReservationNo(), item);
	   diary.addItemReservation(item);
   }
   
   
   /**
    * Designed to make an Item reservation, creates shopItemReservation 
    * object, passed four parameters. Number returned from
    * generateReservationNo() is stored locally in reservationNo as a 
    * String.
    * 
    * Validation check:If itemsReservationMap key matches the reservationNo
    * or itemsID is not matched in itemsMap then output error. Else add the
    * reservationNo and ShopItemReservation into itemsReservationMap as a pair. 
    * 
    */
   public boolean makeItemReservation(String customerID, String itemID, String startDate, int noOfDays)
   {
	   ShopItemReservation ir = new ShopItemReservation(itemID, customerID, startDate, noOfDays);
	   ir.generateReservationNo(); //calls method from ShopItemReservation
	   String reservationNo = ir.getReservationNo();
	   if(itemReservationMap.containsKey(reservationNo) || !itemsMap.containsKey((itemID))) 
	   {
		   System.out.println("Error, reservation invalid");
		   return false;
	   }
	   else 
	   {
		   itemReservationMap.put(reservationNo, ir);
		   diary.addItemReservation(ir); //adds the reservation to the diary too
		   return true;
	   }
   }
   
   public void printAllReservations()
   {
    if(itemReservationMap.isEmpty())
    {
        System.out.println("HELLO");
        return;   
    }
            System.out.println("HELLO1");

     Set set = itemReservationMap.entrySet();
     Iterator iterator = set.iterator();
    while(iterator.hasNext()) 
    {
       Map.Entry mentry = (Map.Entry)iterator.next();
       System.out.println(" Reservation Number: "+ mentry.getKey());
       ShopItemReservation c = (ShopItemReservation) mentry.getValue();
       System.out.println("Item ID: "+c.getItemID()+" Customer Id: "+c.getCustomerID()+" Start Days: "+c.getStartDate()+" No Of Days: "+c.getNoOfDays());
    }
   }
   
   /**
    * Method to delete the reservation
    */
   public void deleteItemReservation(String reservationNo)
   {
	 itemReservationMap.remove(reservationNo);
   }

    /**
     * Prints out the details of all tools and accessories which are stored
     * inside the arrayList toolList to the terminal.
     */
    public void printItemDetails()
    {

    	if(customerMap.isEmpty())
    	{
            return;   
        }
        Set set = itemsMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext())
        {
          Map.Entry mentry = (Map.Entry)iterator.next();
          System.out.println("Item Code: "+ mentry.getKey());
          ShopItem c = (ShopItem) mentry.getValue();
          System.out.println("Item Name: "+c.getItemName()+" Cost: "+c.getCost());
        }
    }
    
    public void printAllCustomers() 
    {
        if(customerMap.isEmpty())
        {
         return;   
        }
        Set set = customerMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext())
        {
           Map.Entry mentry = (Map.Entry)iterator.next();
           System.out.println("Cutomer Code: "+ mentry.getKey());
           Customer c = (Customer) mentry.getValue();
           System.out.println("First Name: "+c.getFirstName()+" Sur Name: "+c.getSurName()+" Other Initials: "+c.getOtherInitials()+" Title: "+c.getTitle());
        }
    }
    
    public void printCustomer(String id)
    {
        if(customerMap.isEmpty())
     {
         return;   
     }
           Customer c = customerMap.get(id);
           System.out.println("ID: "+c.getCustomerID()+" First Name: "+c.getFirstName()+" Sur Name: "+c.getSurName()+" Other Initials: "+c.getOtherInitials()+" Title: "+c.getTitle());
    }
    
    public void printItem(String id)
    {
        if(itemsMap.isEmpty())
     {
         return;   
     }
           ShopItem c = itemsMap.get(id);
           System.out.println("ID: "+c.getItemCode()+" Name: "+c.getItemName()+" Cost: "+c.getCost());
    }
    
//     public void printDiaryEntries(String startDate, String endDate)
//     {
//         
//         diary.printEntries(startDate, endDate);
//     }
 
    public void readItemData() 
    {
        FileDialog fd = new FileDialog(new JFrame());
        fd.setVisible(true); //shows the dialog box
        String filename = fd.getFile(); //Get the directory of the file dialog and gets file
        //fd.getDirectory();
        if (filename == null) 
        {
            return;
        } 
        else 
        {
            File inFile;
            Scanner scanner;
            inFile = new File(filename);

            try 
            {
                scanner = new Scanner(inFile);
                
                while (scanner.hasNextLine())
                {
                    String lineOfText = scanner.nextLine();
                    Scanner sc = new Scanner(lineOfText.trim());
                    sc.useDelimiter(",");
                    if (!(lineOfText.startsWith("//")) && !lineOfText.isEmpty() && !lineOfText.equalsIgnoreCase("[HandTool data]")
                            && !lineOfText.equalsIgnoreCase("[ElectricTool data]") && !lineOfText.equalsIgnoreCase("[electricTool data]")
                            && !lineOfText.equalsIgnoreCase("[Perishable data]") && !lineOfText.equalsIgnoreCase("[Workwear data]"))
                    {
                        ShopItem electricTool = new ElectricTool();
                        electricTool.extractTokens (sc);
                        electricTool.printDetails();
                        storeShopItem(electricTool);
                    } else if (lineOfText.equalsIgnoreCase("[HandTool data]")) {
                        ShopItem handTool = new HandTool();
                        handTool.extractTokens (sc);
                        handTool.printDetails();
                        storeShopItem(handTool);
                    } else if (lineOfText.equalsIgnoreCase("[Perishable data]")) {
                        ShopItem p_accesory = new Perishable();
                        p_accesory.extractTokens (sc);
                        p_accesory.printDetails();
                        storeShopItem(p_accesory);
                    } else if (lineOfText.equalsIgnoreCase("[Workwear data]")) {
                         ShopItem w_accessory = new WorkWear();
                         w_accessory.extractTokens (sc);
                         w_accessory.printDetails();
                         storeShopItem(w_accessory);
                    } 
                    //do not use a switch case
                 
                }
                scanner.close(); 
            } 
            catch (FileNotFoundException e)
            {

            }
        }
    }
    
    public void readItemData(String filePath)
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
        		Scanner scanner = new Scanner(inFile);
                
 
                while (scanner.hasNextLine())
                {
                    String lineOfText = scanner.nextLine();
                    Scanner sc = new Scanner(lineOfText.trim());
                    sc.useDelimiter(",");
                    if (!(lineOfText.startsWith("//")) && !lineOfText.isEmpty() && !lineOfText.equalsIgnoreCase("[HandTool data]")
                            && !lineOfText.equalsIgnoreCase("[ElectricTool data]") && !lineOfText.equalsIgnoreCase("[electricTool data]")
                            && !lineOfText.equalsIgnoreCase("[Perishable data]") && !lineOfText.equalsIgnoreCase("[Workwear data]"))
                    {
                        ShopItem electricTool = new ElectricTool();
                        electricTool.extractTokens (sc);
                        electricTool.printDetails();
                        storeShopItem(electricTool);
                    } else if (lineOfText.equalsIgnoreCase("[HandTool data]")) {
                        ShopItem handTool = new HandTool();
                        handTool.extractTokens (sc);
                        handTool.printDetails();
                        storeShopItem(handTool);
                    } else if (lineOfText.equalsIgnoreCase("[Perishable data]")) {
                        ShopItem p_accesory = new Perishable();
                        p_accesory.extractTokens (sc);
                        p_accesory.printDetails();
                        storeShopItem(p_accesory);
                    } else if (lineOfText.equalsIgnoreCase("[Workwear data]")) {
                         ShopItem w_accessory = new WorkWear();
                         w_accessory.extractTokens (sc);
                         w_accessory.printDetails();
                         storeShopItem(w_accessory);
                    } 
                    //do not use a switch case
                 
                }
                scanner.close(); 
            } 
            catch (FileNotFoundException e)
            {

            }
        }

       // remainder of code from the original readItemData() method.
    }

  public void readCustomerData() {

        FileDialog d = new FileDialog(new JFrame());
        d.setVisible(true); //shows the dialog box
        String filename = d.getFile(); //Get the directory of the file dialog and gets file
        
        //File name and File directory..
        System.out.println("File Name : " + filename);
        System.out.println("Directory : " + d.getDirectory() + "\n\n");
        if (filename == null)
        {
            return;
        } 
        
        else 
        {
            File file = new File(d.getDirectory()+filename);
        try 
        {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) 
            {
                 String lineOfText = scanner.nextLine();
                 Scanner sc = new Scanner(lineOfText.trim());
                if(!(lineOfText.startsWith("//")) && !lineOfText.isEmpty())
                {
                    sc.useDelimiter(",");
                    Customer customer = new Customer();
                    customer.extractTokens(sc);
                    storeCustomer(customer);    
                }
                else
                {
              
                }
            }
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        }       
    }
    public void loadData(String filePath)
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
                  Customer customer = new Customer();
                  customer.extractTokens(sc);
                  storeCustomer(customer);
                   
              }
              else 
              {
          //  System.out.println("!!NO customer record found!!");
              }
          }
          if(counter==0){
                    //      System.out.println("!!NO customer record found!!");

          }
          
      } catch (FileNotFoundException e) 
      {
          e.printStackTrace();
      } 
      }       
  }
    
  public void readCustomerData(String filePath)
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
                  Customer customer = new Customer();
                  customer.extractTokens(sc);
                  storeCustomer(customer);
                  customer.printDetails();      
                   
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
    
    public void writeCustomerData() 
    {
        System.out.println("Data stored in text file:");
        try {
            PrintWriter w = new PrintWriter("C:\\new_customer_data.txt");
            Set set = customerMap.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext())
            {
               Map.Entry mentry = (Map.Entry)iterator.next();
               System.out.println("Cutomer Code: "+ mentry.getKey());
               Customer c = (Customer) mentry.getValue();
               w.println( "Code: "+c.getCustomerID()+" , "+ c.getFirstName()+" , "+ c.getSurName()+" , "+ c.getOtherInitials()+" , "+ c.getTitle());
               c.printDetails();            
            }
            
           
            w.close();
    } catch (IOException e) 
    {
    }
    }
    
    
    /**
     * Designed to write data stored in customerList to a new text file.
     * Passed the filepath of 
     */
    public void writeCustomerData(String filePath) 
    {
        System.out.println("Customer:Data stored in text file:");
        try {
            PrintWriter w = new PrintWriter(filePath);
            Set set = customerMap.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) 
            {
               Map.Entry mentry = (Map.Entry)iterator.next();
               System.out.println("Cutomer Code: "+ mentry.getKey());
               Customer c = (Customer) mentry.getValue();
               w.println( "Code: "+c.getCustomerID()+" , "+ c.getFirstName()+" , "+ c.getSurName()+" , "+ c.getOtherInitials()+" , "+ c.getTitle());
               c.printDetails();            
            }
            
           
            w.close();
        } 
        catch (IOException e) 
        {
        }
    }
    
    public void writeItemReservationData() 
    {
        System.out.println("Data stored in text file:");
        try {
            PrintWriter w = new PrintWriter("C:\\new_item_reservation_data.txt");
            Set set = itemReservationMap.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) 
            {
               Map.Entry mentry = (Map.Entry)iterator.next();
               System.out.println("Reservation ID: "+ mentry.getKey());
               ShopItemReservation c = (ShopItemReservation) mentry.getValue();
               w.println( c.getItemID()+" , "+ c.getCustomerID()+" , "+ c.getStartDate()+" , "+ c.getNoOfDays());     
               }
            
           
            w.close();
      }
      catch (IOException e)
      {
      }
    }
    
    public void writeItemReservationData(String filePath)
    {
        System.out.println("Data stored in text file:");
        try
        {
         
            PrintWriter w = new PrintWriter(filePath);
            Set set = itemReservationMap.entrySet();
            Iterator iterator = set.iterator();
            while(iterator.hasNext()) 
            {
               Map.Entry mentry = (Map.Entry)iterator.next();
               System.out.println("Reservation ID: "+ mentry.getKey());
               ShopItemReservation c = (ShopItemReservation) mentry.getValue();
               w.println( c.getItemID()+" , "+ c.getCustomerID()+" , "+ c.getStartDate()+" , "+ c.getNoOfDays());     
            }
            
           
            w.close();
    } 
    catch (IOException e) 
    {
    }
    }
    
    public void readItemReservationData()
    {

        FileDialog fd = new FileDialog(new JFrame());
        fd.setVisible(true); //shows the dialog box
        String filename = fd.getFile(); //Get the directory of the file dialog and gets file
        
        //File name and File directory..
        System.out.println("File Name : " + filename);
        System.out.println("Directory : " + fd.getDirectory() + "\n\n");
        if (filename == null) 
        {
            return;
        } else
        {
            File file = new File(fd.getDirectory()+filename);
        try 
        {
           
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) 
            {
                 String lineOfText = scanner.nextLine();
                 Scanner sc = new Scanner(lineOfText.trim());
                if(!(lineOfText.startsWith("//")) && !lineOfText.isEmpty()){
                    sc.useDelimiter(",");
                    ShopItemReservation sir = new ShopItemReservation();
                                sir.extractTokens(sc);
                                storeItemReservation(sir);
                              
                }
                else 
                {
              
                }
            }
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        }       
    }
    
    public void readItemReservationData(String filePath)
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
          		Scanner scanner = new Scanner(inFile);
            while (scanner.hasNextLine())
            {
                 String lineOfText = scanner.nextLine();
                 Scanner sc = new Scanner(lineOfText.trim());
                if(!(lineOfText.startsWith("//")) && !lineOfText.isEmpty()){
                    sc.useDelimiter(",");
                    ShopItemReservation sir = new ShopItemReservation();
                    sir.extractTokens(sc);
                    storeItemReservation(sir);
                              
                }
                else 
                {
              
                }
            }
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        }       
    }

	/**
	 * Passed a customer object, if the customers name is unknown,
	 * this calls the generateCustomerID in the Customer class, with 
	 * a set prefix and a number. A random number is generated and combined
	 * with this prefix and stored as CustomerID. This is placed into 
	 * the customerMap.
	 */
	public void storeCustomer(Customer customer) 
	{
		if(customer.getCustomerID().equals("unknown")) //if true execute the below
		{
           customer.generateCustomerID("AB-", 1); //fixed prefix + a set number - processed within Customer class
        }
        customerMap.put(customer.getCustomerID(), customer); //obtains CustomerID and customer object and placed into customerMap
	}
	
	/**
	 * Writes current customer data and Item reservation to 
	 * two seperate text files. In itself doesn't actually
	 * close the shop down.
	 */
	public void closeDownShop()
	{
		writeCustomerData(dumpCustomerDataFileName);
		writeItemReservationData(dumpItemReservationDataFileName);
	}
	
	public void reloadShop(String name)
	{
		readCustomerData(dumpCustomerDataFileName);
		readItemReservationData(dumpItemReservationDataFileName);
	}
	
	public int getNumberOfItems()
	{
		return itemsMap.size();
	}
	
	public int getNumberOfCustomers()
	{
		return customerMap.size();
	}
	
	public int getNumberOfItemReservations()
	{
		return itemReservationMap.size();
	}
}
    
