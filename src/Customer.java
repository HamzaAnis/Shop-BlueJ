import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

/**
 * Represents customer
 * generateRandomNumber()
 * storeCustomer() method from Shop Class, calls generateCustomerID()
 * from this Class, which then calls this method and is passed a value
 * of "6" as an int. If number == 6, min is given the value of 9999
 * and max is given value off 1000.
 * 
 * generateCustomerID()
 * Designed to be called by Shop Class storeCustomer() method, this
 * method is passed a prefx of "AB-" as a String and the number "1" as 
 * an int. The retuned value from generateRandomNumber(1) is then 
 * combined with the prefix number and stored as a CustomerID.
 */
public class Customer 
{
    private String customerID;
    private String surName;
    private String firstName;
    private String otherInitials;
    private String title;
    
    public Customer(String firstName, String surName, String otherInitials, String title)
    {
        this.customerID = "unknown";
        this.firstName = firstName;
        this.surName = surName;
        this.otherInitials = otherInitials;
        this.title = title;
    }

    /**
     * No parameter constructor called upon by 
     * readCustomerData() method in the Shop Class
     */
    public Customer() 
    {
    
    }

    /**
     * Displays the values of all fields within
     * this class in an understandable format
     */
    public void printDetails()
    {
        System.out.println(  "customerID:" + customerID + "\n firstName:" + firstName + "\n surName:" + surName + "\n otherInitials:" + otherInitials + "\n title:" + title );
           System.out.println("-----------------------------------------------------------------------");

    }
    
    /**
     * storeCustomer() method from Shop Class, calls generateCustomerID()
     * from this Class, which then calls this method and is passed a value
     * of "6" as an int. If number == 6, min is given the value of 9999
     * and max is given value off 1000.
     */
    public int generateRandomNumber(int number)
     {
        int minimum = 0;
        int maximum = 0; 

        if (number == 6)
        {
            minimum = 9999;
            maximum = 1000;
        }
        else
        {
            
        }
        
       int  i = 0;
       Random random = new Random();
       i = random.nextInt(minimum) + maximum;
       return i;
    }
    
    /**
     * Designed to be called by Shop Class storeCustomer() method, this
     * method is passed a prefx of "AB-" as a String and the number "1" as 
     * an int. The retuned value from generateRandomNumber(1) is then 
     * combined with the prefix number and stored as a CustomerID.
     */
    public void generateCustomerID(String prefix, int numb)
    {
        int number = generateRandomNumber(numb); //passes numb into generateRandomNumber, the value returned is then assigned to number
        String id = prefix + number; //combines prefix with number and asigned to String id
        setCustomerID(id); //Passes id into setCustomerID mutator method for storage
    }
    
    /**
     * Accessor and Mutator Methods for all fields within
     * Customer Class.
     */
    public String getCustomerID() 
    {
        return customerID;
    }

    public void setCustomerID(String customerID)
    {
        this.customerID = customerID;
    }

    public String getSurName() 
    {
        return surName;
    }

    public void setSurName(String surName)
    {
        this.surName = surName;
    }

    public String getFirstName() 
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

        public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getOtherInitials()
    {
        return otherInitials;
    }

    public void setOtherInitials(String otherInitials)
    {
        this.otherInitials = otherInitials;
    }
    
    public void extractTokens(Scanner customerScan)
    {
        setCustomerID(customerScan.next());
        setFirstName(customerScan.next());
        setSurName(customerScan.next());
        setOtherInitials(customerScan.next());
        setTitle(customerScan.next());
    }
    
    public void readData()
    {

        
    }
}

