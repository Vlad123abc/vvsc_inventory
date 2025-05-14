package inventory;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceTest_mockRepo_mockProduct {
    private InventoryRepository mockRepository;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        // Mock the repository
        mockRepository = mock(InventoryRepository.class);

        // Initialize the service with the mocked repository
        inventoryService = new InventoryService(mockRepository);
    }

    @Test
    void testAddInhousePart() {
        // Arrange
        String name = "TestPart";
        double price = 50.0;
        int inStock = 10;
        int min = 1;
        int max = 20;
        int machineId = 123;

        // Act
        inventoryService.addInhousePart(name, price, inStock, min, max, machineId);

        // Assert
        verify(mockRepository, times(1)).addPart(any(InhousePart.class));
    }

    @Test
    void testLookupPart() {
        // Arrange
        Part mockPart = mock(Part.class);
        when(mockPart.getName()).thenReturn("MockPart");
        when(mockRepository.lookupPart("MockPart")).thenReturn(mockPart);

        // Act
        Part result = inventoryService.lookupPart("MockPart");

        // Assert
        assertNotNull(result, "The part should be found.");
        assertEquals("MockPart", result.getName(), "The part name should match.");
        verify(mockRepository, times(1)).lookupPart("MockPart");
    }
}
