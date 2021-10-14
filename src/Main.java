import databasesAccess.DriverDatabaseAccess;
import databasesAccess.PassengerDatabaseAccess;
import databasesAccess.TripDatabaseAccess;
import databasesAccess.VehicleDatabaseAccess;
import enums.DriverStatus;
import enums.VehicleColor;
import enums.VehicleType;
import model.Driver;
import model.Passenger;
import model.Trip;
import model.Vehicle;

import java.util.Scanner;

import static databasesAccess.DriverDatabaseAccess.checkDriver;
import static databasesAccess.PassengerDatabaseAccess.checkPassenger;

public class Main {
    public static void main(String[] args) {
        DriverDatabaseAccess driverDatabaseAccess = new DriverDatabaseAccess();
        VehicleDatabaseAccess vehicleDatabaseAccess = new VehicleDatabaseAccess();
        PassengerDatabaseAccess passengerDatabaseAccess = new PassengerDatabaseAccess();
        TripDatabaseAccess tripDatabaseAccess = new TripDatabaseAccess();
        Scanner scanner = new Scanner(System.in);
        showMainMeu();
        int choose = scanner.nextInt();
        while (true) {
            switch (choose) {
                case 1: {
                    System.out.println("how many driver you want to register");
                    int n = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < n; i++) {
                        Driver driver = new Driver();
                        Vehicle vehicle = new Vehicle();
                        boolean exist = registerDriver(driver, driverDatabaseAccess);
                        if (exist) {
                            System.out.println("enter driver vehicle information:\nname:");
                            vehicle.setName(scanner.nextLine());
                            System.out.println("tag:");
                            vehicle.setTag(scanner.nextLine());
                            System.out.println("choose color:\n1)blue\n2)white\n3)black");
                            int chooseColor = scanner.nextInt();
                            if (chooseColor == 1) {
                                vehicle.setColor(VehicleColor.BLUE.getVehicleColor());
                            } else if (chooseColor == 2) {
                                vehicle.setColor(VehicleColor.WHITE.getVehicleColor());
                            } else if (chooseColor == 3) {
                                vehicle.setColor(VehicleColor.BLACK.getVehicleColor());
                            }
                            System.out.println("choose vehicle type:");
                            System.out.println("1)car");
                            if (scanner.nextInt() == 1) {
                                vehicle.setVehicleType(VehicleType.CAR.getVehicleType());
                            }
                            vehicleDatabaseAccess.addVehicle(vehicle, driver.getUserName());
                        }
                    }
                    showMainMeu();
                    choose = scanner.nextInt();
                    break;
                }
                case 2: {
                    System.out.println("how many passenger you want to register");
                    int n = scanner.nextInt();
                    for (int i = 0; i < n; i++) {
                        Passenger passenger = new Passenger();
                        scanner.nextLine();
                        System.out.println("enter passenger information:\nuser name:");
                        passenger.setUserName(scanner.nextLine());
                        System.out.println("first name:");
                        passenger.setFirstName(scanner.nextLine());
                        System.out.println("last name:");
                        passenger.setLastName(scanner.nextLine());
                        System.out.println("address:");
                        passenger.setAddress(scanner.nextLine());
                        passengerDatabaseAccess.addPassenger(passenger);
                    }
                    showMainMeu();
                    choose = scanner.nextInt();
                    break;
                }
                case 3: {
                    scanner.nextLine();
                    System.out.println("enter username(your national code):");
                    String userName = scanner.nextLine();
                    if (checkDriver(userName)) {
                        System.out.println("you login.");
                        //TODO

                    } else {
                        System.out.println("username not exist!");
                        System.out.println("1)register\n2)exit");
                        int n = scanner.nextInt();
                        if (n == 1) {
                            Driver driver = new Driver();
                            registerDriver(driver, driverDatabaseAccess);
                            showMainMeu();
                            choose = scanner.nextInt();
                            break;
                        } else if (n == 2) {
                            showMainMeu();
                            choose = scanner.nextInt();
                            break;
                        }
                    }
                }
                case 4: {
                    scanner.nextLine();
                    System.out.println("enter username(your national code):");
                    String userName = scanner.nextLine();
                    if (checkPassenger(userName)) {
                        System.out.println("you login.");
                        System.out.println("1)Travel request(pay by cash)\n2)Travel request(pay by account balance)\n3)increase balance");
                        int choose1 = scanner.nextInt();
                        if (choose1 == 1) {
                            scanner.nextLine();
                            System.out.println("enter your origin according this pattern:600,900");
                            Trip trip = new Trip();
                            trip.setOrigin(scanner.nextLine());
                            System.out.println("enter your destination according this pattern:600,900");
                            trip.setDestination(scanner.nextLine());
                            trip.calculateCost();
                            tripDatabaseAccess.addTrip(trip);
                            new FindClosestDriver(trip);
                            showMainMeu();
                            choose = scanner.nextInt();
                            break;
                        } else if (choose1 == 2) {
                            //TODO
                        } else if (choose1 == 3) {
                            System.out.println("how much balance you wanna increase?");
                            double increaseBalance = scanner.nextDouble();
                            passengerDatabaseAccess.increaseBalance(userName, increaseBalance);
                            System.out.println("your balance increased");
                            showMainMeu();
                            choose = scanner.nextInt();
                            break;
                        }

                    } else {
                        System.out.println("1)register\n2)exit");
                        int n = scanner.nextInt();
                        if (n == 1) {
                            Passenger passenger = new Passenger();
                            scanner.nextLine();
                            System.out.println("enter passenger information:\nuser name:");
                            passenger.setUserName(scanner.nextLine());
                            System.out.println("first name:");
                            passenger.setFirstName(scanner.nextLine());
                            System.out.println("last name:");
                            passenger.setLastName(scanner.nextLine());
                            System.out.println("address:");
                            passenger.setAddress(scanner.nextLine());
                            passengerDatabaseAccess.addPassenger(passenger);
                            showMainMeu();
                            choose = scanner.nextInt();
                        } else if (n == 2) {
                            showMainMeu();
                            choose = scanner.nextInt();
                            break;
                        }
                    }
                    break;
                }
                case 5: {
                    System.out.println("id\tuser name\tfirst name\tlast name\taddress");
                    driverDatabaseAccess.showDriverList();
                    showMainMeu();
                    choose = scanner.nextInt();
                    break;
                }
                case 6: {
                    System.out.println("id\tuser name\tfirst name\tlast name");
                    passengerDatabaseAccess.showPassengerList();
                    showMainMeu();
                    choose = scanner.nextInt();
                    break;
                }
            }
        }
    }

    public static void showMainMeu() {
        System.out.println("what do you want to do :\n" +
                "1)add a group of drivers\n" +
                "2)add a group of passengers\n" +
                "3)driver sign up or login\n" +
                "4)passenger sign up or login\n" +
                "5)show a list of drivers\n" +
                "6)show a list of passengers");
    }

    public static boolean registerDriver(Driver driver, DriverDatabaseAccess driverDatabaseAccess) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter driver information:\n" + "first name:");
        driver.setFirstName(scanner.nextLine());
        System.out.println("last name:");
        driver.setLastName(scanner.nextLine());
        System.out.println("username(it is your national code):");
        driver.setUserName(scanner.nextLine());
        System.out.println("age:");
        int age = scanner.nextInt();
        driver.setAge(age);
        driver.setStatus(DriverStatus.WAIT_FOR_TRIP.getDriverStatus());
        scanner.nextLine();
        System.out.println("enter your location according this pattern:600,900");
        driver.setLocation(scanner.nextLine());
        if (!checkDriver(driver.getUserName())) {
            driverDatabaseAccess.addDriver(driver);
            System.out.println("driver registered");
            return true;
        } else {
            System.out.println("already exist");
            return false;
        }
    }

}
