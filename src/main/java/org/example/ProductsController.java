package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.sql.Connection;

public class ProductsController {

    @FXML
    public TextField txt_productID;
    @FXML
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
    public ChoiceBox choixeBox_categories;


    db_connection connectionClass=new db_connection();
    Connection connection=connectionClass.getConnection();

}
