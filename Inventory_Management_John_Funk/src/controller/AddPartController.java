package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class for AddPart screen
 */
public class AddPartController implements Initializable {

    /**
     * GUI Element
     */
    public Button addPartCancel;

    /**
     * GUI Element
     */
    public Button addPartSaveButton;

    /**
     * GUI Element
     */
    private Stage stage;

    /**
     * GUI Element
     */
    private Scene scene;

    /**
     * GUI Element
     */
    public RadioButton addPartInHouseButton;

    /**
     * GUI Element
     */
    public RadioButton addPartOutsourcedButton;

    /**
     * GUI Element
     */
    public TextField addPartName;

    /**
     * GUI Element
     */
    public TextField addPartInStock;

    /**
     * GUI Element
     */
    public TextField addPartPrice;

    /**
     * GUI Element
     */
    public TextField addPartMax;

    /**
     * GUI Element
     */
    public TextField addPartMin;

    /**
     * GUI Element
     */
    public TextField addPartToggle;

    /**
     * GUI Element
     */
    public Label addPartToggleLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Method for input validation
     * RUNTIME ERROR: Got errors with inventory, min and max values. Used if statements to check those inputs.
     */
    public void handleSave(ActionEvent event) throws IOException {
        try {
            int id = Inventory.getAllParts().size() + Inventory.partIdGenerator.addAndGet(2) + 1000;
            String name = addPartName.getText();
            int stock = Integer.parseInt(addPartInStock.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int max = Integer.parseInt(addPartMax.getText());
            int min = Integer.parseInt(addPartMin.getText());

            // Checks for an inventory input error
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
                alert.setContentText("Inventory is higher than maximum. Please check inputs.");
                alert.showAndWait();
            }
            else if (min > stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Inventory Issue");
                alert.setContentText("Inventory is lower than minimum. Please check inputs.");
                alert.showAndWait();
            }
            else {
                if (addPartOutsourcedButton.isSelected()) {
                    String company = addPartToggle.getText();
                    Part part = new Outsourced(id, name, price, stock, min, max, company);
                    Inventory.addPart(part);
                } else if (addPartInHouseButton.isSelected()) {
                    int machineId = Integer.parseInt(addPartToggle.getText());
                    Part part = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(part);
                }
                returnToMainMenu(event);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Incorrect Inputs");
            alert.setContentText("Ensure values are appropriate format and either " +
                    "In House or Outsourced is selected!");
            alert.showAndWait();
        }
    }

    /**
     * Toggles label for Machine ID
     */
    public void handleAddPartInHouse(ActionEvent event) {
        addPartToggleLabel.setText("Machine ID");
    }

    /**
     * Toggles label for Company Name
     */
    public void handleAddPartOutsourced(ActionEvent event){
        addPartToggleLabel.setText("Company Name");
    }

    /**
     * Returns to main screen after cancel clicked
     */
    public void handleCancel(ActionEvent event) throws IOException{ returnToMainMenu(event); }


    /**
     * Method for returning to the main menu
     */
    public void returnToMainMenu(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
