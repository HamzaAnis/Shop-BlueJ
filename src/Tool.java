import java.util.Scanner;
/**
 * Tool is a subclass of ShopItem on the second level of
 * the hierarchy. Tool is the SuperClass of ElectricTool
 * and HandTool.This class is used to avoid duplication
 * between ElectricTool and HandTool.
 *
 * @author(Hassan Ali)
 * @group(4)
 */
public abstract class Tool extends ShopItem
{
    /**
     * Fields of the superclass, that are to be inherited by HandTool and
     * ElectricTool child classes.
     */

   private float weight;
   private int timesBorrowed;
   private boolean onLoan;

   public Tool(String itemName, String itemCode, double cost, float weight, int timesBorrowed, boolean onLoan)
   {
       this.weight = weight;
       this.timesBorrowed = timesBorrowed;
       this.onLoan = onLoan;
   }

   /**
    * An empty Tool constructor, called upon in order
    * to help extract data into it.
    */
   public Tool() 
   {

   }
 
   /**
    * This is called upon by readData() method within Class
    * Shop and passed a scanner object.Designed to read text data,
    * relevant to Accessory data from a given file.
    */
    public void extractTokens (Scanner toolScan) 
    {
        
        timesBorrowed = toolScan.nextInt();
        onLoan = toolScan.nextBoolean();
        super.extractTokens (toolScan);
        weight = toolScan.nextFloat();
        toolScan.close(); 

    }

   /**
    * printDetails() to be called from HandTool and
    * ElectricTool subclasses. This method calls its own accessor
    * methods and prints them to the terminal.
    */
   public void printDetails()
   {
       super.printDetails();
       System.out.println("Weight : " + " " + getWeight() + "\n" + "TimesBorrowed : "
       + " " + getTimesBorrowed() + "\n" + "onLoan : " + " " + isOnLoan());
   }

   /**
    * Accessor and Mutator methods, designed to be
    * called upon by HandTool and ElectricTool subclass
    * methods.
    */
   public float getWeight() 
   {
       return weight;
   }

   public void setWeight(float weight) 
   {
       this.weight = weight;
   }

   public int getTimesBorrowed()
   {
       return timesBorrowed;
   }

   public void setTimesBorrowed(int timesBorrowed)
   {
       this.timesBorrowed = timesBorrowed;
   }

   public boolean isOnLoan()
   {
       return onLoan;
   }

   public void setOnLoan(boolean onLoan) 
   {
       this.onLoan = onLoan;
   }
}