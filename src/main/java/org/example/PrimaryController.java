package org.example;

import java.io.IOException;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrimaryController {

    @FXML
    Label label;

    @FXML
    private void switchToSecondary() throws IOException {
        //App.setRoot("secondary");


        String connectionUrl = "jdbc:DESKTOP-6937UU1\\TEST://localhost:1433;user=DESKTOP-6937UU1\\adamb;password=Anideliceu.2010";


        try{
            Connection con = DriverManager.getConnection(connectionUrl);
            label.setText("Connected");
        } catch(Exception e){
            System.out.println(e);
        }
               

                //System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName"));

        }





    }

