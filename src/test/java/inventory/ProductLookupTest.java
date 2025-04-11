package inventory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class ProductLookupTest {

    // Mock Product class
    static class Product {
        private int productId;
        private String name;
        private double price;
        private int quantity;
        private int categoryId;
        private int supplierId;
        private String description;

        public Product(int productId, String name, double price, int quantity, int categoryId, int supplierId, String description) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.categoryId = categoryId;
            this.supplierId = supplierId;
            this.description = description;
        }

        public int getProductId() {
            return productId;
        }

        public String getName() {
            return name;
        }
    }

    // Mock product list
    private List<Product> products = new ArrayList<>();

    // Function to test
    public Product lookupProduct(String searchItem) {
        boolean isFound = false;
        Product targetProduct = null;
        int i;
        for (i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getName().contains(searchItem) || (p.getProductId() + "").equals(searchItem)) {
                targetProduct = p;
                isFound = true;
            }
        }
        if (!isFound) {
            return new Product(0, null, 0.0, 0, 0, 0, null);
        }
        return targetProduct;
    }

    // Test case F02_TC01: No match found
    @Test
    public void testNoMatchFound() {
        products.clear();
        products.add(new Product(1, "ProductA", 10.0, 5, 1, 1, "DescriptionA"));
        products.add(new Product(2, "ProductB", 15.0, 3, 2, 2, "DescriptionB"));

        Product result = lookupProduct("Produs");
        assertEquals(0, result.getProductId());
        assertNull(result.getName());
    }

    // Test case F02_TC02: Partial match but no exact match
    @Test
    public void testPartialMatchNoExactMatch() {
        products.clear();
        products.add(new Product(1, "ProductA", 10.0, 5, 1, 1, "DescriptionA"));
        products.add(new Product(2, "ProductB", 15.0, 3, 2, 2, "DescriptionB"));

        Product result = lookupProduct("Produs");
        assertEquals(0, result.getProductId());
        assertNull(result.getName());
    }

    // Test case F02_TC03: Exact match by name
    @Test
    public void testExactMatchByName() {
        products.clear();
        products.add(new Product(1, "Produs", 20.0, 10, 1, 1, "DescriptionProdus"));
        products.add(new Product(2, "ProductB", 15.0, 3, 2, 2, "DescriptionB"));

        Product result = lookupProduct("Produs");
        assertEquals(1, result.getProductId());
        assertEquals("Produs", result.getName());
    }

    // Test case F02_TC04: Exact match by product ID
    @Test
    public void testExactMatchById() {
        products.clear();
        products.add(new Product(1, "ProductA", 10.0, 5, 1, 1, "DescriptionA"));
        products.add(new Product(2, "ProductB", 15.0, 3, 2, 2, "DescriptionB"));

        Product result = lookupProduct("2");
        assertEquals(2, result.getProductId());
        assertEquals("ProductB", result.getName());
    }
}
