package databasesAccess;

import model.Driver;

import java.sql.*;

public class DriverDatabaseAccess {
    private static Connection connection;
    public DriverDatabaseAccess(){
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
    public void addDriver(Driver driver) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(String.format("INSERT INTO taxi.driver (id, user_name,first_name, last_name, age) VALUES (null, '%s', '%s', '%s', '%s');", driver.getUserName(), driver.getFirstName(), driver.getLastName(), driver.getAge()));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    public static boolean checkDriver(String userName) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT user_name FROM taxi.driver");
            while (resultSet.next()) {
                if (userName.equals(resultSet.getString("user_name"))){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public void showDriverList() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from taxi.driver");
            while (resultSet.next()) {
                System.out.print(resultSet.getInt("id") + "\t");
                System.out.print(resultSet.getString("user_name") + "\t");
                System.out.print(resultSet.getString("first_name") + "\t");
                System.out.println(resultSet.getString("last_name"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static int findDriverId(String userName) {
        int id = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT id FROM taxi.driver where user_name='%s'", userName));
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
}
