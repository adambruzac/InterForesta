
package Connection;


import java.sql.Connection;
import java.sql.DriverManager;

public class db_connection {
    public Connection connection;
    public  Connection getConnection(){


        String dbName="InterForesta";
        String userName="root";
        String password="Anideliceu.2010";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            connection= DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return connection;
    }

}