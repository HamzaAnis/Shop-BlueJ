import java.util.Scanner;
/**
 * Accessory is a subclass of ShopItem on the second level of
 * the hierarchy. Accessory is the SuperClass of Perishable
 * and Workwear. This class is used to avoid duplication
 * between Perishable and Workwear.
 *
 * @author(Hassan Ali)
 * @group(4)
 */
public abstract class Accessory extends ShopItem
{
    /**
     * Fields of the superclass, that are to be inherited by Perishable and
     * WorkWear child classes.
     */
    private String position;
    
   /**
    * An empty Accessory constructor, called upon in order
    * to help extract data into it.
    */
   public Accessory() 
   {

   }
    
   /**
    * This is called upon by readData() method within Class
    * Shop and passed a scanner object.Designed to read text data,
    * relevant to Accessory data from a given file.
    */
    public void extractData(Scanner accessoryScan)
    {
        setPosition(accessoryScan.next());
        accessoryScan.close(); 
    }
    
    /**
     * printDetails() to be called from Perishable and
     * WorkWear subclasses. This method calls its own accessor
     * methods and prints them to the terminal.
     */
    public void printDetails() 
    {
        super.printDetails();
        System.out.println("Position : " + " " + getPosition());
    }
    
    /**
     * Accessor and Mutator methods designed to be 
     * called upon by Perishable and Workwear subclass
     * methods
     */

    public void setPosition(String position) 
    {
        this.position = position;
    }
    
        public String getPosition()
    {
        return position;
    }
}
