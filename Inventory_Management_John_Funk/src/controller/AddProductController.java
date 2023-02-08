package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

/**
 * Class for AddProduct Screen
 */
public class AddProductController implements Initializable {

    /**
     * GUI Element
     */
    public TextField addProdName;

    /**
     * GUI Element
     */
    public TextField addProdMax;

    /**
     * GUI Element
     */
    public TextField addProdMin;

    /**
     * GUI Element
     */
    public TextField addProdInStock;

    /**
     * GUI Element
     */
    public TextField addProdPrice;

    /**
     * GUI Element
     */
    public TextField addProdSearch;

    /**
     * GUI Element
     */
    public Button addProdRemoveButton;

    /**
     * GUI Element
     */
    public Button addProdSaveButton;

    /**
     * GUI Element
     */
    public Button addProdCancelButton;

    /**
     * GUI Element
     */
    @FXML
    private TableView<Part> addProdPartsTable;

    /**
     * GUI Element
     */
    @FXML
    private TableColumn<Part, Integer> addProdPartsIDCol;

    /**
     * GUI Element
     */
    @FXML
    private TableColumn<Part, String> addProdPartsNameCol;

    /**
     * GUI Element
     */
    @FXML
    private TableColumn<Part, Integer> addProdPartsInStockCol;

    /**
     * GUI Element
     */
    @FXML
    private TableColumn<Part, Double> addProdPartsPriceCol;

    /**
     * GUI Element
     */
    @FXML
    private TableView<Part> addProdAssocPartsTable;

    /**
     * GUI Element
     */
    @FXML
    private TableColumn<Part, Integer> addProdAssocPartsIDCol;

    /**
     * GUI Element
     */
    @FXML
    private TableColumn<Part, String> addProdAssocPartsNameCol;

    /**
     * GUI Element
     */
    @FXML
    private TableColumn<Part, Integer> addProdAssocPartsInStockCol;

    /**
     * GUI Element
     */
    @FXML
    private TableColumn<Part, Double> addProdAssocPartsPriceCol;

    /**
     * GUI Element
     */
    public Button addButton;

    /**
     * GUI Element
     */
    public Button removeButton;

    /**
     * GUI Element
     */
    public Button saveButton;

    Stage stage;
    Scene scene;
    ObservableList<Part> currAssocParts = FXCollections.observableArrayList();

    /**
     * Initializes the tables for allParts and currParts.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addProdPartsTable.setItems(Inventory.getAllParts());
        addProdPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdPartsInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProdAssocPartsTable.setItems(currAssocParts);
        addProdAssocPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdAssocPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdAssocPartsInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdAssocPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Returns the user to main screen
     */
    public void returnToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for input validation
     * RUNTIME ERROR: Got errors with inventory, min and max values. Used if statements to check those inputs.
     */
    public void handleAddProdSave(ActionEvent event) throws IOException {
        try {
            String name = addProdName.getText();
            int stock = Integer.parseInt(addProdInStock.getText());
            double price = Double.parseDouble(addProdPrice.getText());
            int max = Integer.parseInt(addProdMax.getText());
            int min = Integer.parseInt(addProdMin.getText());
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Inventory Issue");
                alert.setContentText("Maximum MUST be GREATER than minimum!");
                alert.showAndWait();
            }
            else if (max < stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Inventory Issue");
                alert.setContentText("Product inventory is higher than the maximum allowed. Please check your inputs.");
                alert.showAndWait();
            }
            else if (min > stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Inventory Issue");
                alert.setContentText("Product inventory is lower than the minimum allowed. Please check your inputs.");
                alert.showAndWait();
            }
            else {
                int id = Inventory.getAllProducts().size() + Inventory.productIdGenerator.addAndGet(3) + 1000;
                Product newProduct = new Product(id, name, price, stock, min, max);
                Inventory.addProduct(newProduct);

                for (Part part : currAssocParts) {
                    newProduct.addAssociatedPart(part);
                }
                returnToMainMenu(event);
            }
        }
        catch (Exception e) {
            addProdName.setText("");
            addProdInStock.setText("");
            addProdPrice.setText("");
            addProdMax.setText("");
            addProdMin.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Incorrect Inputs");
            alert.setContentText("Be sure that your values are in the appropriate format!");
            alert.showAndWait();
        }
    }

    /**
     * Adds selected item to current product list
     */
    public void handleAddProdAdd(ActionEvent event) {
        Part selection = addProdPartsTable.getSelectionModel().getSelectedItem();
        if(selection != null)
            currAssocParts.add(selection);
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Empty Selection");
            alert.setContentText("You have not selected a part to add!");
            alert.showAndWait();
        }
    }

    /**
     * Method for product removal
     */
    public void handleAddProdRemove(ActionEvent event){
        Part item = addProdAssocPartsTable.getSelectionModel().getSelectedItem();
        try {
            if (item == null)
                throw new Exception();
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("WARNING");
                alert.setHeaderText("Part Removal Confirmation");
                alert.setContentText("Are you sure you want to remove " + item.getName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    currAssocParts.remove(item);
                }
            }
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Empty Selection");
            alert.setContentText("No part selected.");
            alert.showAndWait();
        }
    }

    /**
     * Method for add product search
     */
    public void handleAddProdSearch(ActionEvent event){
        addProdPartsTable.getSelectionModel().clearSelection();
        String search = addProdSearch.getText();
        ObservableList<Part> results = Inventory.lookUpPart(search);
        try{
            if(results.size() == 0){
                int idSearch = Integer.parseInt(search);
                Part idResult = Inventory.lookUpPart(idSearch);
                if(idResult.equals(null)){
                    throw new Exception();
                }
                else{
                    addProdPartsTable.setItems(Inventory.getAllParts());
                    addProdPartsTable.getSelectionModel().select(idResult);
                }
            }
            else{
                addProdPartsTable.setItems(results);
            }
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("Part does not exist. Press OK to return to add product page");
            alert.showAndWait();
            addProdSearch.setText("");
        }
    }

    /**
     * Returns user to main screen when cancel clicked
     */
    public void handleAddProdCancel(ActionEvent event) throws IOException{ returnToMainMenu(event); }

}

