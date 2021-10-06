import java.sql.*;
import java.util.stream.StreamSupport;

public class TaxiCompanyDataBase {
    private Connection connection;

    TaxiCompanyDataBase() {
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
        if (!checkDriver(driver.getUserName())) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(String.format("INSERT INTO taxi.driver (id, user_name,first_name, last_name, age) VALUES (null, '%s', '%s', '%s', '%s');", driver.getUserName(), driver.getFirstName(), driver.getLastName(), driver.getAge()));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.out.println("already exist!");
        }
    }

    public void addVehicle(Vehicle vehicle, String nationalCode) {
        if (!checkVehicle(vehicle.getTag())) {
            if (checkDriver(nationalCode)) {
                try {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(String.format("INSERT INTO taxi.vehicle (id,name, tag, color, vehicle_type, driverid) VALUES (null,'%s', '%s', '%s', '%s', '%s');", vehicle.getName(), vehicle.getTag(), vehicle.getColor(), vehicle.getVehicleType(), findDriverId(nationalCode)));
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

    public boolean checkDriver(String userName) {
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

    private int findDriverId(String userName) {
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
