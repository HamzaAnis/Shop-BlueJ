import java.util.Scanner;
/**
 * ShopItem class, contains generic fields, shared by both
 * Tool and Accessory class thus used to avoid duplication.
 * As this is an abstract class, it is only instantiated by
 * subclass Tool and Accessories.
 * 
 * @author  
 * @group(4)
 */
public abstract class ShopItem
{
    /**
     * Fields of the superclass, that are to be inherited by Tool and
     * Accessory child classes.
     */
    
    private String itemName;
    private String itemCode;
    private double cost;
    
    /**
     * Constructor for objects of class ShopItem, 
     * initialising cost variable to value of 0.
     */
    public ShopItem()
    {
       cost=0;
    }

    /**
    * This is called upon by readData() method within Class
    * Shop and passed a scanner object.Designed to read text data,
    * relevant to Accessory data from a given file.
    */
    public void extractTokens (Scanner toolScan) 
    {
        itemName = toolScan.next();
        itemCode = toolScan.next();
        cost = toolScan.nextDouble();
    }
    
    /**
     *  Calls accessor methods within this ShopItem class
     *  and prints to terminal. This method is called by
     *  the printDetails() of Tool and/or Accessory.
     */
    public void printDetails() 
    {
       System.out.println("------" + "\n" + "Name : " + " " + getItemName() + "\n " + " \n" + "ItemCode : " + " " + getItemCode()
        + "\n" + "Cost : " + " " + getCost());
    }  

    /**
     * Accessor and Mutator methods designed to be 
     * called upon by either Tool or Accessory 
     * methods
     */
    
    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }
}
