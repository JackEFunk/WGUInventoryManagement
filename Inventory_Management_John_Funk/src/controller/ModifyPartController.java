package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class for modify part screen
 */
public class ModifyPartController implements Initializable {

    /**
     * GUI Element
     */
    public RadioButton modPartInHouseButton;

    /**
     * GUI Element
     */
    public RadioButton modPartOutsourcedButton;

    /**
     * GUI Element
     */
    public TextField modPartToggle;

    /**
     * GUI Element
     */
    public Label modPartToggleText;

    /**
     * GUI Element
     */
    public TextField modPartId;

    /**
     * GUI Element
     */
    public TextField modPartName;

    /**
     * GUI Element
     */
    public TextField modPartInStock;

    /**
     * GUI Element
     */
    public TextField modPartPrice;

    /**
     * GUI Element
     */
    public TextField modPartMax;

    /**
     * GUI Element
     */
    public TextField modPartMin;

    /**
     * GUI Element
     */
    public Button modPartCancel;

    /**
     * GUI Element
     */
    public Button modPartSaveButton;

    int index = MainController.getPartToModify();
    Stage stage;
    Scene scene;
    private Part currentPart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){}

    /**
     * Method to pass parts from main controller
     */
    public void passSelectedPart(Part part) {
        this.currentPart = part;
        modPartId.setText(String.valueOf(currentPart.getId()));
        modPartName.setText(String.valueOf(currentPart.getName()));
        modPartInStock.setText(String.valueOf(currentPart.getStock()));
        modPartPrice.setText(String.valueOf(currentPart.getPrice()));
        modPartMax.setText(String.valueOf(currentPart.getMax()));
        modPartMin.setText(String.valueOf(currentPart.getMin()));
        if (currentPart instanceof InHouse) {
            modPartToggle.setText(String.valueOf(((InHouse) currentPart).getMachineId()));
            modPartInHouseButton.setSelected(true);
            modPartToggleText.setText("Machine ID");
        }
        if (currentPart instanceof Outsourced) {
            modPartToggle.setText(String.valueOf(((Outsourced) currentPart).getCompanyName()));
            modPartOutsourcedButton.setSelected(true);
            modPartToggleText.setText("Company Name");
        }
    }

    /**
     * Toggles the label for InHouse
     */
    public void handleModPartInHouse(ActionEvent event){
        modPartToggleText.setText("Machine ID");
    }

    /**
     * Toggles the label for Outsourced
     */
    public void handleModPartOutsourced(ActionEvent event){
        modPartToggleText.setText("Company Name");
    }

    /**
     * Method to validate part info
     * RUNTIME ERROR: Got errors with inventory, min and max values. Used if statements to check those inputs.
     */
    public void handleModPartSave(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modPartId.getText());
            String name = modPartName.getText();
            int stock = Integer.parseInt(modPartInStock.getText());
            double price = Double.parseDouble(modPartPrice.getText());
            int max = Integer.parseInt(modPartMax.getText());
            int min = Integer.parseInt(modPartMin.getText());
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
            else {
                if (modPartOutsourcedButton.isSelected() && currentPart instanceof InHouse) {
                    String company = modPartToggle.getText();
                    currentPart = new Outsourced(id, name, price, stock, min, max, company);
                } else if (modPartInHouseButton.isSelected() && currentPart instanceof Outsourced) {
                    int machineId = Integer.parseInt(modPartToggle.getText());
                    currentPart = new InHouse(id, name, price, stock, min, max, machineId);
                } else {
                    currentPart.setName(name);
                    currentPart.setPrice(price);
                    currentPart.setStock(stock);
                    currentPart.setMax(max);
                    currentPart.setMin(min);
                    if (currentPart instanceof InHouse) {
                        int machineId = Integer.parseInt(modPartToggle.getText());
                        ((InHouse) currentPart).setMachineId(machineId);
                    } else {
                        String company = modPartToggle.getText();
                        ((Outsourced) currentPart).setCompanyName(company);
                    }
                }
                Inventory.updatePart(index, currentPart);
                returnToMainMenu(event);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Inputs");
            alert.setContentText("Ensure values are in appropriate format and" +
                    " either In House or Outsourced is selected!");
            alert.showAndWait();
        }
    }

    /**
     * Returns the user to main menu
     */
    public void handleModPartCancel(ActionEvent event) throws IOException{
        returnToMainMenu(event);
    }

    /**
     * Returns the user to main screen
     */
    public void returnToMainMenu(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
