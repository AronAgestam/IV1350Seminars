package test.integration;
import main.view.*;
import main.controller.*;
import main.integration.*;
  
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class ExceptionTest {

    Controller controller;
    View view;
    EISHandler eis;
    DiscountHandler dh;

    @BeforeEach
    public void setup(){
        this.dh = new DiscountHandler();
        this.eis = new EISHandler();
        this.controller = new Controller();
		this.view = new View(controller);
        view.startSaleProcedure();
    }

    @Test 
    public void testItemNotFoundException(){ 
        // Thrown from bottom-level method
        assertThrows(ItemNotFoundException.class, () -> eis.searchItem(4), "Exception not thrown from searchItem in EISHandler for itemID 4"); 
        // Thrown from top-level method 
        assertThrows(ItemNotFoundException.class, () -> view.addItemProcedure(4, 4), "Exception not thrown from addItemProcedure in View for itemID 4");
        // Caught in Example Run
        assertDoesNotThrow( () -> view.exampleRunItemNotFound(), "Exception not caught in exampleRunItemNotFound!" );
    }
    @Test 
    public void testItemDatabaseOfflineException(){ 
        // Thrown from bottom-level method
        assertThrows(DatabaseOfflineException.class, () -> eis.searchItem(1000), "Exception not thrown from searchItem in EISHandler for itemID 1000"); 
        // Thrown from top-level method 
        assertThrows(DatabaseOfflineException.class, () -> view.addItemProcedure(1000, 4), "Exception not thrown from addItemProcedure in View for itemID 1000");
        // Caught in Example Run
        assertDoesNotThrow( () -> view.exampleRunItemDatabase(), "Exception not caught in exampleRunItemDatabase!" );
    }
    @Test 
    public void testDiscountDatabaseOfflineException(){
        // Thrown from bottom-level method
        assertThrows(DatabaseOfflineException.class, () -> dh.searchDiscount(controller.getSale(), 1000), "Exception not thrown from searchDiscount in DiscountHandler for customerID 1000"); 
        // Thrown from top-level method 
        assertThrows(DatabaseOfflineException.class, () -> view.getDiscountProcedure(1000), "Exception not thrown from getDisocuntProcedure in View for customerID 1000");
        // Caught in Example Run
        assertDoesNotThrow( () -> view.exampleRunDiscountDatabase(), "Exception not caught in exampleRunDiscountDatabase!" );
    }

}
