package org.example;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

public class db_connection {

    Connection connObj;
    String JDBC_URL = "jdbc:sqlserver://interforesta.cczcoxmc3e60.us-east-2.rds.amazonaws.com:1433;databaseName=interforesta;user=admin;password=Anideliceu.1";
    public void connectDB(){
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connObj = DriverManager.getConnection(JDBC_URL);
        if(connObj != null) {
            DatabaseMetaData metaObj = (DatabaseMetaData) connObj.getMetaData();
            // System.out.println("Driver Name?= " + metaObj.getDriverName() + ", Driver Version?= " + metaObj.getDriverVersion() + ", Product Name?= " + metaObj.getDatabaseProductName() + ", Product Version?= " + metaObj.getDatabaseProductVersion());
            System.out.println("System connected to AWS Server");
        }
    } catch(Exception sqlException) {
        sqlException.printStackTrace();
    }
    return;
}


}
