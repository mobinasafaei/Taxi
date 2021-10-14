package databasesAccess;

import model.Trip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TripDatabaseAccess {
    Connection connection;

    public TripDatabaseAccess() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taxi", "root", "13811383");
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addTrip(Trip trip) {
        try {
            Statement statement=connection.createStatement();
            statement.executeUpdate(String.format("INSERT INTO taxi.trip (destination, origin, cost, driver_id,passenger_id) VALUES ('%s', '%s', '%s',null,null);",trip.getDestination(),trip.getOrigin(),trip.getCost()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
