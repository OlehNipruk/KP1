

import service.ManufacturerService;
import service.SouvenirService;
import view.ConsoleView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ManufacturerService manufacturerService = new ManufacturerService();
        SouvenirService souvenirService = new SouvenirService();

        ConsoleView consoleView = new ConsoleView(manufacturerService, souvenirService);


        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            consoleView.printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    consoleView.addManufacturer(manufacturerService, scanner);
                    break;
                case 2:
                    consoleView.editManufacturer(manufacturerService, scanner);
                    break;
                case 3:
                    consoleView.viewAllManufacturers(manufacturerService);
                    break;
                case 4:
                    consoleView.addSouvenir(souvenirService, manufacturerService, scanner);
                    break;
                case 5:
                    consoleView.editSouvenir(souvenirService, scanner);
                    break;
                case 6:
                    consoleView.viewAllSouvenirs(souvenirService);
                    break;
                case 7:
                    consoleView.viewSouvenirsByManufacturer(manufacturerService, souvenirService, scanner);
                    break;
                case 8:
                    consoleView.viewSouvenirsByCountry(souvenirService, scanner);
                    break;
                case 9:
                    consoleView.viewManufacturersByPrice(manufacturerService, souvenirService, scanner);
                    break;
                case 10:
                    consoleView.viewAllManufacturersWithSouvenirs(manufacturerService, souvenirService);
                    break;
                case 11:
                    consoleView.deleteManufacturer(manufacturerService, souvenirService, scanner);
                    break;

                case 12:
                    consoleView.viewManufacturersForSouvenirByYear(souvenirService, scanner);
                    break;
                case 13:
                    consoleView.viewSouvenirsByYear(souvenirService, scanner);
                    break;
                case 14:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }
}