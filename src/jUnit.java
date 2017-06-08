

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class jUnit.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class jUnit
{
    /**
     * Default constructor for test class jUnit
     */
    public jUnit()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testStartDate()
    {
        ShopItemReservation shopItem1 = new ShopItemReservation();
        shopItem1.setNoOfDays(3);
        assertEquals(3, shopItem1.getNoOfDays());
    }

    @Test
    public void testDate()
    {
        ShopItemReservation shopItem1 = new ShopItemReservation();
        shopItem1.setCustomerID("12");
    }

    @Test
    public void testItemId()
    {
        Items items1 = new Items();
        items1.setId("1");
    }

    @Test
    public void testCustomerId()
    {
        Items items1 = new Items();
        items1.setCusId("1");
        assertEquals("1", items1.getCusId());
    }

    @Test
    public void testShopName()
    {
        Items items1 = new Items();
        items1.setShopName("shop");
        assertEquals("shop", items1.getShopName());
    }

    @Test
    public void testCustomersurName()
    {
        Customer customer1 = new Customer();
        customer1.setSurName("sur");
        assertEquals("sur", customer1.getSurName());
    }

    @Test
    public void testCustomerInitials()
    {
        Customer customer1 = new Customer();
        customer1.setOtherInitials("initials");
        assertEquals("initials", customer1.getOtherInitials());
    }

    @Test
    public void testCustomerTitle()
    {
        Customer customer1 = new Customer();
        customer1.setTitle("title");
        assertEquals("title", customer1.getTitle());
    }

    @Test
    public void testPower()
    {
        ElectricTool electric1 = new ElectricTool();
        electric1.setPower("2000");
        assertEquals("2000", electric1.getPower());
    }

    @Test
    public void testrechargeable()
    {
    }

    @Test
    public void testReadCustomerDAta()
    {
        Customer customer1 = new Customer();
        customer1.readData();
        customer1.readData();
    }
}











