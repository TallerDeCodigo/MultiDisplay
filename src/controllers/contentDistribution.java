package controllers;

import java.sql.*;
import java.util.ArrayList;


/**
 * Content distribution class to manage operations with database
 * @author John Falcon for TDC
 * @version 0.1.1
 */
public class contentDistribution {

    String connection_url = "jdbc:mysql://localhost:3306/puebla_interactive";
    String username = "root";
    String password = "root";
    Connection connection = null;


    public contentDistribution(String connection_url, String username, String password){
        this.connection_url =  (connection_url != null) ? connection_url : this.connection_url;
        this.username = (username != null) ? username : this.username;
        this.password = (password != null) ? password : this.password;

    }


    /**
     * Connect to MySQL databse
     * @param String database_name
     */
    public void connectDB(){

        /*try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }*/

        try ( Connection aconnection = DriverManager.getConnection(connection_url, username, password)) {
            this.connection = aconnection;
            System.out.println("Database connected!");
        } catch (SQLException e) {

            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    /**
     * Connect to MySQL databse
     * @param String query
     */
    public ArrayList getSubmenu(Integer parent){
        ArrayList resultSet = new ArrayList();
        try(PreparedStatement Statement = this.connection.prepareStatement("Select * from places")){
            ResultSet result = Statement.executeQuery();
            while(result.next()) {
                resultSet.add(result.getString("name"));
            }
        }catch(SQLException e){
            throw new SQLException("Error executing query", e);
        }

        return
    }

}
