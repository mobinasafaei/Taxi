import enums.VehicleColor;
import enums.VehicleType;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaxiCompanyDataBase taxiCompanyDataBase = new TaxiCompanyDataBase();
        Scanner scanner = new Scanner(System.in);
        showMainMeu();
        int choose = scanner.nextInt();
        while (true) {
            switch (choose) {
                case 1: {
                    addGroupOfDriver(scanner, taxiCompanyDataBase);
                    showMainMeu();
                    choose = scanner.nextInt();
                    break;
                }
                case 2: {
                    System.out.println("how many passenger you want to register");
                    int n = scanner.nextInt();
                    for (int i = 0; i < n; i++) {
                        addPassenger(scanner, taxiCompanyDataBase);
                    }
                    showMainMeu();
                    choose = scanner.nextInt();
                    break;

                }
                case 3: {
                    scanner.nextLine();
                    System.out.println("enter username(your national code):");
                    if (taxiCompanyDataBase.checkDriver(scanner.nextLine())) {
//TODO
                    } else {
                        System.out.println("1)register\n2)exit");
                        int n = scanner.nextInt();
                        if (n == 1) {
                            Driver driver = new Driver();
                            registerDriver(scanner, driver, taxiCompanyDataBase);
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
                    System.out.println("enter username(your national code):");
                    if (taxiCompanyDataBase.checkDriver(scanner.nextLine())) {
//TODO
                    } else {
                        System.out.println("1)register\n2)exit");
                        int n = scanner.nextInt();
                        if (n == 1) {
                            addPassenger(scanner, taxiCompanyDataBase);
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
                    taxiCompanyDataBase.showDriverList();
                    showMainMeu();
                    choose = scanner.nextInt();
                    break;
                }
                case 6: {
                    taxiCompanyDataBase.showPassengerList();
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

    public static void addGroupOfDriver(Scanner scanner, TaxiCompanyDataBase taxiCompanyDataBase) {
        System.out.println("how many driver you want to register");
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            Driver driver = new Driver();
            Vehicle vehicle = new Vehicle();
            registerDriver(scanner, driver, taxiCompanyDataBase);
            scanner.nextLine();
            if (!taxiCompanyDataBase.checkDriver(driver.getNationalCode())) {
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
                vehicle.setVehicleType(VehicleType.CAR.getVehicleType());
                taxiCompanyDataBase.addVehicle(vehicle, driver.getNationalCode());
            }
        }
    }

    public static void addPassenger(Scanner scanner, TaxiCompanyDataBase taxiCompanyDataBase) {
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
        taxiCompanyDataBase.addPassenger(passenger);

    }

    public static void registerDriver(Scanner scanner, Driver driver, TaxiCompanyDataBase taxiCompanyDataBase) {
        System.out.println("enter driver information:\n" + "first name:");
        driver.setFirstName(scanner.nextLine());
        System.out.println("last name:");
        driver.setLastName(scanner.nextLine());
        System.out.println("username(it is your national code):");
        driver.setNationalCode(scanner.nextLine());
        System.out.println("age:");
        driver.setAge(scanner.nextInt());
        taxiCompanyDataBase.addDriver(driver);
    }

}
