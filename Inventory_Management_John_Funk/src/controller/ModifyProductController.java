package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Class for modify product
 */
public class ModifyProductController implements Initializable {

    /**
     * GUI Element
     */
    public TableView<Part> modProdPartsTable;

    /**
     * GUI Element
     */
    public TableColumn<Part, Integer> modProdIDCol;

    /**
     * GUI Element
     */
    public TableColumn<Part, String> modProdNameCol;

    /**
     * GUI Element
     */
    public TableColumn<Part, Integer> modProdInStockCol;

    /**
     * GUI Element
     */
    public TableColumn<Part, Double> modProdPriceCol;

    /**
     * GUI Element
     */
    public TableView<Part> modProdAssocPartsTable;

    /**
     * GUI Element
     */
    public TableColumn<Part, Integer> modProdAssocPartsIDCol;

    /**
     * GUI Element
     */
    public TableColumn<Part, String> modProdAssocPartsNameCol;

    /**
     * GUI Element
     */
    public TableColumn<Part, Integer> modProdAssocPartsInStockCol;

    /**
     * GUI Element
     */
    public TableColumn<Part, Double> modProdAssocPartsPriceCol;

    /**
     * GUI Element
     */
    public TextField modProdId;

    /**
     * GUI Element
     */
    public TextField modProdName;

    /**
     * GUI Element
     */
    public TextField modProdInStock;

    /**
     * GUI Element
     */
    public TextField modProdPrice;

    /**
     * GUI Element
     */
    public TextField modProdMin;

    /**
     * GUI Element
     */
    public TextField modProdMax;

    /**
     * GUI Element
     */
    public TextField modProdSearch;

    /**
     * GUI Element
     */
    public Button modProdAddButton;

    /**
     * GUI Element
     */
    public Button modProdRemoveButton;

    /**
     * GUI Element
     */
    public Button modProdSaveButton;

    /**
     * GUI Element
     */
    public Button modProdCancelButton;

    Stage stage;
    Scene scene;
    private Product currProd;
    int prodIndex = MainController.getProductToModify();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modProdPartsTable.setItems(Inventory.getAllParts());
        modProdIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Returns the user to the main screen
     */
    public void returnToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to display associated parts from product
     */
    public void passSelectedProduct(Product item) {
        this.currProd = item;
        modProdAssocPartsTable.setItems(currProd.getAllAssociatedParts());
        modProdAssocPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdAssocPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdAssocPartsInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdAssocPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modProdId.setText(String.valueOf(currProd.getId()));
        modProdName.setText(currProd.getName());
        modProdInStock.setText(String.valueOf(currProd.getStock()));
        modProdPrice.setText(String.valueOf(currProd.getPrice()));
        modProdMax.setText(String.valueOf(currProd.getMax()));
        modProdMin.setText(String.valueOf(currProd.getMin()));
    }

    /**
     * Method for saving product
     * RUNTIME ERROR: Got errors with inventory, min and max values. Used if statements to check those inputs.
     */
    public void handleModProdSave(ActionEvent event) throws IOException {
        try {
            String name = modProdName.getText();
            int stock = Integer.parseInt(modProdInStock.getText());
            double price = Double.parseDouble(modProdPrice.getText());
            int max = Integer.parseInt(modProdMax.getText());
            int min = Integer.parseInt(modProdMin.getText());
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Inventory Issue");
                alert.setContentText("Maximum must be greater than minimum.");
                alert.showAndWait();
            }
            else if (max < stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Inventory Issue");
                alert.setContentText("Inventory higher than maximum. Please check inputs.");
                alert.showAndWait();
            }
            else if (min > stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Inventory Issue");
                alert.setContentText("Inventory lower than minimum. Please check inputs.");
                alert.showAndWait();
            }
            else{
                currProd.setName(name);
                currProd.setPrice(price);
                currProd.setStock(stock);
                currProd.setMax(max);
                currProd.setMin(min);
                Inventory.updateProduct(prodIndex, currProd);
                returnToMainMenu(event);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Inputs");
            alert.setContentText("Ensure values are appropriate format.");
        }
    }

    /**
     * Method for product search
     */
    public void handleModProdSearch(ActionEvent event) {
        String search = modProdSearch.getText();
        ObservableList<Part> results = Inventory.lookUpPart(search);
        try {
            if (results.size() == 0) {
                int idSearch = Integer.parseInt(search);
                Part idResult = Inventory.lookUpPart(idSearch);
                if (idResult.equals(null)) {
                    throw new Exception();
                } else {
                    modProdPartsTable.setItems(Inventory.getAllParts());
                    modProdPartsTable.getSelectionModel().select(idResult);
                }
            } else {
                modProdPartsTable.setItems(results);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("Press OK to return to Add Product page.");
            alert.showAndWait();
            modProdSearch.setText("");
        }
    }

    /**
     * Method to add product
     */
    public void handleModProdAdd(ActionEvent event) {
        Part item = modProdPartsTable.getSelectionModel().getSelectedItem();
        if(item != null){
            currProd.addAssociatedPart(item);}
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Empty Selection");
            alert.setContentText("Select part to add.");
            alert.showAndWait();
        }
    }

    /**
     * Method for removing product
     */
    public void handleModProdRemove(ActionEvent event) {
        Part item = modProdAssocPartsTable.getSelectionModel().getSelectedItem();
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
                    currProd.deleteAssociatedPart(item);
                }
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Empty Selection");
            alert.setContentText("Select part to remove.");
            alert.showAndWait();
        }
    }

    /**
     * Method to return to main screen on cancel
     */
    public void handleModProdCancel(ActionEvent event) throws IOException { returnToMainMenu(event); }

}

