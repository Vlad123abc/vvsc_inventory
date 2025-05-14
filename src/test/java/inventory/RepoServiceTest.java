package inventory;

import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RepoServiceTest {

    private InventoryRepository inventoryRepository; // Real repository
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() throws IOException {
        // Initialize the real InventoryRepository
        inventoryRepository = new InventoryRepository("data/test3.txt");

        // Initialize the InventoryService with the real repository
        inventoryService = new InventoryService(inventoryRepository);
    }

    @Test
    void testAddProductWithMockedProduct() {
        // Arrange
        Product mockProduct = mock(Product.class); // Mock the Product class
        when(mockProduct.getProductId()).thenReturn(1);
        when(mockProduct.getName()).thenReturn("MockProduct");
        when(mockProduct.getPrice()).thenReturn(100.0);
        when(mockProduct.getInStock()).thenReturn(10);
        when(mockProduct.getMin()).thenReturn(1);
        when(mockProduct.getMax()).thenReturn(20);
        when(mockProduct.getAssociatedParts()).thenReturn(FXCollections.observableArrayList());

        // Act
        inventoryRepository.addProduct(mockProduct); // Add the mocked product to the repository
        ObservableList<Product> products = inventoryService.getAllProducts(); // Retrieve all products

        // Assert
        assertFalse(products.isEmpty(), "The products list should not be empty.");
        Product addedProduct = products.get(0); // Get the first product
        assertEquals("MockProduct", addedProduct.getName(), "The product name should match.");
        assertEquals(100.0, addedProduct.getPrice(), "The product price should match.");
        assertEquals(10, addedProduct.getInStock(), "The product inventory level should match.");
    }

    @Test
    void testLookupMockedProduct() {
        // Arrange
        Product mockProduct = mock(Product.class); // Mock the Product class
        when(mockProduct.getProductId()).thenReturn(1);
        when(mockProduct.getName()).thenReturn("MockProduct");
        when(mockProduct.getPrice()).thenReturn(100.0);
        when(mockProduct.getInStock()).thenReturn(10);
        when(mockProduct.getMin()).thenReturn(1);
        when(mockProduct.getMax()).thenReturn(20);
        when(mockProduct.getAssociatedParts()).thenReturn(FXCollections.observableArrayList());

        // Add the mocked product to the repository
        inventoryRepository.addProduct(mockProduct);

        // Act
        Product result = inventoryService.lookupProduct("MockProduct"); // Lookup the product by name

        // Assert
        assertNotNull(result, "The product should be found.");
        assertEquals("MockProduct", result.getName(), "The product name should match.");
        assertEquals(100.0, result.getPrice(), "The product price should match.");
    }
}
