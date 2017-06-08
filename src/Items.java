
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author accle
 */
public class Items {

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the cusId
     */
    public String getCusId() {
        return cusId;
    }

    /**
     * @param cusId the cusId to set
     */
    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @param shopName the shopName to set
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    private String id;
    private String cusId;
    private String itemName;
    private String shopName;
    	public void extractTokens (Scanner toolScan) 
    {
		id = toolScan.next();
        cusId = toolScan.next();
        itemName = toolScan.next();
        shopName = toolScan.next();
        
       // startDate = DateUtil.convertStringToDate(toolScan.next());
       // noOfDays = toolScan.nextInt();
    }
      public void printDetails() 
    {
       System.out.println("-----" + "\n" + "item Name : " + " " + getItemName()+"\n"+"customer Id : " +getCusId()
       +"\n"+"item type : "+getItemName()+"\n"+" shop name : "+getShopName()
       );
    } 
	

    	public void readItems(String filePath)
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
                  Items s = new Items();
                  s.extractTokens(sc);
              
                  s.printDetails();      
                   
              }
              else 
              {
            System.out.println("!!NO customer record found!!");
              }
          }
          if(counter==0){
                          System.out.println("!!NO  record found!!");

          }
          
      } catch (FileNotFoundException e) 
      {
          e.printStackTrace();
      } 
      }       
  }
  
}
