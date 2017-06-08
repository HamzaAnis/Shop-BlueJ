import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import java.awt.FileDialog;
import java.io.*;
/**
 * ElectricTool is a subclass of Tool, it represents
 * an ElectricTool such as a Drill hence relevant fields.
 * The constructor calls its SuperClass Tool and ShopItem. 
 *
 * @author (Hassan Ali)
 * @group (4)
 */

public class ElectricTool extends Tool 
{
   private boolean rechargeable;
   private String power;

   public ElectricTool(String toolName, String itemCode, double cost, float weight, int timesBorrowed, boolean onLoan, boolean rechargeable, String power)
   {
      super(toolName, itemCode, cost, weight, timesBorrowed, onLoan);
      this.rechargeable = rechargeable; 
      this.power = power;
   }
   
   /**
    * An empty ElectricTool constructor, called upon in order
    * to help extract data into an ElectricTool object.
    */
   public ElectricTool()
   {
       
   }

   /**
    * This is called upon by readData() method within SuperClass 
    * Shop and passed a scanner object.Designed to read text data, 
    * relevant to ElectricTool data from a given file.
    */
   
   public void extractTokens (Scanner elecScan) 
   { 
       rechargeable = elecScan.nextBoolean();
       power = elecScan.next();
       super.extractTokens (elecScan);
       elecScan.close(); 
   }

   /**
     * Calls the printDetails() from the Tool SuperClass to print 
     * out common variables. This method also calls its own accessor
     * methods and prints them to the terminal.
     */
   public void printDetails() 
   {
      super.printDetails();
      System.out.println("Rechargeable : " + " "
      + isRechargeable() + "\n" + "Power : " + " " +getPower() + " ");
   }

   /**
    * Accessor and Mutator methods, designed to be called upon.
    * Used by printDetails() method above. Mutator methods
    * are used by extractTokens method by the scanner object.
    */  
   public String getPower() 
   {
       return power;
   }

   public void setPower(String power) 
   {
       this.power = power;
   }
   
      public boolean isRechargeable() 
   {
      return rechargeable;
   }

   public void setRechargeable(boolean recharegeable) 
   {
       this.rechargeable = recharegeable;
   }
}
