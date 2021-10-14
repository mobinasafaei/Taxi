package databasesAccess;

import enums.PassengerStatus;
import model.Passenger;

import java.sql.*;

public class PassengerDatabaseAccess {
    private static Connection connection;

    public PassengerDatabaseAccess() {
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
                statement.executeUpdate(String.format("INSERT INTO taxi.passenger (id,user_name, first_name, last_name, address,balance,passenger_status) " +
                                "VALUES (null,'%s' ,'%s', '%s', '%s','%s','%s');"
                        , passenger.getUserName(), passenger.getFirstName(), passenger.getLastName(), passenger.getAddress(), 0, PassengerStatus.FREE.getPassengerStatus()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.out.println("user name already exist ");
        }
    }

    public static boolean checkPassenger(String userName) {
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

    public int getPassengerIdByUsername(String username) {
        int id = -1;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * from taxi.passenger where user_name='%s'", username));
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public void increaseBalance(String userName, double increaseBalance) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from taxi.passenger");
            double newBalance = 0;
            while (resultSet.next()) {
                if (userName.equals(resultSet.getString("user_name"))) {
                    newBalance = resultSet.getDouble("balance") + increaseBalance;
                }
            }
            statement.executeUpdate(String.format("UPDATE taxi.passenger SET balance = '%s' WHERE (user_name = '%s')", newBalance, userName));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean checkPassengerStatus(String username) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT passenger_status from taxi.passenger where user_name='%s'", username));
            while (resultSet.next()) {
                if (resultSet.getString("passenger_status").equals(PassengerStatus.FREE.getPassengerStatus())) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void changePassengerStatusToOnTrip(String username) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("UPDATE taxi.passenger SET passenger_status='%s' where user_name='%s'", PassengerStatus.ON_TRIP.getPassengerStatus(), username));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public double getBBalanceByUsername(String username) {
      double balance=0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select balance from taxi.passenger where user_name='%s'", username));
        while (resultSet.next()){
            balance=resultSet.getDouble("balance");
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return balance;
    }
}
