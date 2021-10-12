package databasesAccess;

import model.Vehicle;

import java.sql.*;

import static databasesAccess.DriverDatabaseAccess.checkDriver;
import static databasesAccess.DriverDatabaseAccess.findDriverId;

public class VehicleDatabaseAccess {
    private Connection connection;
    DriverDatabaseAccess driverDatabaseAccess =new DriverDatabaseAccess();
    public VehicleDatabaseAccess(){
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
    public void addVehicle(Vehicle vehicle, String nationalCode) {
        if (!checkVehicle(vehicle.getTag())) {
            if (checkDriver(nationalCode)) {
                try {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(String.format("INSERT INTO taxi.vehicle " +
                                    "(id,name, tag, color, vehicle_type, driverid) VALUES (null,'%s', '%s', '%s', '%s', '%s');",
                            vehicle.getName(), vehicle.getTag(), vehicle.getColor(), vehicle.getVehicleType(),
                            findDriverId(nationalCode)));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                System.out.println("username is wrong or driver not exist you must register first.");
            }
        } else {
            System.out.println("vehicle is already exist!");
        }
    }
    public boolean checkVehicle(String tag) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from taxi.vehicle");
            while (resultSet.next()) {
                if (tag.equals(resultSet.getString("tag"))) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
