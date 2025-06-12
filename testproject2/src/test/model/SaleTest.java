package test.model;
import main.integration.Discount;
import main.model.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.ArrayList;

public class SaleTest {

    double a;
    double b;
    LocalTime c;
    ArrayList<Item> d;
    double e;
    double f;
    ArrayList<Discount> g;
    Item Morot;
    Item Potatis;
    Item Banan;
    
    @BeforeEach
    public void setUp() {
        a = 1;
        b = 2;
        c = LocalTime.now();
        d = new ArrayList<Item>();
        e = 0;
        f = 0;
        g = new ArrayList<Discount>();
        Morot = new Item(17, "Lång, Orange", 50, 2, 3);
        Potatis = new Item(42, "Stor, rund", 20, 2, 2);
    }

    @AfterEach
    public void tearDown() {

    }
    
    @Test
    public void test() {
        Sale testSale = new Sale();
        Item Morot = new Item(17, "Lång, Orange", 43, 2, 3);
        testSale.addItem(Morot, 4);
        System.err.println(testSale.getItems());
        assertEquals(1, testSale.getItems().size(), "One item sale should have one item");
        assertEquals(17, testSale.getItems().get(0).getItemIdentifier(), "Identifier not matching");
        
        Item potät = new Item(42, "Stor, rund", 43, 2, 3);
        int expectedQuantity = 48383;
        testSale.addItem(potät, expectedQuantity);
        System.err.println(testSale.getItems());
        assertEquals(2, testSale.getItems().size(), "Two item sale should have two items");
        assertEquals(expectedQuantity, testSale.getItems().get(1).getQuantity(), "Two item sale should have two items");

        Item potät2 = new Item(42, "Stor, rund", 43, 2, 3);
        testSale.addItem(potät2, 48383);

        assertEquals(2, testSale.getItems().size(), "Three item sale with two distinct items should have two item list");

    }

    // "Morot" has base quantity 3 in BeforeEach, but that is overwritten by addItem, working as intended.
    @Test
    public void testAddItem() {
        Sale testSale = new Sale();
        assertEquals(0, testSale.getItems().size(), "New sale should have no item");
        testSale.addItem(Morot, 4);
        assertEquals(1, testSale.getItems().size(), "One item sale should have one item");
        assertEquals(4, testSale.getItems().get(0).getQuantity(), "Quantity not matching - 0 + 4 Morot");
        testSale.addItem(Morot, 5);
        assertEquals(1, testSale.getItems().size(), "One item sale should have one item");
        assertEquals(9, testSale.getItems().get(0).getQuantity(), "Quantity not matching - 0 + 4 + 5 Morot");

    }

    @Disabled
    @Test
    public void testToString() {
        Sale currentSale = new Sale();
        String expectedString = "Sale: [ " + a + " " + b + " " + c + " " + d + " " + e + " " + f + " " + g + " ]";
        assertEquals(expectedString, currentSale.toString(), "Strings not matching");

    }

    @Disabled
    @Test
    public void testToString2() {
        Sale currentSale = new Sale();
        String expectedString = "Sale: [ 1.0 2.0 10:15 [] store address 7.0 ]";
        assertEquals(expectedString, currentSale.toString(), "Strings not matching");
    }

    @Test
    public void testSale() {
        Sale currentSale = new Sale();
        assertEquals(d, currentSale.getItems(), "Items not matching");
        assertEquals(e, currentSale.getPaymentAmount(), "Payment not matching");
        assertEquals(f, currentSale.getChangeAmount(), "Change not matching");
        assertEquals(g, currentSale.getDiscounts(), "Discount not matching");
    }
}
    