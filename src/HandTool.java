import java.util.Scanner;
/**
 * HandTool is a subclass of Tool, it represents
 * an HandTool such as a hammer hence relevant fields.
 * The constructor calls its SuperClass Tool and ShopItem. 
 *
 * @author(Hassan Ali)
 * @group(4)
 */
public class HandTool extends Tool 
{
   private boolean sharpenable;
   
   public HandTool(String toolName, String itemCode, double cost, float weight, int timesBorrowed, boolean onLoan, boolean sharpenable) 
   {
       super(toolName, itemCode, cost, weight, timesBorrowed, onLoan); //calls the superclass constructor Tool
       this.sharpenable = sharpenable;
   }

   /**
    * An empty HandTool constructor, called upon by the Shop
    * superclass in order to help extract data into 
    * an ElectricTool object.
    */
    public HandTool() 
    {

    }

   public void extractTokens (Scanner handScan) 
   {
       sharpenable = handScan.nextBoolean();
       super.extractTokens (handScan);
       handScan.close(); 
   }

   /**
     * Calls the printDetails() from the Tool SuperClass to print 
     * out common variables. This method also calls its own accessor
     * methods and prints them to the terminal.
     */
    
   public void printDetails() 
   {
       System.out.println("Sharpenable : " + " " + isSharpenable() + " ");
       super.printDetails();
   }
   
   /**
    * Accessor and Mutator methods, designed to be called upon.
    * Used by printDetails() method above. Mutator methods
    * are used by extractTokens () method by the scanner object.
    */  
   
   public void setSharpenable(boolean sharpenable)
   {
       this.sharpenable = sharpenable;
   }
   
   public boolean isSharpenable()
   {
       return sharpenable;
   }


}

