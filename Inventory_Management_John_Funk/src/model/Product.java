package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Class for product
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Method for product
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Method to set ID
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Method to get ID
     */
    public int getId(){
        return id;
    }

    /**
     * Method to set name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Method to get name
     */
    public String getName(){
        return name;
    }

    /**
     * Method to set price
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Method to get price
     */
    public double getPrice(){
        return price;
    }

    /**
     * Method to set stock
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * Method to get stock
     */
    public int getStock(){
        return stock;
    }

    /**
     * Method to set min
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * Method to get min
     */
    public int getMin(){
        return min;
    }

    /**
     * Method to set max
     */
    public void setMax(int max){
        this.max = max;
    }

    /**
     * Method to get max
     */
    public int getMax(){
        return max;
    }

    /**
     * Method to add associated parts
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Method to delete associated part
     */
    public boolean deleteAssociatedPart(Part currPart){
        if(associatedParts.contains(currPart)){
            associatedParts.remove(currPart);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Method to get all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
