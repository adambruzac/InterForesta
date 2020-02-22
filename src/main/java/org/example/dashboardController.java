package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

    @FXML
    private Label lbl_numberProducts;


    public void showProducts(){

    try {
        App.setRoot("Products");
    } catch (IOException e) {
        e.printStackTrace();
    }

}

    public void showCategories(){

        try {
            App.setRoot("addCategory");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void numberProducts(){
        int rowNumber = 0;
        db_connection connectionClass=new db_connection();

        try {
            Connection connection=connectionClass.getConnection();
            String sql = "SELECT count(*) FROM products1";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                rowNumber = resultSet.getInt("count(*)");

            }
        } catch (Exception e){

            System.out.println(e);

        }
        //lbl_numberProducts.setText(Integer.toString(rowNumber));
        System.out.println(rowNumber);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberProducts();

    }
}
