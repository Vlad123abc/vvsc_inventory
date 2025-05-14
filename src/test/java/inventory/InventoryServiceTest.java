package inventory;

import inventory.model.*;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    private InventoryService inventoryService;
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() throws IOException {
        // Initialize the InventoryRepository and InventoryService
        inventoryRepository = new InventoryRepository("data/test2.txt");
        inventoryService = new InventoryService(inventoryRepository);
    }

    @Test
    void testAddInhousePart() {
        // Arrange
        String name = "TestInhousePart";
        double price = 15.0;
        int inStock = 10;
        int min = 1;
        int max = 20;
        int machineId = 123;

        // Act
        inventoryService.addInhousePart(name, price, inStock, min, max, machineId);

        // Assert
        ObservableList<Part> parts = inventoryService.getAllParts();
        assertFalse(parts.isEmpty(), "The parts list should not be empty.");
        Part addedPart = parts.get(parts.size() - 1); // Get the last added part
        assertTrue(addedPart instanceof InhousePart, "The added part should be an InhousePart.");
        assertEquals(name, addedPart.getName(), "The part name should match.");
        assertEquals(price, addedPart.getPrice(), "The part price should match.");
        assertEquals(inStock, addedPart.getInStock(), "The part inventory level should match.");
    }

    @Test
    void testAddProduct() {
        // Arrange
        String name = "TestProduct";
        double price = 100.0;
        int inStock = 5;
        int min = 1;
        int max = 10;
        ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        associatedParts.add(new InhousePart(1, "Part1", 10.0, 5, 1, 10, 123));

        // Act
        inventoryService.addProduct(name, price, inStock, min, max, associatedParts);

        // Assert
        ObservableList<Product> products = inventoryService.getAllProducts();
        assertFalse(products.isEmpty(), "The products list should not be empty.");
        Product addedProduct = products.get(products.size() - 1); // Get the last added product
        assertEquals(name, addedProduct.getName(), "The product name should match.");
        assertEquals(price, addedProduct.getPrice(), "The product price should match.");
        assertEquals(inStock, addedProduct.getInStock(), "The product inventory level should match.");
        assertEquals(associatedParts.size(), addedProduct.getAssociatedParts().size(), "The associated parts size should match.");
    }
}
