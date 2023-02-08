package main;

/**
 * John Funk
 * C482 - Software I
 * Student ID# 010010682
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;

import java.util.Objects;


/**
 * Class for Main
 * FUTURE ENHANCEMENT: A good potential enhancement could be adding a method to ensure a product's price makes sense
 * with its associated parts. One way to do this would be to add an input validation to ensure the sum of the parts'
 * prices is less than the price of the product. Another way to do it would be to automatically calculate the price
 * of the product based on the sum of the parts' prices. A percentage markup could be added if needed.
 */
public class Main extends Application {

    /**
     * Load main FXML and set GUI viewport
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        stage.setTitle("Home");
        stage.setScene(new Scene(root, 900, 350));
        stage.show();
    }

    /**
     * Launch GUI
     */
    public static void main(String[] args) {
        Inventory.addTestData();
        launch(args);
    }
}

