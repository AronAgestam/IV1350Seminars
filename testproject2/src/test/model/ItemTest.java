package test.model;
import main.model.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class ItemTest {

    int a = 5;
    String b = "Banan";
    double c = 8;
    double d = 6;
    int e = 1;

    @Test
    public void testHello() {
        assertTrue(true, "Du är dum :)");
    }
    @Test
    public void test() {
        Item banan = new Item(a, b, c, d, e);
        assertEquals(a, banan.getItemIdentifier(), "Identifier not matching");
        assertEquals(b, banan.getItemDescription(), "Description not matching");
        assertEquals(c, banan.getPrice(), "Price not matching");
        assertEquals(d, banan.getVAT(), "VAT not matching");
    }
}
