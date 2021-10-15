import databasesAccess.DriverDatabaseAccess;
import databasesAccess.PassengerDatabaseAccess;
import databasesAccess.TripDatabaseAccess;
import databasesAccess.VehicleDatabaseAccess;
import enums.*;
import model.Driver;
import model.Passenger;
import model.Trip;
import model.Vehicle;

import java.util.Scanner;

import static databasesAccess.DriverDatabaseAccess.checkDriver;
import static databasesAccess.DriverDatabaseAccess.findDriverIdByUsername;
import static databasesAccess.PassengerDatabaseAccess.checkPassenger;

public class Main {
    public static void main(String[] args) {
        DriverDatabaseAccess driverDatabaseAccess = new DriverDatabaseAccess();
        VehicleDatabaseAccess vehicleDatabaseAccess = new VehicleDatabaseAccess();
        PassengerDatabaseAccess passengerDatabaseAccess = new PassengerDatabaseAccess();
        TripDatabaseAccess tripDatabaseAccess = new TripDatabaseAccess();
        Scanner scanner = new Scanner(System.in);
        showMainMeu();
        boolean exit = true;
        int choose = scanner.nextInt();
        while (exit) {
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
                        System.out.println("1)confirm cash receipt\n2)travel finished\n3)exit");
                        int choose1 = scanner.nextInt();
                        int driverId = findDriverIdByUsername(userName);
                        int passengerId = tripDatabaseAccess.findPassengerIdByDriverId(driverId);
                        String passengerUsername = passengerDatabaseAccess.getPassengerUsernameById(passengerId);
                        if (choose1 == 1) {
                            if (tripDatabaseAccess.getPaymentStatusByDriverId(driverId).equals(PaymentStatus.NOT_PAYED.getPaymentStatus())) {
                                tripDatabaseAccess.changeTripPaymentStatusToPayed(driverId);
                                showMainMeu();
                                choose = scanner.nextInt();
                                break;
                            } else {
                                System.out.println("trip cost already receipt");
                                showMainMeu();
                                choose = scanner.nextInt();
                                break;
                            }

                        } else if (choose1 == 2) {
                            if (tripDatabaseAccess.getPaymentStatusByDriverId(driverId).equals(PaymentStatus.PAYED.getPaymentStatus())) {
                                passengerDatabaseAccess.changePassengerStatusToFree(passengerUsername);
                                tripDatabaseAccess.changeTripStatusToFinished(driverId);
                                driverDatabaseAccess.changeDriverStatusToWaitForTrip(driverId);
                                String newLocation = tripDatabaseAccess.getDestination(driverId);
                                driverDatabaseAccess.changeDriverLocation(newLocation, driverId);
                            } else {
                                System.out.println("trip cost not receipt yet!");
                                showMainMeu();
                                choose = scanner.nextInt();
                                break;
                            }
                        } else if (choose1 == 3) {
                            showMainMeu();
                            choose = scanner.nextInt();
                            break;
                        }

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
                        System.out.println("1)Travel request(pay by cash)\n2)Travel request(pay by account balance)\n3)increase balance\n4)exit");
                        int choose1 = scanner.nextInt();
                        if (choose1 == 1) {
                            if (passengerDatabaseAccess.checkPassengerStatus(userName)) {
                                scanner.nextLine();
                                System.out.println("enter your origin according this pattern:600,900");
                                Trip trip = new Trip();
                                trip.setOrigin(scanner.nextLine());
                                System.out.println("enter your destination according this pattern:600,900");
                                trip.setDestination(scanner.nextLine());
                                trip.calculateCost();
                                FindClosestDriver findClosestDriver = new FindClosestDriver(trip);
                                if (findClosestDriver.isDriverFound()) {
                                    trip.setPaymentStatus(PaymentStatus.NOT_PAYED.getPaymentStatus());
                                    trip.setTripStatus(TripStatus.ONGOING.getTripStatus());
                                    tripDatabaseAccess.addTrip(trip);
                                    tripDatabaseAccess.paymentWay(PaymentWay.PAY_BY_CASH.getPaymentWay(), trip);
                                    int driverId = findClosestDriver.getDriverId();
                                    tripDatabaseAccess.addDriverIdToTrip(trip, driverId);
                                    int passengerId = passengerDatabaseAccess.getPassengerIdByUsername(userName);
                                    tripDatabaseAccess.addPassengerIdToTrip(trip, passengerId);
                                    passengerDatabaseAccess.changePassengerStatusToOnTrip(userName);
                                    driverDatabaseAccess.changeDriverStatusToOnTrip(driverId);
                                } else {
                                    System.out.println("sorry we don't have driver now:( ");
                                }
                                System.out.println("1)increase balance\n2)exit");
                                int choose3 = scanner.nextInt();
                                if (choose3 == 1) {
                                    System.out.println("how much balance you wanna increase?");
                                    double increaseBalance = scanner.nextDouble();
                                    double balance = passengerDatabaseAccess.getBBalanceByUsername(userName);
                                    double newBalance = increaseBalance + balance;
                                    passengerDatabaseAccess.setNewBalance(userName, increaseBalance);
                                    System.out.println("your balance increased");
                                    showMainMeu();
                                    choose = scanner.nextInt();
                                    break;
                                } else if (choose3 == 2) {
                                    System.out.println("you exit.");
                                    showMainMeu();
                                    choose = scanner.nextInt();
                                    break;
                                }
                            } else {
                                System.out.println("you are on trip!");
                                showMainMeu();
                                choose = scanner.nextInt();
                                break;
                            }
                        } else if (choose1 == 2) {
                            if (passengerDatabaseAccess.checkPassengerStatus(userName)) {
                                scanner.nextLine();
                                System.out.println("enter your origin according this pattern:600,900");
                                Trip trip = new Trip();
                                trip.setOrigin(scanner.nextLine());
                                System.out.println("enter your destination according this pattern:600,900");
                                trip.setDestination(scanner.nextLine());
                                trip.calculateCost();
                                double balance = passengerDatabaseAccess.getBBalanceByUsername(userName);
                                if (balance >= trip.getCost()) {
                                    FindClosestDriver findClosestDriver = new FindClosestDriver(trip);
                                    if (findClosestDriver.isDriverFound()) {
                                        double newBalance = balance - trip.getCost();
                                        passengerDatabaseAccess.setNewBalance(userName, newBalance);
                                        trip.setTripStatus(TripStatus.ONGOING.getTripStatus());
                                        trip.setPaymentStatus(PaymentStatus.PAYED.getPaymentStatus());
                                        tripDatabaseAccess.addTrip(trip);
                                        tripDatabaseAccess.paymentWay(PaymentWay.PAY_BY_ACCOUNT_BALANCE.getPaymentWay(), trip);
                                        int driverId = findClosestDriver.getDriverId();
                                        tripDatabaseAccess.addDriverIdToTrip(trip, driverId);
                                        int passengerId = passengerDatabaseAccess.getPassengerIdByUsername(userName);
                                        tripDatabaseAccess.addPassengerIdToTrip(trip, passengerId);
                                        passengerDatabaseAccess.changePassengerStatusToOnTrip(userName);
                                        driverDatabaseAccess.changeDriverStatusToOnTrip(driverId);
                                    } else {
                                        System.out.println("sorry we don't have driver now:( ");
                                    }
                                } else {
                                    System.out.println("you should increase your balance:((((");
                                }
                                System.out.println("1)increase balance\n2)exit");
                                int choose3 = scanner.nextInt();
                                if (choose3 == 1) {
                                    System.out.println("how much balance you wanna increase?");
                                    double increaseBalance = scanner.nextDouble();
                                    double newBalance = increaseBalance + balance;
                                    passengerDatabaseAccess.setNewBalance(userName, increaseBalance);
                                    System.out.println("your balance increased");
                                    showMainMeu();
                                    choose = scanner.nextInt();
                                    break;
                                } else if (choose3 == 2) {
                                    System.out.println("you exit.");
                                    showMainMeu();
                                    choose = scanner.nextInt();
                                    break;
                                }
                            } else {
                                System.out.println("you are on trip!");
                                showMainMeu();
                                choose = scanner.nextInt();
                                break;
                            }
                        } else if (choose1 == 3) {
                            System.out.println("how much balance you wanna increase?");
                            double increaseBalance = scanner.nextDouble();
                            double balance = passengerDatabaseAccess.getBBalanceByUsername(userName);
                            double newBalance = increaseBalance + balance;
                            passengerDatabaseAccess.setNewBalance(userName, increaseBalance);
                            System.out.println("your balance increased");
                            showMainMeu();
                            choose = scanner.nextInt();
                            break;
                        } else if (choose1 == 4) {
                            System.out.println("you exit!");
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
                    tripDatabaseAccess.showTrips();
                    showMainMeu();
                    choose = scanner.nextInt();
                    break;
                }
                case 6: {
                    System.out.println("id\tuser name\tfirst name\tlast name\taddress");
                    driverDatabaseAccess.showDriverList();
                    showMainMeu();
                    choose = scanner.nextInt();
                    break;
                }
                case 7: {
                    System.out.println("id\tuser name\tfirst name\tlast name");
                    passengerDatabaseAccess.showPassengerList();
                    showMainMeu();
                    choose = scanner.nextInt();
                    break;
                }
                case 8: {
                    System.out.println("you exit!");
                    exit = false;
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
                "5)show ongoing trips\n" +
                "6)show a list of drivers\n" +
                "7)show a list of passengers\n" +
                "8)exit");
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
