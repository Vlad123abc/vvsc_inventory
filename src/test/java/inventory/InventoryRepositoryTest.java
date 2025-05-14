package inventory;

import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryRepositoryTest {

    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() throws IOException {
        // Initialize the InventoryRepository, which creates its own Inventory
        inventoryRepository = new InventoryRepository();
    }

    @Test
    void testAddProduct() {
        // Arrange
        Product mockProduct = mock(Product.class);
        // Stub the getAssociatedParts method to return a non-null list
        when(mockProduct.getAssociatedParts()).thenReturn(FXCollections.observableArrayList());

        // Act
        inventoryRepository.addProduct(mockProduct);

        // Assert
        ObservableList<Product> products = inventoryRepository.getAllProducts();
        assertTrue(products.contains(mockProduct), "The product should be added to the repository.");
    }

    @Test
    void testLookupProduct() {
        // Arrange
        Product mockProduct = mock(Product.class);
        when(mockProduct.getName()).thenReturn("MockProduct");
        when(mockProduct.getProductId()).thenReturn(1);
        // Stub the getAssociatedParts method to return a non-null list
        when(mockProduct.getAssociatedParts()).thenReturn(FXCollections.observableArrayList());

        // Add the mock product to the repository
        inventoryRepository.addProduct(mockProduct);

        // Act
        Product result = inventoryRepository.lookupProduct("MockProduct");

        // Assert
        assertNotNull(result, "The product should be found.");
        assertEquals("MockProduct", result.getName(), "The product name should match.");
    }
}
