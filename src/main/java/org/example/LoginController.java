package org.example;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginController {
    @FXML
    private Label lbl_msg;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_password;



    @FXML
    private void switchToSecondary() throws IOException {


        db_connection connectionClass=new db_connection();
        Connection connection=connectionClass.getConnection();

        try {
            Statement statement=connection.createStatement();
            String sql="SELECT * FROM interforesta.users WHERE user_name = '"+txt_username.getText()+"' AND user_password = '"+txt_password.getText()+"';";
            ResultSet resultSet=statement.executeQuery(sql);

            if (resultSet.next()){

                App.setRoot("addCategory");
                System.out.println("ok");
            }else {
                lbl_msg.setText("bad user");
                System.out.println("not ok");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



        /*
                db_connection connectionClass=new db_connection();

        String usernames = "MATCH (u:Users{username:\"admin\"})";
        String password = "MATCH (u:Users{password:\"admin\"})";

        if(usernames == txt_username.getText() && password == txt_password.getText()){
            App.setRoot("addCategory");
        }



*/

}





