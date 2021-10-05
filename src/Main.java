import enums.VehicleColor;

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
                    System.out.println("how many driver you want to register");
                    int n = scanner.nextInt();
                    for (int i = 0; i < n; i++) {
                        Driver driver = new Driver();
                        Vehicle vehicle = new Vehicle();
                        System.out.println("enter driver information:\n" + "first name:");
                        driver.setFirstName(scanner.nextLine());
                        System.out.println("last name:");
                        driver.setLastName(scanner.nextLine());
                        System.out.println("username(it is your national code):");
                        driver.setLastName(scanner.nextLine());
                        System.out.println("age:");
                        driver.setAge(scanner.nextInt());
                        taxiCompanyDataBase.addDriver(driver);
                        System.out.println("enter driver vehicle information:\nname:");
                        vehicle.setName(scanner.nextLine());
                        System.out.println("tag:");
                        vehicle.setTag(scanner.nextLine());
                        System.out.println("choose color:\n1)blue\n2)white\n3)black");
                        int chooseColor=scanner.nextInt();
                        if(chooseColor==1){
                            vehicle.setColor(VehicleColor.BLUE.getVehicleColor());
                        }else if(chooseColor==2){
                            vehicle.setColor(VehicleColor.WHITE.getVehicleColor());
                        }else if(chooseColor==3){
                            vehicle.setColor(VehicleColor.BLACK.getVehicleColor());
                        }
                        System.out.println("vehicle type:");
                        vehicle.setVehicleType(scanner.nextLine());
                        taxiCompanyDataBase.addVehicle(vehicle,driver.getNationalCode());
                    }
                    break;
                }
                case 2: {
                    System.out.println();
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
}
