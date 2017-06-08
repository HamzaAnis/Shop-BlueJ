import java.util.Scanner;
/**
 * Perishable is a subclass of Accessory, it represents
 * an Perishable items such as fuel hence relevant fields.
 * The constructor calls its SuperClass Accessory and ShopItem. 
 *
 * @author (Hassan Ali)
 * @group (4)
 */

public class Perishable extends Accessory
{
    /**
     * Fields of the subclass Perishable
     */
    private boolean isIrritant;
    private String useByDate;
    private int volume;

    /**
     * A no parameter constructor, initilising variables
     * isIrritant, useByDate and volume.
     */
    public Perishable() 
    {
        isIrritant = false;
        useByDate = null;
        volume = 0;
    }

    /**
    * This is called upon by readData() method within Class
    * Shop and passed a scanner object.Designed to read text data,
    * relevant to Perishable data from a given file.
    */
    public void extractTokens (Scanner perishScan)
    {
        isIrritant = perishScan.nextBoolean();
        useByDate = perishScan.next();
        volume = perishScan.nextInt();
        super.extractTokens (perishScan);
        perishScan.close(); 
    }

    /**
     * Calls the printDetails() from the Accessory SuperClass to print 
     * out common variables. This method also calls its own accessor
     * methods and prints them to the terminal.
     */
    public void printDetails() 
    {
        super.printDetails();
        System.out.println("Irritant : " + "" + isIrritant() + "\nUseByDate : " + getUseByDate() + "\nVolume : " + getVolume());
    }
    
    /**
     * Accessor and Mutator methods, designed to be called upon.
     * Used by printDetails() method above. Mutator methods
     * are used by extractTokens () method by the scanner object.
     */
    
    public int getVolume() 
    {
        return volume;
    }

    public void setVolume(int v) 
    {
        this.volume = v;
    }
    
    public boolean isIrritant()
    {
        return isIrritant;
    }

    public void setIrritant(boolean i) 
    {
        this.isIrritant = i;
    }
    
    public String getUseByDate()
    {
        return useByDate;
    }

    public void setUseByDate(String date) 
    {
        this.useByDate = date;
    }
}

