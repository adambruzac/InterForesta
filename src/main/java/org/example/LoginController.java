package org.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;


public class LoginController {
    @FXML
    private Label lbl_msg;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_password;

    @FXML
    private void switchToSecondary() throws IOException {

//Code from  https://github.com/antkaynak/JavaFX-SQL-Login-Template/blob/master/SQLogin/src/sample/Controller.java --
        String checkUser = txt_username.getText();
        String checkPassword = txt_password.getText();
        String sql = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
        try {
            String JDBC_URL = "jdbc:sqlserver://interforesta.cczcoxmc3e60.us-east-2.rds.amazonaws.com:1433;databaseName=interforesta;user=admin;password=Anideliceu.1";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connObj = DriverManager.getConnection(JDBC_URL);
            Statement command = connObj.createStatement();
            ResultSet rs = command.executeQuery("SELECT * FROM users WHERE user_name = '" + txt_username.getText() + "' AND user_password='" + txt_password.getText() + "';");
            if (rs.next()) {
                App.setRoot("secondary");


            } else {
                lbl_msg.setText("Login unsuccesful!");
            }
        }
         catch(Exception sqlException) {
            sqlException.printStackTrace();
        }


    }


}





