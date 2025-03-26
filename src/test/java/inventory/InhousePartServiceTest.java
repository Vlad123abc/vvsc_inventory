package inventory;

import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InhousePartServiceTest {

    private InventoryService service;

    @BeforeEach
    void setUp() throws IOException {
        service = new InventoryService(new InventoryRepository());
    }

    @Test
    @DisplayName("Test valid input (ECP) - all parameters valid")
    void testAddInhousePart_ValidInput() {
        // Arrange
        String name = "N";
        double price = 40.0;
        int inStock = 20;
        int min = 10;
        int max = 40;
        int partDynamicValue = 123;

        // Act & Assert
        assertDoesNotThrow(() -> service.addInhousePart(name, price, inStock, min, max, partDynamicValue));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-3.0, 0.0}) // Invalid prices
    @DisplayName("Test invalid input (ECP) - price is invalid")
    void testAddInhousePart_InvalidPrice(double price) {
        // Arrange
        String name = "N";
        int inStock = 20;
        int min = 10;
        int max = 40;
        int partDynamicValue = 123;

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                service.addInhousePart(name, price, inStock, min, max, partDynamicValue)
        );
        assertTrue(exception.getMessage().contains("The price must be greater than 0. "));
    }

    @RepeatedTest(3)
    @DisplayName("Test boundary value (BVA) - inStock equals min")
    void testAddInhousePart_StockEqualsMin() {
        // Arrange
        String name = "PartA";
        double price = 100.0;
        int inStock = 5; // Boundary value
        int min = 5;
        int max = 15;
        int partDynamicValue = 123;

        // Act & Assert
        assertDoesNotThrow(() -> service.addInhousePart(name, price, inStock, min, max, partDynamicValue));
    }

    @Test
    @DisplayName("Test boundary value (BVA) - inStock below min")
    void testAddInhousePart_StockBelowMin() {
        // Arrange
        String name = "PartA";
        double price = 40.0;
        int inStock = 5; // Invalid boundary value
        int min = 10;
        int max = 40;
        int partDynamicValue = 123;

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                service.addInhousePart(name, price, inStock, min, max, partDynamicValue)
        );
        assertTrue(exception.getMessage().contains("Inventory level is lower than minimum value. "));
    }

    @Test
    @DisplayName("Test boundary value (BVA) - name at max length")
    void testAddInhousePart_NameMaxLength() {
        // Arrange
        StringBuilder name = new StringBuilder();
        for(int i=0;i<255;i++)
            name.append("a");
        double price = 40.0;
        int inStock = 20;
        int min = 10;
        int max = 40;
        int partDynamicValue = 123;

        // Act & Assert
        assertDoesNotThrow(() -> service.addInhousePart(String.valueOf(name), price, inStock, min, max, partDynamicValue));
    }

    @Test
    @DisplayName("Test boundary value (BVA) - name over max length")
    void testAddInhousePart_NameOverMaxLength() {
        // Arrange
        StringBuilder name = new StringBuilder();
        for(int i=0;i<256;i++)
            name.append("a");
        double price = 40.0;
        int inStock = 20;
        int min = 10;
        int max = 40;
        int partDynamicValue = 123;

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                service.addInhousePart(String.valueOf(name), price, inStock, min, max, partDynamicValue)
        );
        assertTrue(exception.getMessage().contains("Name too large."));    }

    @Disabled("Test disabled temporarily for debugging purposes")
    @Test
    @DisplayName("Test invalid input (ECP) - name is empty")
    void testAddInhousePart_InvalidName() {
        // Arrange
        String name = ""; // Invalid
        double price = 100.0;
        int inStock = 10;
        int min = 5;
        int max = 15;
        int partDynamicValue = 123;

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                service.addInhousePart(name, price, inStock, min, max, partDynamicValue)
        );
        assertTrue(exception.getMessage().contains("A name has not been entered. "));
    }
}
