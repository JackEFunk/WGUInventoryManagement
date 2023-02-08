package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
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
import model.Inventory;
import model.Part;
import model.Product;

/**
 * Class for Main screen
 */
public class MainController implements Initializable {

    /**
     * GUI Element
     */
    public TableView<Part> mainPartTable;

    /**
     * GUI Element
     */
    public TableColumn<Part, Integer> mainPartIDCol;

    /**
     * GUI Element
     */
    public TableColumn<Part, String> mainPartNameCol;

    /**
     * GUI Element
     */
    public TableColumn<Part, Integer> mainPartInStockCol;

    /**
     * GUI Element
     */
    public TableColumn<Part, Double> mainPartPriceCol;

    /**
     * GUI Element
     */
    public TableView<Product> mainProductTable;

    /**
     * GUI Element
     */
    public TableColumn<Product, Integer> mainProductIDCol;

    /**
     * GUI Element
     */
    public TableColumn<Product, String> mainProductNameCol;

    /**
     * GUI Element
     */
    public TableColumn<Product, Integer> mainProductInStockCol;

    /**
     * GUI Element
     */
    public TableColumn<Product, Double> mainProductPriceCol;

    /**
     * GUI Element
     */
    public TextField mainPartSearchField;

    /**
     * GUI Element
     */
    public TextField mainProductSearchField;
    private static int modPartIndex;
    private static int modProdIndex;

    private Stage stage;
    private Scene scene;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPartTable.setItems(Inventory.getAllParts());
        mainPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductTable.setItems(Inventory.getAllProducts());
        mainProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProductInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Method to get part to modify
     */
    public static int getPartToModify() {
        return modPartIndex;
    }

    /**
     * Method to get product to modify
     */
    public static int getProductToModify() {
        return modProdIndex;
    }


    /**
     * Loads Add Part TableView
     */
    public void handleMainPartAdd(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddPart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the ModifyPart controller
     */
    public void handleMainPartMod(ActionEvent event) throws IOException {
        Part item = mainPartTable.getSelectionModel().getSelectedItem();
        try {
            modPartIndex = Inventory.getAllParts().indexOf(item);
            // Loading the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
            Parent root = loader.load();
            // Passing the part to modify
            ModifyPartController modPart = loader.getController();
            modPart.passSelectedPart(item);
            // Setting the stage
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("WARNING");
            alert.setContentText("Select product and try again.");
            alert.show();
        }
    }

    /**
     * Method for product add
     */
    public void handleMainProdAdd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddProduct.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for product modify
     */
    public void handleMainProdMod(ActionEvent event) throws IOException {
        Product item = mainProductTable.getSelectionModel().getSelectedItem();
        try {
            modProdIndex = Inventory.getAllProducts().indexOf(item);
            // Loading the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
            Parent root = loader.load();
            // Passing the product to modify
            ModifyProductController modProd = loader.getController();
            modProd.passSelectedProduct(item);
            // Setting the stage
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("WARNING");
            alert.setContentText("Select product and try again.");
            alert.show();
        }
    }

    /**
     * Exits the system
     */
    public void handleExit(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Method for main part search
     */
    public void handleMainPartSearch(ActionEvent event) {
        mainPartTable.getSelectionModel().clearSelection();
        String search = mainPartSearchField.getText();
        ObservableList<Part> results = Inventory.lookUpPart(search);
        try {
            if (results.size() == 0) {
                int idSearch = Integer.parseInt(search);
                Part idResults = Inventory.lookUpPart(idSearch);
                if (idResults.equals(null)) {
                    throw new Exception();
                }
                else {
                    mainPartTable.setItems(Inventory.getAllParts());
                    mainPartTable.getSelectionModel().select(Inventory.lookUpPart(idSearch));
                }
            } else {
                mainPartTable.setItems(results);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("Part does not exist. Press OK to return to the main menu!");
            alert.showAndWait();
            mainPartSearchField.setText("");

        }
    }

    /**
     * Method for main product search
     */
    public void handleMainProdSearch(ActionEvent event) {
        mainProductTable.getSelectionModel().clearSelection();
        String search = mainProductSearchField.getText();
        ObservableList<Product> results = Inventory.lookUpProduct(search);
        try {
            if (results.size() == 0) {
                int idSearch = Integer.parseInt(search);
                Product idResults = Inventory.lookUpProduct(idSearch);
                if (idResults.equals(null)) {
                    throw new Exception();
                } else {
                    mainProductTable.setItems(Inventory.getAllProducts());
                    mainProductTable.getSelectionModel().select(Inventory.lookUpProduct(idSearch));
                }
            } else {
                mainProductTable.setItems(results);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Product Not Found");
            alert.setContentText("Product does not exist. Press OK to return to the main menu!");
            alert.showAndWait();
            mainProductSearchField.setText("");
        }
    }

    /**
     * Method for main part delete
     */
    public void handleMainPartDelete(ActionEvent event) {
        Part item = (Part) mainPartTable.getSelectionModel().getSelectedItem();
        ObservableList<Part> allParts = Inventory.getAllParts();
        if (item == null) {
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING");
            alert.setHeaderText("Deletion Confirmation");
            alert.setContentText("Are you sure you want to delete " + item.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(item);
            }
        }
        mainPartTable.setItems(allParts);
    }

    /**
     * Method for product delete
     */
    public void handleMainProdDelete(ActionEvent event) {
        Product item = (Product) mainProductTable.getSelectionModel().getSelectedItem();
        ObservableList<Product> allProds = Inventory.getAllProducts();
        if (item == null) {
            return;
        } else {
            if (item.getAllAssociatedParts().size() == 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("WARNING");
                alert.setHeaderText("Deletion Confirmation");
                alert.setContentText("Are you sure you want to delete " + item.getName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(item);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Can't Delete");
                alert.setContentText("Products with associated parts can't be deleted.");
                alert.show();
            }
        }
        mainProductTable.setItems(allProds);
    }
}

