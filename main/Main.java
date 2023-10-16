package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        //add parts and stuff for testing
        InHouse testPart1 = new InHouse(Inventory.getPartIdCount(),"wheel", 3.99, 2, 1, 10, 33);
        InHouse testPart2 = new InHouse(Inventory.getPartIdCount(), "tire", 27.88, 16, 4, 32, 55);
        InHouse testPart3 = new InHouse(Inventory.getPartIdCount(), "caliper", 22.48, 12, 2, 44, 88);
        InHouse testPart4 = new InHouse(Inventory.getPartIdCount(), "tie rod", 32.33, 2, 1, 10, 98);
        InHouse testPart5 = new InHouse(Inventory.getPartIdCount(), "shock absorber", 33.98, 4, 2, 12, 101);
        Outsourced testPart6 = new Outsourced(Inventory.getPartIdCount(), "lug nut", 1.99, 44, 12, 120, "AmWay");
        Outsourced testPart7 = new Outsourced(Inventory.getPartIdCount(), "lug nut", 1.99, 44, 12, 120, "Done Right");
        Inventory.addPart(testPart1);
        Inventory.addPart(testPart2);
        Inventory.addPart(testPart3);
        Inventory.addPart(testPart4);
        Inventory.addPart(testPart5);
        Inventory.addPart(testPart6);
        Inventory.addPart(testPart7);

        Product testProduct1 = new Product(Inventory.getProductIdCount(), "Car", 3994.99, 12, 2, 100);
        Product testProduct2 = new Product(Inventory.getProductIdCount(),"boat", 2989.09, 2, 1,10);
        testProduct1.addAssociatedPart(testPart1);
        testProduct1.addAssociatedPart(testPart2);
        testProduct1.addAssociatedPart(testPart3);
        testProduct2.addAssociatedPart(testPart4);
        testProduct2.addAssociatedPart(testPart5);
        testProduct2.addAssociatedPart(testPart6);
        Inventory.addProduct(testProduct1);
        Inventory.addProduct(testProduct2);

        System.out.println(testProduct1.getAllAssociatedParts());
        System.out.println(testProduct2.getAllAssociatedParts());

        //Load the main thing
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setTitle("Inventory Management");
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();

    }


    public static void main(String[] args){
        launch(args);
    }
}
