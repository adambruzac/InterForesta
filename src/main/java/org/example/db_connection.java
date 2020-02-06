
package org.example;


import java.sql.Connection;
import java.sql.DriverManager;


public class db_connection {


    public Connection connection;

    public Connection getConnection() {


        String dbName = "interforesta";
        String userName = "root";
        String password = "Anideliceu.2010";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, userName, password);


        } catch (Exception e) {
            System.out.println("Connection failed");
        }


        return connection;

    }
/*

            public void test(){
                Driver driver = GraphDatabase.driver("bolt://localhost:11020", AuthTokens.basic("neo4j", "admin"));

            }




        }*/

}