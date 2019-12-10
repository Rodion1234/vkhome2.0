
package ru.markov.vkproject.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class ConnectionDB {
    
    
    Connection connection;
    
    public Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        
        connection = DriverManager.getConnection("jdbc:mysql://localhost/db_work?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false","root","root");
    
        return connection;
    }

    
}
