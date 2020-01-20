package org.example;

import Connection.db_connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriesController {


    @FXML
    public TableView<CategoriesController> tbl_catTable;

    @FXML
    public TableColumn<categoriesDetails, String> column_catID;

    @FXML
    public TableColumn<categoriesDetails, String> column_catName;

    @FXML
    public TextField txt_categoyID;

    @FXML
    public TextField txt_categoryName;

    private ObservableList<CategoriesController> data;



    db_connection connectionClass=new db_connection();
    Connection connection=connectionClass.getConnection();

    @FXML
    private void addCategory() throws IOException {


    String sql = "INSERT INTO categories(catID, category_name)" + " VALUES (?, ?)";

        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString (1, txt_categoyID.getText());
            preparedStmt.setString (2, txt_categoryName.getText());
            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadDataFromDatabase() throws IOException{
    try {
        data = FXCollections.observableArrayList();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM categories");
        while (rs.next()){

            data.add(new categoriesDetails(rs.getString(1), rs.getString(2)));


        }


    } catch (SQLException ex){
        System.out.println("Error" + ex);
    }
    column_catID.setCellValueFactory(new PropertyValueFactory<>("catID"));
    column_catName.setCellValueFactory(new PropertyValueFactory<>("catName"));

    tbl_catTable.setItems(null);
    tbl_catTable.setItems(data);



    }



}