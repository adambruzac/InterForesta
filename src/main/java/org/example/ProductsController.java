package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    @FXML
    public TextField txt_productID;
    public TextField txt_productName;
    public TextField txt_productPrice;
    public Button btn_addProduct;
    public Button btn_printQR;
    public ImageView img_qr;
    public Button btnlogout;
    public TextArea txt_productDetails;
    public ComboBox comboBox_Categories;
    private ObservableList<ProductsController> data ;
    public TableView<ProductsController> tbl_prodTable;
    public TableColumn<ProductsController, String> column_prodID;
    public TableColumn<ProductsController, String> column_prodName;
    public TableColumn<ProductsController, String> column_description;




    db_connection connectionClass=new db_connection();

    @FXML
    private void addProduct(){
        String sql = "INSERT INTO products(product_id, product_name, product_price, product_details, catID)" + " VALUES (?, ?, ?, ?, ?)";
        try {
            // create the mysql insert preparedstatement
            Connection connection=connectionClass.getConnection();

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString (1, txt_productID.getText());
            preparedStmt.setString (2, txt_productName.getText());
            preparedStmt.setDouble(3, Double.parseDouble(txt_productPrice.getText()));
            preparedStmt.setString(4, txt_productDetails.getText());
            preparedStmt.setString(5, handleCategory());

            // execute the preparedstatement
            preparedStmt.execute();
            System.out.println("Product added to the database!");
            loadProdFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void loadProdFromDatabase() throws IOException{
        try {
            Connection connection=connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM products");
            while (rs.next()){

                data.add(new productDetails(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getString(6)));

            }

        } catch (SQLException ex){
            System.out.println("Error" + ex);
        }
        column_prodID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        column_prodName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        column_description.setCellValueFactory(new PropertyValueFactory<>("product_details"));

        tbl_prodTable.setItems(null);
        tbl_prodTable.setItems(data);

        System.out.println("Products loaded to table");

    }

    @FXML
    public void loadCategories(){
        String sqlStationName = " select * from categories ";
        try {
            Connection connection=connectionClass.getConnection();

            data = FXCollections.observableArrayList();
            connection = (Connection) connectionClass.getConnection();
            PreparedStatement pstStn = connection.prepareStatement(sqlStationName);
            ResultSet stnRS = pstStn.executeQuery(sqlStationName);
            while (stnRS.next()) {
                comboBox_Categories.getItems().add(stnRS.getString("catID") /*+ " " + stnRS.getString("category_name")*/);
            }
            stnRS.close();
            pstStn.close();
            connection.close();

        } catch (SQLException ex) {
            System.err.println("ERR" + ex);
        }
}
    @FXML
    public String handleCategory(){

        String category = String.valueOf(comboBox_Categories.getValue());
        System.out.println(category);
        return category;
    }




    public void goBack(ActionEvent actionEvent) {

        try {
            App.setRoot("dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            loadProdFromDatabase();
            loadCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







