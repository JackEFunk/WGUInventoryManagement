package model;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for Inventory
 */
public class Inventory {

    /**
     * Part ID Generation
     */
    public static AtomicInteger partIdGenerator = new AtomicInteger();

    /**
     * Product ID Generation
     */
    public static AtomicInteger productIdGenerator = new AtomicInteger();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Method to add part
     */
    public static void addPart(Part newPart){ allParts.add(newPart); }

    /**
     * Method to add product
     */
    public static void addProduct(Product newProduct){ allProducts.add(newProduct); }

    /**
     * Method to search parts by ID
     */
    public static Part lookUpPart(int partId){
        for(Part part: allParts){
            if(part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    /**
     * Method to search parts by name
     */
    public static ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> partResults = FXCollections.observableArrayList();
        for(Part part : allParts){
            if(part.getName().toLowerCase().contains(partName.toLowerCase())){
                partResults.add(part);
            }
        }
        return partResults;
    }

    /**
     * Sample data for testing
     */
    public static void addTestData() {
        InHouse partInhouseOne = new InHouse(1, "Part InHouse One", 9.99, 10, 5, 25, 100);
        InHouse partInhouseTwo = new InHouse(2, "Part Inhouse 2", 49.99, 20, 4, 50, 105);
        Outsourced partOutsourcedOne = new Outsourced(3, "partOutsourced One", 99.99, 10, 2, 12, "123 Company");
        Outsourced partOutsourcedTwo = new Outsourced(4, "Part Outsourced Two", 9.99, 5, 1, 8, "Company ABC");
        allParts.add(partInhouseOne);
        allParts.add(partInhouseTwo);
        allParts.add(partOutsourcedOne);
        allParts.add(partOutsourcedTwo);

        Product productOne = new Product(1, "Product One", 25.00, 10, 1, 10);
        Product productTwo = new Product(2, "ProductTwo", 12.00, 4, 1, 10);
        Product productThree = new Product(3, "product3", 200.00, 12, 1, 25);
        allProducts.add(productOne);
        allProducts.add(productTwo);
        allProducts.add(productThree);
    }

    /**
     * Method to update part
     */
    public static void updatePart(int index, Part p){
        allParts.set(index, p);
    }

    /**
     * Method to delete part
     */
    public static boolean deletePart(Part p) {
        for (Part part : allParts) {
            if (part.getId() == p.getId()) {
                allParts.remove(p);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get Parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Method to lookup products by ID
     */
    public static Product lookUpProduct(int productId){
        for(Product product: allProducts){
            if(product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    /**
     * Method to lookup products by name
     */
    public static ObservableList<Product> lookUpProduct(String productName){
        ObservableList<Product> prodResults = FXCollections.observableArrayList();
        for(Product product : allProducts){
            if(product.getName().toLowerCase().contains(productName.toLowerCase())) {
                prodResults.add(product);
            }
        }
        return prodResults;
    }

    /**
     * Method to update product
     */
    public static void updateProduct(int index, Product newProduct){ allProducts.set(index, newProduct); }

    /**
     * Method to delete product
     */
    public static boolean deleteProduct(Product newProduct) {
        for (Product prod : allProducts) {
            if (prod.getId() == newProduct.getId()) {
                allProducts.remove(newProduct);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get all products
     */
    public static ObservableList<Product> getAllProducts(){ return allProducts; }


}
