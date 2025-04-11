package inventory;

import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventoryTest {

    @Test
    public void testNoMatchFound() {
        Inventory inventory = new Inventory();
        ObservableList<Part> parts = FXCollections.observableArrayList();
        inventory.addProduct(new Product(1, "ProductA", 10.0, 5, 1, 10, parts));
        inventory.addProduct(new Product(2, "ProductB", 15.0, 3, 1, 10, parts));

        Product result = inventory.lookupProduct("Produs");
        assertEquals(0, result.getProductId());
        assertNull(result.getName());
    }

    @Test
    public void testNoMatchFound_empty_list() {
        Inventory inventory = new Inventory();
        ObservableList<Part> parts = FXCollections.observableArrayList();

        Product result = inventory.lookupProduct("Produs");
        assertEquals(0, result.getProductId());
        assertNull(result.getName());
    }

    @Test
    public void testPartialMatchNoExactMatch() {
        Inventory inventory = new Inventory();
        ObservableList<Part> parts = FXCollections.observableArrayList();
        inventory.addProduct(new Product(1, "ProductA", 10.0, 5, 1, 10, parts));
        inventory.addProduct(new Product(2, "ProductB", 15.0, 3, 1, 10, parts));

        Product result = inventory.lookupProduct("Produs");
        assertEquals(0, result.getProductId());
        assertNull(result.getName());
    }

    @Test
    public void testExactMatchByName() {
        Inventory inventory = new Inventory();
        ObservableList<Part> parts = FXCollections.observableArrayList();
        inventory.addProduct(new Product(1, "Produs", 20.0, 10, 1, 15, parts));
        inventory.addProduct(new Product(2, "ProductB", 15.0, 3, 1, 10, parts));

        Product result = inventory.lookupProduct("Produs");
        assertEquals(1, result.getProductId());
        assertEquals("Produs", result.getName());
    }

    @Test
    public void testExactMatchById() {
        Inventory inventory = new Inventory();
        ObservableList<Part> parts = FXCollections.observableArrayList();
        inventory.addProduct(new Product(1, "ProductA", 10.0, 5, 1, 10, parts));
        inventory.addProduct(new Product(2, "ProductB", 15.0, 3, 1, 10, parts));

        Product result = inventory.lookupProduct("2");
        assertEquals(2, result.getProductId());
        assertEquals("ProductB", result.getName());
    }
}
