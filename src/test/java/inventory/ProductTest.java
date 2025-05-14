package inventory;

import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testIsValidProduct() {
        // Arrange
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(new OutsourcedPart(1, "Part1", 10.0, 5, 1, 10, "CompanyA"));
        parts.add(new OutsourcedPart(2, "Part2", 15.0, 3, 1, 5, "CompanyB"));
        String errorMessage = "";

        // Act
        String result = Product.isValidProduct("TestProduct", 30.0, 5, 1, 10, parts, errorMessage);

        // Assert
        assertEquals("", result, "Product should be valid with no error messages.");
    }

    @Test
    void testAddAndRemoveAssociatedPart() {
        // Arrange
        ObservableList<Part> parts = FXCollections.observableArrayList();
        Product product = new Product(1, "TestProduct", 50.0, 10, 1, 20, parts);
        OutsourcedPart part = new OutsourcedPart(1, "Part1", 10.0, 5, 1, 10, "CompanyA");

        // Act
        product.addAssociatedPart(part);
        boolean partAdded = product.getAssociatedParts().contains(part);

        product.removeAssociatedPart(part);
        boolean partRemoved = !product.getAssociatedParts().contains(part);

        // Assert
        assertTrue(partAdded, "Part should be added to the product.");
        assertTrue(partRemoved, "Part should be removed from the product.");
    }
}
