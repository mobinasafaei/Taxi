import java.sql.*;

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

    public void addDriver(Driver driver, Vehicle vehicle) {
        if (!checkDriver(driver.getNationalCode())) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(String.format("INSERT INTO taxi.driver (id, first_name, last_name, national_code, age) VALUES (null, '%s', '%s', '%s', '%s');", driver.getFirstName(), driver.getLastName(), driver.getNationalCode(), driver.getAge()));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            System.out.println("already exist!");
        }
    }

    public void addVehicle(Vehicle vehicle, String nationalCode) {
        if (checkDriver(nationalCode)) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(String.format("INSERT INTO taxi.vehicle (id,name, tag, color, vehicle_type, driverid) VALUES (null,'%s', '%s', '%s', '%s', '%s');", vehicle.getName(), vehicle.getTag(), vehicle.getColor(), vehicle.getVehicleType(), findDriverId(nationalCode)));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.out.println("username is wrong");
        }
    }

    public void addPassenger(Passenger passenger) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("INSERT INTO taxi.passenger (id, first_name, last_name, address) VALUES (null, '%s', '%s', '%s');", passenger.getFirstName(), passenger.getLastName(), passenger.getAddress()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean checkDriver(String nationalCode) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT national_code FROM taxi.driver");
            while (resultSet.next()) {
                if (nationalCode.equals(resultSet.getString("national_code"))) {
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
            ResultSet resultSet = statement.executeQuery("SELECT national_code from taxi.passenger");
            while (resultSet.next()) {
                if (userName.equals(resultSet.getString("national_code"))) {
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
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select * from taxi.driver");
            while (resultSet.next()){
                System.out.print(resultSet.getInt("id")+"\t");
                System.out.print(resultSet.getString("first_name")+"\t");
                System.out.println(resultSet.getString("last_name")+"\t");
                System.out.println(resultSet.getString("national_code"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void showPassengerList() {
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select * from taxi.passenger");
            while (resultSet.next()){
                System.out.print(resultSet.getInt("id")+"\t");
                System.out.print(resultSet.getString("first_name")+"\t");
                System.out.println(resultSet.getString("last_name")+"\t");
                System.out.println(resultSet.getString("address"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int findDriverId(String nationalCode) {
        int id = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT id FROM taxi.driver where national_code='%s'", nationalCode));
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
}
