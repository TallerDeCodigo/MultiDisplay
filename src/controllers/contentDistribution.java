package controllers;

import model.Place;

import java.sql.*;
import java.util.ArrayList;


/**
 * Content distribution class to manage operations with database
 * @author John Falcon for TDC
 * @version 0.1.1
 */

public class contentDistribution {

    private String connection_url = "jdbc:mysql://127.0.0.1:3306/puebla_interactive";
    private String username       = "root";
    private String password       = "root";
    private Connection connection = null;

    public contentDistribution(String connection_url, String username, String password){
        this.connection_url =  (connection_url != null) ? connection_url : this.connection_url;
        this.username = (username != null) ? username : this.username;
        this.password = (password != null) ? password : this.password;
        if(this.connection != null){
            connectDB();
        }
    }

    /**
     * Connect to MySQL databse
     * @param String database_name
     */
    public Connection connectDB(){

        /*try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }*/
        if(this.connection == null)
            try{
                Connection aconnection = DriverManager.getConnection(connection_url, username, password);
                this.connection = aconnection;
                System.out.println("Database connected!");
                return this.connection;
            } catch (SQLException e) {

                throw new IllegalStateException("Cannot connect the database!", e);
            }
        return this.connection;
    }

    /**
     * Get menu items by group id
     * @param String query
     */
    public ArrayList fetchMainMenu() throws SQLException {
        ArrayList<Place> resultSet = new ArrayList();
        Connection localcon = getConnection();
        try{
            PreparedStatement statement = localcon.prepareStatement("SELECT * FROM groups;");
            ResultSet result = statement.executeQuery();

            if(result != null){

                while(result.next()) {
                    Place myPlace = new Place();

                    myPlace.setId(result.getInt("id"));
                    myPlace.setGroup(result.getInt("order"));
                    myPlace.setName(result.getString("name"));
                    myPlace.setCluster(true);

                    resultSet.add(myPlace);
                }
            }
            statement.close();
            return resultSet;
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("Error executing query", e);
        }

    }

    /**
     * Get submenu items by parent id
     * @param String query
     */
    public ArrayList getSubmenu(Integer parent) throws SQLException {
        ArrayList<Place> resultSet = new ArrayList();
        Connection localcon = getConnection();
        try{
            PreparedStatement statement = localcon.prepareStatement(String.format("SELECT * FROM places WHERE group = %d", parent) );
            ResultSet result = statement.executeQuery();
            if(result != null)
                while(result.next()) {
                    Place myPlace = new Place();

                    myPlace.setId(result.getInt("id"));
                    myPlace.setGroup(result.getInt("order"));
                    myPlace.setName(result.getString("name"));
                    myPlace.setDescription(result.getString("description"));
                    myPlace.setImage(result.getString("image"));
                    myPlace.setVideo(result.getString("video"));
                    myPlace.setLatlong(result.getString("latlong"));

                    resultSet.add(myPlace);
                }
            statement.close();
            return resultSet;
        }catch(SQLException e){
            throw new SQLException("Error executing query", e);
        }
    }

    public Connection getConnection() {
        return (connection != null) ? connection : this.connectDB();
    }
}
