package view;
import model.Manufacturer;
import model.Souvenir;
import service.ManufacturerService;
import service.SouvenirService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private ManufacturerService manufacturerService;
    private SouvenirService souvenirService;

    public ConsoleView(ManufacturerService manufacturerService, SouvenirService souvenirService) {
        this.manufacturerService = manufacturerService;
        this.souvenirService = souvenirService;
    }

    public void printMainMenu() {
        System.out.println("1. Add Manufacturer");
        System.out.println("2. Edit Manufacturer");
        System.out.println("3. View All Manufacturers");
        System.out.println("4. Add Souvenir");
        System.out.println("5. Edit Souvenir");
        System.out.println("6. View All Souvenirs");
        System.out.println("7. View Souvenirs by Manufacturer");
        System.out.println("8. View Souvenirs by Country");
        System.out.println("9. View Manufacturers by Price");
        System.out.println("10. View All Manufacturers with Souvenirs");
        System.out.println("11. Delete Manufacturer");
        System.out.println("12. View Manufacturers for Souvenir by Year");
        System.out.println("13. View Souvenirs by Year");
        System.out.println("14. Exit");
        System.out.print("Enter your choice: ");
    }

    public void addManufacturer(ManufacturerService manufacturerService, Scanner scanner) {
        System.out.print("Enter manufacturer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter country: ");
        String country = scanner.nextLine();

        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturerService.addManufacturer(manufacturer);
        System.out.println("Manufacturer added successfully.");
    }

    public void editManufacturer(ManufacturerService manufacturerService, Scanner scanner) {
        System.out.print("Enter manufacturer ID to edit: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Manufacturer manufacturer = manufacturerService.getManufacturerById(id);
        if (manufacturer != null) {
            System.out.print("Enter new manufacturer name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new country: ");
            String country = scanner.nextLine();

            manufacturer.setName(name);
            manufacturer.setCountry(country);

            manufacturerService.updateManufacturer(manufacturer);
            System.out.println("Manufacturer updated successfully.");
        } else {
            System.out.println("Manufacturer not found.");
        }
    }

    public void viewAllManufacturers(ManufacturerService manufacturerService) {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        if (manufacturers.isEmpty()) {
            System.out.println("No manufacturers found.");
        } else {
            System.out.println("All Manufacturers:");
            for (Manufacturer manufacturer : manufacturers) {
                System.out.println(manufacturer);
            }
        }
    }

    public void addSouvenir(SouvenirService souvenirService, ManufacturerService manufacturerService, Scanner scanner) {
        System.out.print("Enter souvenir name: ");
        String name = scanner.nextLine();

        viewAllManufacturers(manufacturerService);
        System.out.print("Enter manufacturer ID for the souvenir: ");
        long manufacturerId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter release date (yyyy-MM-dd): ");
        String releaseDateStr = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = dateFormat.parse(releaseDateStr);
            Souvenir souvenir = new Souvenir(name, manufacturerId, releaseDate, price);
            souvenirService.addSouvenir(souvenir);
            System.out.println("Souvenir added successfully.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Souvenir not added.");
        }
    }

    public void editSouvenir(SouvenirService souvenirService, Scanner scanner) {
        System.out.print("Enter souvenir ID to edit: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Souvenir souvenir = souvenirService.getSouvenirById(id);
        if (souvenir != null) {
            System.out.print("Enter new souvenir name: ");
            String name = scanner.nextLine();

            System.out.print("Enter new manufacturer ID for the souvenir: ");
            long manufacturerId = scanner.nextLong();
            scanner.nextLine();

            System.out.print("Enter new release date (yyyy-MM-dd): ");
            String releaseDateStr = scanner.nextLine();

            System.out.print("Enter new price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date releaseDate = dateFormat.parse(releaseDateStr);
                souvenir.setName(name);
                souvenir.setManufacturerId(manufacturerId);
                souvenir.setReleaseDate(releaseDate);
                souvenir.setPrice(price);

                souvenirService.updateSouvenir(souvenir);
                System.out.println("Souvenir updated successfully.");
            } catch (ParseException e) {
                System.out.println("Invalid date format. Souvenir not updated.");
            }
        } else {
            System.out.println("Souvenir not found.");
        }
    }

    public void viewAllSouvenirs(SouvenirService souvenirService) {
        List<Souvenir> souvenirs = souvenirService.getAllSouvenirs();
        if (souvenirs.isEmpty()) {
            System.out.println("No souvenirs found.");
        } else {
            System.out.println("All Souvenirs:");
            for (Souvenir souvenir : souvenirs) {
                printSouvenir(souvenir);
            }
        }
    }

    public void viewSouvenirsByManufacturer(ManufacturerService manufacturerService, SouvenirService souvenirService, Scanner scanner) {
        viewAllManufacturers(manufacturerService);
        System.out.print("Enter manufacturer ID to view souvenirs: ");
        long manufacturerId = scanner.nextLong();
        scanner.nextLine(); // Clear the newline character

        Manufacturer manufacturer = manufacturerService.getManufacturerById(manufacturerId);
        if (manufacturer != null) {
            List<Souvenir> souvenirs = souvenirService.getSouvenirsByManufacturer(manufacturerId);
            if (souvenirs.isEmpty()) {
                System.out.println("No souvenirs found for manufacturer: " + manufacturer.getName());
            } else {
                System.out.println("Souvenirs by Manufacturer " + manufacturer.getName() + ":");
                for (Souvenir souvenir : souvenirs) {
                    printSouvenir(souvenir);
                }
            }
        } else {
            System.out.println("Manufacturer not found.");
        }
    }

    public void viewSouvenirsByCountry(SouvenirService souvenirService, Scanner scanner) {
        System.out.print("Enter country to view souvenirs: ");
        String country = scanner.nextLine();
        List<Souvenir> souvenirs = souvenirService.getSouvenirsByCountry(country);
        if (souvenirs.isEmpty()) {
            System.out.println("No souvenirs found for country: " + country);
        } else {
            System.out.println("Souvenirs by Country " + country + ":");
            for (Souvenir souvenir : souvenirs) {
                printSouvenir(souvenir);
            }
        }
    }

    public void viewManufacturersByPrice(ManufacturerService manufacturerService, SouvenirService souvenirService, Scanner scanner) {
        System.out.print("Enter price to view manufacturers: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Clear the newline character
        List<Manufacturer> manufacturers = manufacturerService.getManufacturersByPrice(price);
        if (manufacturers.isEmpty()) {
            System.out.println("No manufacturers found with souvenirs cheaper than " + price);
        } else {
            System.out.println("Manufacturers with Souvenirs Cheaper Than " + price + ":");
            for (Manufacturer manufacturer : manufacturers) {
                printManufacturer(manufacturer);
            }
        }
    }

    public void viewAllManufacturersWithSouvenirs(ManufacturerService manufacturerService, SouvenirService souvenirService) {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        if (manufacturers.isEmpty()) {
            System.out.println("No manufacturers found.");
        } else {
            System.out.println("Manufacturers with their Souvenirs:");
            for (Manufacturer manufacturer : manufacturers) {
                printManufacturer(manufacturer);
                List<Souvenir> souvenirs = souvenirService.getSouvenirsByManufacturer(manufacturer.getId());
                if (souvenirs.isEmpty()) {
                    System.out.println("No souvenirs found for manufacturer: " + manufacturer.getName());
                } else {
                    for (Souvenir souvenir : souvenirs) {
                        printSouvenir(souvenir);
                    }
                }
                System.out.println("--------");
            }
        }
    }

    public void deleteManufacturer(ManufacturerService manufacturerService, SouvenirService souvenirService, Scanner scanner) {
        System.out.print("Enter manufacturer ID to delete: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Manufacturer manufacturer = manufacturerService.getManufacturerById(id);
        if (manufacturer != null) {
            manufacturerService.deleteManufacturer(manufacturer);
            souvenirService.deleteSouvenirsByManufacturer(manufacturer.getId());
            System.out.println("Manufacturer and associated souvenirs deleted successfully.");
        } else {
            System.out.println("Manufacturer not found.");
        }
    }


    public void viewManufacturersForSouvenirByYear(SouvenirService souvenirService, Scanner scanner) {
        System.out.print("Enter souvenir name: ");
        String souvenirName = scanner.nextLine();

        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        List<Manufacturer> manufacturers = souvenirService.getManufacturersForSouvenirByYear(souvenirName, year);
        if (manufacturers.isEmpty()) {
            System.out.println("No manufacturers found for souvenir: " + souvenirName + " in year " + year);
        } else {
            System.out.println("Manufacturers for Souvenir " + souvenirName + " in year " + year + ":");
            for (Manufacturer manufacturer : manufacturers) {
                printManufacturer(manufacturer);
            }
        }
    }

    public void viewSouvenirsByYear(SouvenirService souvenirService, Scanner scanner) {
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        List<Souvenir> souvenirs = souvenirService.getSouvenirsByYear(year);
        if (souvenirs.isEmpty()) {
            System.out.println("No souvenirs found for year " + year);
        } else {
            System.out.println("Souvenirs for Year " + year + ":");
            for (Souvenir souvenir : souvenirs) {
                printSouvenir(souvenir);
            }
        }
    }


    public void printSouvenir(Souvenir souvenir) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String releaseDateStr = dateFormat.format(souvenir.getReleaseDate());
        System.out.println("Souvenir{" +
                "id=" + souvenir.getId() +
                ", name='" + souvenir.getName() + '\'' +
                ", manufacturerId=" + souvenir.getManufacturerId() +
                ", releaseDate=" + releaseDateStr +
                ", price=" + souvenir.getPrice() +
                '}');
    }

    public void printManufacturer(Manufacturer manufacturer) {
        System.out.println("Manufacturer{" +
                "id=" + manufacturer.getId() +
                ", name='" + manufacturer.getName() + '\'' +
                ", country='" + manufacturer.getCountry() + '\'' +
                '}');
    }

}
