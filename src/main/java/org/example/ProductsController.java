package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsController {

    @FXML
    public TextField txt_productID;
    public TextField txt_productName;
    public TextField txt_productPrice;
    public Button btn_addProduct;
    public Button btn_printQR;
    public TableView tbl_prodTable;
    public TableColumn column_prodID;
    public TableColumn column_prodName;
    public ImageView img_qr;
    public Button btnlogout;
    public TextArea txt_productDetails;
    public ComboBox comboBox_Categories;
    private ObservableList<CategoriesController> data;


    db_connection connectionClass=new db_connection();
    Connection connection=connectionClass.getConnection();

    @FXML
    private void addProduct(){
        String sql = "INSERT INTO products(product_id, product_name, product_price, product_stock, product_details)" + " VALUES (?, ?, ?, ?, ?)";
        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString (1, txt_productID.getText());
            preparedStmt.setString (2, txt_productName.getText());
            // execute the preparedstatement
            preparedStmt.execute();
            System.out.println("Category added to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

@FXML
    public void loadCategories(){
        String sqlStationName = " select * from categories ";
        try {

            data = FXCollections.observableArrayList();
            connection = (Connection) connectionClass.getConnection();
            PreparedStatement pstStn = connection.prepareStatement(sqlStationName);
            ResultSet stnRS = pstStn.executeQuery(sqlStationName);
            while (stnRS.next()) {
                comboBox_Categories.getItems().add(stnRS.getString("category_name"));            }
            stnRS.close();
            pstStn.close();
            connection.close();

        } catch (SQLException ex) {
            System.err.println("ERR" + ex);
        }



}


    public void goBack(ActionEvent actionEvent) {

        try {
            App.setRoot("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}







