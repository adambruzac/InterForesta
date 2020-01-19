package org.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import Connection.db_connection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    public TextField txt_categoyID;

    @FXML
    public TextField txt_categoryName;


    @FXML
    private void addCategory() throws IOException {
        db_connection connectionClass=new db_connection();
        Connection connection=connectionClass.getConnection();


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
}