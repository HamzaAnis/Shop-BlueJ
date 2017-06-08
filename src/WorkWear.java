import java.util.Scanner;
/**
 * WorkWear is a subclass of Accessory, it represents
 * an WorkWear items such as an overcoat hence relevant fields.
 * The constructor calls its SuperClass Accessory and ShopItem. 
 *
 * @author (Hassan Ali)
 * @group (4)
 */
public class WorkWear extends Accessory 
{
   private String manufacturingStandard;
   private String colour;
   private String size;

   /**
    * An empty Workwear constructor, called upon in order
    * to help extract data into an WorkWear object.
    */
   public WorkWear()
   {

   }

   /**
    * This is called upon by readData() method within Class
    * Shop and passed a scanner object.Designed to read text data,
    * relevant to WorkWear data from a given file.
    */
   public void extractTokens (Scanner workScan)
   {
       manufacturingStandard = workScan.next();
       colour = workScan.next();
       size = workScan.next();
       super.extractTokens (workScan);
       workScan.close(); 
   }

   /**
    * Calls the printDetails() from the Accessory SuperClass to print 
    * out common variables. This method also calls its own accessor
    * methods and prints them to the terminal.
    */
   public void printDetails() 
   {
      super.printDetails();
      System.out.println("Manufacturing : " + " " + getManufacturingStandard() + "\nColour :  " + getColor() + "\nSize : " + getSize());
   }

   /**
    * Accessor and Mutator methods, designed to be called upon.
    * Used by printDetails() method above. Mutator methods
    * are used by extractTokens () method by the scanner object.
    */ 
   public String getManufacturingStandard()
   {
      return manufacturingStandard;
   }

   public void setManufacturingStandard(String manufacturingStandard) 
   {
      this.manufacturingStandard = manufacturingStandard;
   }

   public String getSize() 
   {
      return size;
   }

   public void setSize(String size)
   {
      this.size = size;
   }
   
      public String getColor()
   {
      return colour;
   }

   public void setColor(String colour)
   {
      this.colour = colour;
   }
}
