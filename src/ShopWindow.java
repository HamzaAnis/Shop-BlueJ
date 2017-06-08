// These import statements could be greatly reduced in number by using,
// for example, import java.awt.*;, but in this form we can clearly see
// all the classes imported
import java.awt.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

/**
 * Class ShopWindow is a front-end for the reservation system. It concentrates
 * the GUI aspects in one place and relies on the Shop class to provide the shop
 * model functionality.
 *
 * The window has an area for displaying output, for example the output from
 * System.out.println(), and a menu bar.
 *
 * @author D.E.Newton
 * @version Part 4 Step 5
 */
public class ShopWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 42L; // needed because JFrame is serializable, otherwise generates compiler warning
    private static int WIDTH = 800, HEIGHT = 600; // for the areaScrollPane, which will dictate the overall window size
    public static String currentShopName = "None Loaded";
    public Shop shop;
    private Container contentPane;
    private JTextArea outputArea;
    private HashSet<String> associatedTextSet; // for menus and menu items
    private JMenu shopMenu, editMenu, shopItemMenu, customerMenu, reservationMenu, helpMenu;
    private   JButton itembtn,reservbtn;
    private JTextField t1,t2,t3,t4;
    public ShopItemReservation reserve;
    /**
     * Constructor for objects of class ReservationSystemWindow
     */
    public ShopWindow() {
  //  itembtn=new JButton();
itembtn=new JButton();
reservbtn=new JButton();

     t1=new JTextField(10);
        t2=new JTextField(10);
        t3=new JTextField(10);
        t4=new JTextField(10);
        associatedTextSet = new HashSet<String>();

        initialiseWindow();
    }

    private void initialiseWindow() {
      
        setTitle("Shop: None Loaded");
        setLocation(50, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setBackground(Color.magenta);  // magenta so it is obvious if can see the background when "layout" not worked correctly !
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // this code, involving window adapter and window listener, is to ensure that if the
        // window is closed by using the "Close" icon at the top right hand corner of the
        // window then the "Exit" command is executed so that the system is closed down properly
        WindowAdapter windowListener = new WindowAdapter() {
            // override windowClosing()
            public void windowClosing(WindowEvent e) {
                ActionEvent action = new ActionEvent(this, 0, "Exit");
                actionPerformed(action);
            }
        };
        addWindowListener(windowListener);
     
      
                    itembtn.setText("add item");
                    reservbtn.setText("reserve item");
                    
                    t1.setText("item type");
                    t2.setText("item name");
                    t3.setText(currentShopName.toString());
                    t3.disable();
                        
  itembtn.addActionListener(this);

        reservbtn.addActionListener(this);

addItemV();
        setupMenusAndActions();
        setUpOutputArea();
  add(t1);
        add(t2);
        add(t3);
        add(t4);  
        
        add(itembtn);
        add(reservbtn);
        setVisible(true);
    }
public void addItemV(){
    itembtn.setVisible(false);
                        t1.setVisible(false);
                        t2.setVisible(false);
                        t3.setVisible(false);
                        reservbtn.setVisible(false);
                        t4.setVisible(false);}
    private void setUpOutputArea() {
        // some basic ideas taken from
        //    http://www.jcreator.com/forums/index.php?showtopic=773
        //    http://javacook.darwinsys.com/new_recipes/14.9betterTextToTextArea.jsp
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Courier", Font.PLAIN, 12));
        outputArea.setEditable(false);
        outputArea.setBackground(Color.white);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setMargin(new Insets(5, 10, 5, 10));

        JScrollPane areaScrollPane = new JScrollPane(outputArea);
        areaScrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Border b = BorderFactory.createLoweredBevelBorder();
        areaScrollPane.setViewportBorder(BorderFactory.createTitledBorder(b, "Output View"));

        contentPane.add(areaScrollPane);
        pack();

        TextAreaOutputStream textOutput = new TextAreaOutputStream(outputArea);
        PrintStream capturedOutput = new PrintStream(textOutput);
        System.setOut(new PrintStream(capturedOutput));  // divert StandardOutput to capturedOutput
    }

    private Integer getKeyEventMnemonic(char letter) {

        if (!Character.isLetter(letter)) {
            // for non-letters, need to use KeyEvent.VK_XXX to associate letter
            System.out.println("\n*** Character <" + letter + "> is not a letter. ");
            System.out.println("*** Letters only are accepted for mnemonic association");
            System.exit(1); // none letter auto-detected, this can only occur during development so ok to halt
        }

//        if (Character.isLowerCase(letter)) {
//            System.out.println("\n*** Warning ");
//            System.out.println("*** Mnemonic letter '" + letter + "' converted to upper case in getKeyEventMnemonic() ");
//            System.out.println("*** When using the letter with the Alt key it is not case sensitive ");
//            letter = Character.toUpperCase(letter);
//        }

        // letters A-Z have simple associations
        int key = letter; // char assigned to int
        return key;
    }

    private JMenu setupMenu(JMenuBar menuBar, String menuText, char letter) {
        if (associatedTextSet.contains(menuText)) {
            // the text is used to identify the menu item and it should be unique to avoid confusion
            System.out.println("\n*** Aborting program");
            System.out.println("*** Attempt to define a menu with duplicate text \"" + menuText + "\" in setupMenu()");
            System.exit(2);  // duplicate auto-detected, this can only occur during development so ok to halt
        }
        int key = getKeyEventMnemonic(letter);
        JMenu menu = new JMenu(menuText);
        menu.setMnemonic(key);
        menuBar.add(menu);
        return menu;
    }

    private void setupMenuItem(JMenu menu, String menuItemText, String menuItemTip,
            char letter, boolean enabled) {
        setupMenuItem(menu, menuItemText, menuItemTip, letter, enabled, null);
    }

    private void setupMenuItem(JMenu menu, String menuItemText, String menuItemTip,
            char letter, boolean enabled, KeyStroke keyStroke) {
        if (associatedTextSet.contains(menuItemText)) {
            // the text is used to identify the menu item and link it to an action so it must be unique
            System.out.println("\n*** Aborting program");
            System.out.println("*** Attempt to define a menu item with duplicate text \"" + menuItemText + "\" in setupMenuItem()");
            System.exit(3);  // duplicate auto-detected, this can only occur during development so ok to halt
        }
        associatedTextSet.add(menuItemText);

        int key = getKeyEventMnemonic(letter);
        JMenuItem menuItem = new JMenuItem(menuItemText);
        menuItem.addActionListener(this);
        menuItem.setEnabled(enabled);
        menuItem.setMnemonic(key);
        menuItem.setToolTipText(menuItemTip);
        if (keyStroke != null) {
            menuItem.setAccelerator(keyStroke);
        }
        menu.add(menuItem);
    }

    private void setupMenusAndActions() {
        JMenuBar menuBar = new JMenuBar();

        // shop menu
        shopMenu = setupMenu(menuBar, "Shop", 'S');
        setupMenuItem(shopMenu, "New shop...", "Create a shop", 'N', true);
        setupMenuItem(shopMenu, "Open shop...", "Open a shop", 'O', true);
        setupMenuItem(shopMenu, "Close shop", "Close the shop", 'C', false);
        setupMenuItem(shopMenu, "Exit", "Close down and exit the model", 'X', true);

        // edit menu
        editMenu = setupMenu(menuBar, "Edit", 'E');
        setupMenuItem(editMenu, "Copy", "Copy selected text from Output area to clipboard", 'C', true, KeyStroke.getKeyStroke("ctrl C"));
        setupMenuItem(editMenu, "Clear", "Clear Output area", 'L', true);
        setupMenuItem(editMenu, "Print", "Print text in the output area", 'P', true);

        // shop item menu
        shopItemMenu = setupMenu(menuBar, "Shop item", 'I');
        setupMenuItem(shopItemMenu, "Print shop item...", "Display shop item with given code", 'V', true);
        setupMenuItem(shopItemMenu, "Print all shop items", "Display all shop items", 'D', true);
        setupMenuItem(shopItemMenu, "add item", "add item", 'D', true);

        setupMenuItem(shopItemMenu, "print all items", "print all items", 'D', true);
        setupMenuItem(shopItemMenu, "print all reservations", "print all reservations", 'D', true);
        
        // customer menu
        customerMenu = setupMenu(menuBar, "Customer", 'C');
        setupMenuItem(customerMenu, "Print customer...", "Display customer with given id", 'C', true);
        setupMenuItem(customerMenu, "Print all customers", "Display all customers in the shop", 'D', true);
        setupMenuItem(customerMenu, "Load customers...", "Read customer data from text file", 'L', true);

        // reservation menu
        reservationMenu = setupMenu(menuBar, "Reservation", 'R');
        setupMenuItem(reservationMenu, "Make reservation...", "Make a reservation of a shop item for a customer", 'M', true);
        setupMenuItem(reservationMenu, "Print all reservations", "Display all reservations in the model", 'D', true);

        // help menu
        helpMenu = setupMenu(menuBar, "Help", 'H');
        setupMenuItem(helpMenu, "Help contents", "Launch html documentation", 'C', true);
        setupMenuItem(helpMenu, "About", "About the shop", 'A', true);

        setJMenuBar(menuBar);
    }

    /**
     * method actionPerformed
     *
     * Action events are generated by components such as buttons and menus in
     * response to mouse clicks, keystrokes etc. These events as passed to all
     * registered EventListener objects to deal with as they see fit. ShopWindow
     * objects will respond to action events generated by menu items because the
     * menu items have registered "this" as a listener using the
     * addActionListener() method.
     *
     * @param e, an ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        //
        // System menu
        //
        
        if (e.getSource() ==reservbtn) {
            System.out.println("  Newly entered record");
        //store item to file here
    addItemV();
    reserve=new ShopItemReservation();
        BufferedWriter out = null;
try  
{
   
    FileWriter fstream = new FileWriter(currentShopName+"reservationShopItemRecord.txt", true); //true tells to append data.
    out = new BufferedWriter(fstream);
    Random rn = new Random();
int n = 100 -2 + 1;
int i = rn.nextInt() % n;
int randomNum =  2 + i;
DateUtil d=new DateUtil();
Date ds=new Date();
LocalDate localDate = LocalDate.now();
String Localdate=d.convertDateToShortString(ds);
    out.write(randomNum+","+t1.getText()+","+t2.getText()+","+t3.getText()+localDate.toString()+"\n");
   
    
}
catch (IOException es)
{
    System.err.println("Error: " + es.getMessage());
}
finally
{
    if(out != null) {
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ShopWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
          JOptionPane.showMessageDialog(null, "added to the file");

        }
        
        
        if (e.getSource() ==itembtn) {
            System.out.println("  Newly entered record");
        //store item to file here
    addItemV();
    reserve=new ShopItemReservation();
    t3.setText(currentShopName);
        System.out.println("Item ID : "+t1.getText());
            System.out.println("Item Name:"+t2.getText());
        System.out.println("Shop Name: "+t3.getText());
        BufferedWriter out = null;
try  
{
   
    FileWriter fstream = new FileWriter(currentShopName+"itemsRecord.txt", true); //true tells to append data.
    out = new BufferedWriter(fstream);
    Random rn = new Random();
int n = 100 -2 + 1;
int i = rn.nextInt() % n;
int randomNum =  2 + i;

    out.write(String.valueOf(randomNum)+","+t1.getText()+","+t2.getText()+","+t3.getText()+"\n");
   
    
}
catch (IOException es)
{
    System.err.println("Error: " + es.getMessage());
}
finally
{
    if(out != null) {
        try {
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ShopWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
          JOptionPane.showMessageDialog(null, "added to the file");

        }
        if (action.equals("New shop...") || action.equals("Open shop...")) {

            // these two actions grouped together to avoid duplicate code
            String prefix;
            if (action.equals("New shop...")) {
                prefix = "creating";
            } else {
                prefix = "re-loading";
            }

            String inputMessage = "Please input the name of the shop";

            String name = JOptionPane.showInputDialog(inputMessage);
            if (name != null) {
                currentShopName = name;
                if (name.length() == 0) {
                    name = "Not named";
                }
                System.out.println("\n " + prefix + " shop \"" + name + "\"");
                shop = new Shop(name);
                if (action.equals("New shop...")) {

                    try {
                        String cusfname = name + "customerRecord.txt";
                        String resvfname = name + "reservationShopItemRecord.txt";
                        String item = name + "itemsRecord.txt";
                        
                        File cus = new File(cusfname);
                        File resv = new File(resvfname);
                        File it = new File(item);

                        if (cus.createNewFile() && resv.createNewFile() && it.createNewFile()) {
                            System.out.println("Files are created !" + "Customers Record belonging to this shop in  : "
                                    + cusfname + "\n" + "Shop item Reservation record in " + resvfname);

                        } else {
                            System.out.println("File already exists.");
                        }

                    } catch (IOException ce) {
                        ce.printStackTrace();
                    }
//shop.reloadShop();
                }
                // add name of the shop to the window title
                String oldTitle = getTitle();
                int posnColon = oldTitle.indexOf(":");
                setTitle(oldTitle.substring(0, posnColon + 2) + name);

                checkEnableStatusOfCommands();
            }
        } else if (action.equals("Close shop")) {
            //shop.closeDownSystem(); // save data so can restart
            shop = null;
            outputArea.selectAll();
            outputArea.setText("");

            checkEnableStatusOfCommands();
        } 
        
        else if (action.equals("Exit")) {
            if (shop != null) // a shop has been created or opened so save data so can restart
            //shop.closeDownSystem();
            {
                System.exit(0);  // close down the application
            }
        } //
        // Edit menu
        //
        else if (action.equals("Copy")) {
            outputArea.copy();
        } else if (action.equals("Clear")) {
            outputArea.selectAll();
            outputArea.setText("");

        } else if (action.equals("Print")) {

            // errorPrintln("\nAction \"" + action + "\" not fully implemented");
        } //
        // Shop item menu
        //
        else if (action.equals("Print shop item...")) {
            // shop.printItem("1"); 

        } 
        else if (action.equals("add item")){
        outputArea.selectAll();
        outputArea.setText("");
       t1.setVisible(true);
       t2.setVisible(true);
       t3.setVisible(true);
       t3.setText(currentShopName);
       itembtn.setVisible(true);
        }
        
        
        else if (action.equals("Print all shop items")) {
            if (currentShopName == "None Loaded") {
                JOptionPane.showMessageDialog(null, "Select a shop first");

            } else //         System.out.println("y)es I am going");
            {
                shop.printItemDetails();
            }
        } //
        // customer menu
        //
        else if (action.equals("Print customer...")) {
            if (currentShopName == "None Loaded") {
                JOptionPane.showMessageDialog(null, "Select a shop first");

            } else 
            {
                if (shop.customerMap.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please load customer first to print a specific customer record");
           
            }

                else
                {
                
                 String inputMessage="enter  the id of the customer that you want to print";
                String cusid = JOptionPane.showInputDialog(inputMessage);
           int check=0;
                if (cusid != null) {
            for (Customer c : shop.customerMap.values()) {
                if(cusid.equals(c.getCustomerID()))
{   check=1;
                  c.printDetails();
                  break;
}                   
                }
            if(check==0){
                            JOptionPane.showMessageDialog(null, "No such customer found");

            }
            }}
            }

            //   errorPrintln("\nAction \"" + action + "\" not fully implemented");
        } else if (action.equals("Print all customers")) {
            if (currentShopName == "None Loaded") {
                JOptionPane.showMessageDialog(null, "Select a shop first");

            }
            else  {
                System.out.println(currentShopName);
                String f = currentShopName + "customerRecord.txt";
                 shop.readCustomerData(f);}
            //  errorPrintln("\nAction \"" + action + "\" not fully implemented");
        } else if (action.equals("Load customers...")) {

            if (currentShopName == "None Loaded") {
                JOptionPane.showMessageDialog(null, "Select a shop first");

            } else {
                            String f = currentShopName + "customerRecord.txt"; 
                          shop.loadData(f);
                JOptionPane.showMessageDialog(null, "Customers LOADED!");
            }

        } //
        // reservation menu
        //
        else if (action.equals("Make reservation...")) {
         //   errorPrintln("\nAction \"" + action + "\" not fully implemented");
       
        addItemV();
        t1.setText("Customer ID");
        t2.setText("Item Id");
        t3.setText("reserving days");
        t3.enable();
        t1.setVisible(true);
        t2.setVisible(true);
        t3.setVisible(true);
        t4.setVisible(true);
        reservbtn.setVisible(true);
        
        
        } else if (action.equals("Print all reservations")) {
            shop.printAllReservations();
            //    errorPrintln("\nAction \"" + action + "\" not fully implemented");
        } //
        // Help menu
        //
        else if (action.equals("Help contents")) {
            errorPrintln("\nAction \"" + action + "\" not fully implemented");
        } 
        
        else if(action.equals("print all items")){
        String f=currentShopName+"itemsRecord.txt";
        reserve=new ShopItemReservation();
        reserve.readReservationData(f);
                
        }
        else if (action.equals("About")) {
            String version = "Version: 4.11 (released 15 April, 2010)";
            JOptionPane.showMessageDialog(this, version, "About the shop",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            errorPrintln("\n*** Warning");
            errorPrintln("*** Action \"" + e.getActionCommand() + "\" not recognised");
    addItemV();

        }
    }

    private void checkEnableStatusOfCommands() {
        // called after every action that might affect the state of the shop

        if (shop != null) {
            // disable New shop... and Open shop... and enable Close shop commands on Shop menu
            menuItemSetEnabled(false, shopMenu, "New shop...");
            menuItemSetEnabled(false, shopMenu, "Open shop...");
            menuItemSetEnabled(true, shopMenu, "Close shop");

            // enable Load commands
            menuItemSetEnabled(true, customerMenu, "Load customers...");

            boolean hasShopItems = shop.getNumberOfItems() > 0;
            boolean hasCustomers = shop.getNumberOfCustomers() > 0;

            if (hasShopItems) {
                // enable other items on shop item menu
                menuItemSetEnabled(true, shopItemMenu, "Print shop item...");
                menuItemSetEnabled(true, shopItemMenu, "Print all shop items");
            }

            if (hasCustomers) {
                // enable other items on customer menu
                menuItemSetEnabled(true, customerMenu, "Print customer...");
                menuItemSetEnabled(true, customerMenu, "Print all customers");
            }

            if (hasShopItems && hasCustomers) {
                // enable Make reservation... item on shop menu
                menuItemSetEnabled(true, reservationMenu, "Make reservation...");
            }
        } else {
            // no shop model

            // enable New shop... and Open shop... commands
            menuItemSetEnabled(true, shopMenu, "New shop...");
            menuItemSetEnabled(true, shopMenu, "Open shop...");

            // make sure all menu items in shop item, customer and reservation menus are disabled
            menuItemSetEnabled(false, shopItemMenu, "Print item...");
            //menuItemSetEnabled(false, shopItemMenu, "Print vehicles");
            menuItemSetEnabled(false, customerMenu, "Load customers...");
            menuItemSetEnabled(false, customerMenu, "Print customer...");
            menuItemSetEnabled(false, customerMenu, "Print all customers");
            menuItemSetEnabled(false, reservationMenu, "Make reservation...");
            menuItemSetEnabled(false, reservationMenu, "Print all reservations");
        }
    }

    private void menuItemSetEnabled(boolean enable, JMenu menu, String actionCommand) {
        for (int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem item = menu.getItem(i);
            if (item.getActionCommand().equalsIgnoreCase(actionCommand)) {
                item.setEnabled(enable);
                return;
            }
        }

        // actionCommand not recognised
        errorPrintln("\n*** Warning -- Unexpected Error");
        errorPrintln("***\t Action \"" + actionCommand + "\" not recognised in method menuItemIsEnabled()");
        String enableAction;
        if (enable) {
            enableAction = "enabled";
        } else {
            enableAction = "disabled";
        }
        errorPrintln("***\t The action has NOT been " + enableAction);
        errorPrintln("***\t Please report this error to the System Administrator");
        errorPrintln("\n*** Note: The System Administrator is YOU if you have added ");
        errorPrintln("*** erroneous actionCommands.");
    }

    private void errorPrintln(String message) {
        // convenience method so can write error mesages to both System.err and System.out
        System.err.println(message);
        System.out.println(message);
    }

    // inner class
    private class TextAreaOutputStream extends OutputStream {

        private final JTextArea textArea;
        private final StringBuilder sb = new StringBuilder();
        private boolean streamOpen = true;
        private boolean noError = true;

        public TextAreaOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void flush() {
            // ignore
        }

        @Override
        public void close() {
            streamOpen = false;
        }

        @Override
        public void write(int b) throws IOException {
            // The print() and println() methods of the PrintStream class ultimately depend
            // on the abstract method write(int b) of the OutputStream class.  This subclass
            // overrides write(int b) and appends the character corresponding to b to a
            // StringBuffer object sb, unless that character corresponds to a line feed
            // character.  In the latter case, the contents of sb are appended to the JTextArea
            // and sb is cleared ready for the next call to write().
            if (!streamOpen) {
                if (noError) {
                    // Only output a message first time write() invoked when stream closed.
                    // Cannot (usefully) throw an IOException because the PrintStream that
                    // uses TextAreaOutputStream merely sets its error state true and then
                    // relies on checkError() being invoked.
                    System.err.println("\n*** Unexpected error: TextAreaOutputStream closed when attempting to use write()");
                    noError = false;
                } else {
                    return; // ignore call
                }
            }

            if (b == '\r') // carriage return, do nothing
            {
                return;
            }

            if (b == '\n') // line feed, first copy string to textArea
            {
                textArea.append(sb.toString());
                sb.setLength(0);
            }

            sb.append((char) b);
        }
    }
}
