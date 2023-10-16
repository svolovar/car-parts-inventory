package model;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for searching and manipulating parts and products
 * @author Steven Volovar
 */

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partIdCount = 0;
    private static int productIdCount = 0;

    public Inventory() {

    }

    /**
     * @param newPart part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId id to search
     * @return search results based on id
     */
    public static Part lookupPart(int partId) {
        for (int i = 0; i < allParts.size(); i++) {
            Part p = allParts.get(i);
            if (p.getId() == partId) {
                return p;
            }
        }
        return null;
    }

    /**
     * @param productId id to search
     * @return search results based on id
     */
    public static Product lookupProduct(int productId) {
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);
            if (p.getId() == productId) {
                return p;
            }
        }
        return null;
    }

    /**
     * @param partName name or partial name to search
     * @return parts containing search string
     */
    public static ObservableList<Part> lookupPart(String partName) {
        System.out.println("Searching for parts");
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part p : allParts) {
            if (p.getName().contains(partName)) {
                namedParts.add(p);
            }
        }
        return namedParts;
    }

    /**
     *
     * @param productName name or partial name to search
     * @return products containing search string
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        System.out.println("Searching for products");
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product p : allProducts) {
            if (p.getName().contains(productName)) {
                namedProducts.add(p);
            }
        }
        return namedProducts;
    }

    /**
     *
     * @param index
     * @param selectedPart
     */
    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     *
     * @param index
     * @param newProduct
     */
    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     *
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /**
     *
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     *
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     *
     * @return
     */
    public static int getPartIdCount() {
        partIdCount++;
        return partIdCount;
    }

    /**
     *
     * @return
     */
    public static int getProductIdCount() {
        productIdCount++;
        return productIdCount;
    }
}


