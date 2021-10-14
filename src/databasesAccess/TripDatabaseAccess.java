package databasesAccess;

import enums.TripStatus;
import model.Trip;

import java.sql.*;

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addTrip(Trip trip) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format
                    ("INSERT INTO taxi.trip (destination, origin, cost,trip_status) VALUES ('%s', '%s', '%s','%s');",
                    trip.getDestination(), trip.getOrigin(), trip.getCost(), TripStatus.ONGOING));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void showTrips() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from taxi.trip where trip_status='%s'",TripStatus.ONGOING.getTripStatus()));
            while (resultSet.next()) {
                System.out.print(resultSet.getString("origin")+"\t");
                System.out.print(resultSet.getString("destination")+"\t");
                System.out.print(resultSet.getString("driver_id")+"\t");
                System.out.println(resultSet.getString("passenger_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addDriverIdToTrip(Trip trip,int id){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("UPDATE taxi.trip SET driver_id = '%s'" +
                    "WHERE (origin = '%s' and destination='%s' );",id,trip.getOrigin(),trip.getDestination()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addPassengerIdToTrip(Trip trip,int id){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("UPDATE taxi.trip SET passenger_id = '%s' " +
                    "WHERE (origin = '%s' and destination='%s' );",id,trip.getOrigin(),trip.getDestination()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void paymentWay(String paymentWay,Trip trip){
        try {
            Statement statement = connection.createStatement();
           statement.executeUpdate(String.format("UPDATE taxi.trip SET payment_way = '%s' WHERE (origin = '%s' and destination='%s')",paymentWay,trip.getOrigin(),trip.getDestination()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
