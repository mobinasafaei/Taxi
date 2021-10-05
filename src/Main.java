import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();
    }

    public void showMainMeu() {
        System.out.println("what do you want to do :\n" +
                "1)add a group of drivers\n" +
                "2)add a group of passengers\n" +
                "3)driver sign up or login\n" +
                "4)passenger sign up or login\n" +
                "5)show a list of drivers\n" +
                "6)show a list of passengers");
    }
}
