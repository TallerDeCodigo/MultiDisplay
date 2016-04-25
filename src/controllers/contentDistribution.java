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

    private String connection_url;
    private String username;
    private String password;
    private Connection connection;

    public contentDistribution(String connection_url, String username, String password){
        this.connection_url =  (connection_url != null) ? connection_url : "jdbc:mysql://127.0.0.1:3306/puebla_interactive?autoReconnect=true&useSSL=false";
        this.username = (username != null) ? username : "root";
        this.password = (password != null) ? password : "root";
        if(this.connection != null){
            connectDB();
        }
    }

    public contentDistribution(){
        this.connection_url =  "jdbc:mysql://127.0.0.1:3306/puebla_interactive?autoReconnect=true&useSSL=false";
        this.username = "root";
        this.password = "root";
        this.connection = null;
        connectDB();
    }

    /**
     * Connect to MySQL database
     * @param String database_name
     */
    public Connection connectDB(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
        if(this.connection == null)
            try{
                Connection aconnection = DriverManager.getConnection(connection_url, username, password);
                this.connection = aconnection;
                System.out.println("Database connected, we got the beans");
                return this.connection;
            } catch (SQLException e) {

                throw new IllegalStateException("Sowwy, cannot connect the database!", e);
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
            PreparedStatement statement = localcon.prepareStatement("SELECT * FROM groups ORDER BY _order;");
            ResultSet result = statement.executeQuery();

            if(result != null){

                while(result.next()) {
                    Place myPlace = new Place();

                    myPlace.setId(result.getInt("id"));
                    myPlace.setGroup(result.getInt("_order"));
                    myPlace.setName(result.getString("name"));
                    myPlace.setCluster(true);
                    resultSet.add(myPlace);
                }
            }
            statement.close();
            return resultSet;
        }catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("Error executing main menu query", e);
        }
    }

    /**
     * Get submenu items by parent id
     * @param String query
     */
    public ArrayList fetchSubmenu(Integer parent) throws SQLException {
        ArrayList<Place> resultSet = new ArrayList();
        Connection localcon = getConnection();
        try{
            PreparedStatement statement = localcon.prepareStatement(String.format("SELECT * FROM places WHERE grupo = %d ORDER BY _order;", parent) );
            ResultSet result = statement.executeQuery();
            if(result != null)
                while(result.next()) {

                    Place myPlace = new Place();
                    myPlace.setId(result.getInt("id"));
                    myPlace.setGroup(result.getInt("_order"));
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
            throw new SQLException("Error executing submenu query", e);
        }
    }

    /**
     * Fetch detail content
     * @param String query
     */
    public Place fetchDetail(Integer place_id) throws SQLException {
        Connection localcon = getConnection();
        try{
            PreparedStatement statement = localcon.prepareStatement(String.format("SELECT * FROM places WHERE id = %d;", place_id) );
            ResultSet result = statement.executeQuery();
            if(result != null)
                while(result.next()) {
                    // Forms new Place object
                    Place myPlace = new Place();
                    myPlace.setId(result.getInt("id"));
                    myPlace.setGroup(result.getInt("_order"));
                    myPlace.setName(result.getString("name"));
                    myPlace.setDescription(result.getString("description"));
                    myPlace.setImage(result.getString("image"));
                    myPlace.setVideo(result.getString("video"));
                    myPlace.setLatlong(result.getString("latlong"));
                    myPlace.setDetails(result.getString("details"));
                    myPlace.setAddress(result.getString("address"));
                    myPlace.setLocation(result.getString("location"));
                    return myPlace;
                }
            statement.close();
        }catch(SQLException e){
            throw new SQLException("Error executing detail query", e);
        }
        return new Place();
    }

    public Connection getConnection() {
        return (connection != null) ? connection : this.connectDB();
    }
}
