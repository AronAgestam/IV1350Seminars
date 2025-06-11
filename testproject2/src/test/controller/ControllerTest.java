package test.controller;
import main.controller.*;
import main.integration.*;
import main.model.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
  
    
public class ControllerTest {

    Controller controller;
    
    @BeforeEach
    public void setup(){
        controller = new Controller();
    }
        
    @Test
    public void testDiscountDatabaseOfflineException(){
        try{
            controller.startSale();
            Item e = controller.addItem(2, 20);
            controller.getDiscount(1000);
            fail("DatabaseOfflineException not thrown!");
            }
        catch(ItemNotFoundException | DatabaseOfflineException exception){
        }
    }

    @Test
    public void testItemDatabaseOfflineException(){
        try{
            controller.startSale();
            Item e = controller.addItem(1000, 20);
            controller.getDiscount(1001);
            fail("DatabaseOfflineException not thrown!");
            }
        catch(ItemNotFoundException | DatabaseOfflineException exception){
        }
    }

    @Test
    public void testItemNotFoundException(){
        try{
            controller.startSale();
            Item e = controller.addItem(7, 20);
            controller.getDiscount(1001);
            fail("ItemNotFoundException not thrown!");
            }
        catch(ItemNotFoundException | DatabaseOfflineException exception){
        }
    }

    @Test
    public void testNoException(){
        try{
            controller.startSale();
            Item e = controller.addItem(2, 20);
            controller.getDiscount(1001);
            }
        catch(ItemNotFoundException | DatabaseOfflineException exception){
            fail("An Exception was thrown!");
        }
    }
}
    