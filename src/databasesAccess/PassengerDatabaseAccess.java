package databasesAccess;

import model.Passenger;

import java.sql.*;

public class PassengerDatabaseAccess {
    private Connection connection;
    public PassengerDatabaseAccess(){
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
    public void addPassenger(Passenger passenger) {
        if (!checkPassenger(passenger.getUserName())) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(String.format("INSERT INTO taxi.passenger (id,user_name, first_name, last_name, address) VALUES (null,'%s' ,'%s', '%s', '%s');", passenger.getUserName(), passenger.getFirstName(), passenger.getLastName(), passenger.getAddress()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            System.out.println("user name already exist ");
        }
    }

    public boolean checkPassenger(String userName) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT user_name from taxi.passenger");
            while (resultSet.next()) {
                if (userName.equals(resultSet.getString("user_name"))) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public void showPassengerList() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from taxi.passenger");
            while (resultSet.next()) {
                System.out.print(resultSet.getInt("id") + "\t");
                System.out.print(resultSet.getString("user_name") + "\t");
                System.out.print(resultSet.getString("first_name") + "\t");
                System.out.print(resultSet.getString("last_name") + "\t");
                System.out.println(resultSet.getString("address"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
