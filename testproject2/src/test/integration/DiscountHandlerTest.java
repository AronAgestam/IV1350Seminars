package test.integration;
import main.integration.*;
import main.model.*;
import main.controller.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.*;     
    
public class DiscountHandlerTest {

    double a;
    double b;
    LocalTime c;
    ArrayList<Item> d;
    double e;
    double f;
    ArrayList<Discount> g;

    @BeforeEach
    public void setup(){
        a = 1;
        b = 2;
        c = LocalTime.now();
        d = new ArrayList<Item>();
        e = 0;
        f = 0;
        g = new ArrayList<Discount>();
    }
    
    
    @Test
    public void test1() throws Exception{
        Sale currentSale = new Sale();
        currentSale.addItem(new Item(123, "Potatis", 50, 6, 1), 1);
        DiscountHandler dh = new DiscountHandler();
        ArrayList<Discount> discounts = dh.searchDiscount(currentSale, 123); 
        assertEquals(0, discounts.size(), "Expected no discounts");
    }
    @Test
    public void test2() throws Exception{
        Sale currentSale = new Sale();
        currentSale.addItem(new Item(123, "Potatis", 1000, 6, 1), 1);
        DiscountHandler dh = new DiscountHandler();
        ArrayList<Discount> discounts = dh.searchDiscount(currentSale, 123); 
        assertEquals(1, discounts.size(), "Expected one discount");
        Discount disc1 = discounts.get(0);
        assertEquals(0.04, disc1.getDiscountPercentage(), "Expected 4 percent discount for total cost over 200");
        assertEquals(40, disc1.getDiscountSum(), "Expected 4 percent discount for total cost over 200");
    }
    @Test
    public void test3() throws Exception{
        Sale currentSale = new Sale();
        currentSale.addItem(new Item(123, "Potatis", 50, 6, 1), 1);
        DiscountHandler dh = new DiscountHandler();
        ArrayList<Discount> discounts = dh.searchDiscount(currentSale, 1002); 
        assertEquals(1, discounts.size(), "Expected one discount");
        Discount disc1 = discounts.get(0);
        assertEquals(0.06, disc1.getDiscountPercentage(), "Expected 5 percent discount for certain customerID");
        assertEquals(3, disc1.getDiscountSum(), "Expected 5 percent discount for certain customerID");
    }
    @Test
    public void test4() throws Exception{
        Sale currentSale = new Sale();
        currentSale.addItem(new Item(123, "Banan", 20, 6, 2), 2);
        DiscountHandler dh = new DiscountHandler();
        ArrayList<Discount> discounts = dh.searchDiscount(currentSale, 123); 
        assertEquals(1, discounts.size(), "Expected one discount");
        Discount disc1 = discounts.get(0);
        assertEquals(0.20, disc1.getDiscountPercentage(), "Expected 20 percent discount on duplicate items");
        assertEquals(8, disc1.getDiscountSum(), "Expected 20 percent discount on duplicate items");
    }
    
    @Test
    public void test5() throws Exception{
        Sale currentSale = new Sale();
        currentSale.addItem(new Item(123, "Potatis", 2000, 6, 1),1);
        currentSale.addItem(new Item(124, "Banan", 1000, 6, 1),2);
        DiscountHandler dh = new DiscountHandler();
        ArrayList<Discount> discounts = dh.searchDiscount(currentSale, 1003); 
        assertEquals(3, discounts.size(), "Expected three discounts");
        Discount disc1 = discounts.get(0);
        assertEquals(0.04, disc1.getDiscountPercentage(), "Expected 4 percent discount for total cost over 200");
        assertEquals(160, disc1.getDiscountSum(), "Expected 4 percent discount for total cost over 200");
    }
 
}