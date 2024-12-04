package t5_marin.coffeemachine;

import java.sql.*;
import java.util.*;

public class CoffeeMachineManagerConsole {

    private static final Scanner sc = new Scanner(System.in);
    private static Connection connection;

    // DAOs
    private static ManagerEntryDAO managerEntryDAO;
    private static LocationDAO locationDAO;
    private static CoffeeTypeDAO coffeeTypeDAO;

    public static void main(String[] args) {
        try {
            // Establishing connection to the database
            connection = DriverManager.getConnection("jdbc:h2:./src/t5_marin/coffeemachine/coffee_machine_db.mv.db", "marin", "");
            managerEntryDAO = new ManagerEntryDAO(connection);
            locationDAO = new LocationDAO(connection);
            coffeeTypeDAO = new CoffeeTypeDAO();

            CoffeeMachineDatabaseCheck dbCheck = new CoffeeMachineDatabaseCheck(connection);
            dbCheck.checkAndCreateTables();

            run();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    private static void run() throws SQLException {
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add New Manager Entry");
            System.out.println("2. Update Manager Entry");
            System.out.println("3. List Manager Entries");
            System.out.println("4. Delete Manager Entry");
            System.out.println("5. Inspect Coffee Machine");
            System.out.println("6. Location CRUD Operations");
            System.out.println("7. Coffee Type CRUD Operations");
            System.out.println("8. Exit");

            int option = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    addNewManagerEntry();
                    break;
                case 2:
                    updateManagerEntry();
                    break;
                case 3:
                    listManagerEntries();
                    break;
                case 4:
                    deleteManagerEntry();
                    break;
                case 5:
                    inspectCoffeeMachine();
                    break;
                case 6:
                    locationCrudOperations();
                    break;
                case 7:
                    coffeeTypeCrudOperations();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // 1. Add New Manager Entry
    private static void addNewManagerEntry() throws SQLException {
        System.out.println("Do you want to add an existing Coffee Machine or create a new one?");
        System.out.println("1. Add existing Coffee Machine");
        System.out.println("2. Create new Coffee Machine");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        CoffeeMachine coffeeMachine = null;

        if (choice == 1) {
            // Add existing Coffee Machine
            System.out.println("Enter existing Coffee Machine ID:");
            int coffeeMachineId = sc.nextInt();
            sc.nextLine(); // Consume newline

            // Fetch the coffee machine by ID
            coffeeMachine = new CoffeeMachineDAO(connection).readCoffeeMachineById(coffeeMachineId);

            if (coffeeMachine == null) {
                System.out.println("Coffee Machine with ID " + coffeeMachineId + " not found.");
                return;
            }
        } else if (choice == 2) {
            // Create new Coffee Machine - invoke CRUD to create new coffee machine
            coffeeMachine = createNewCoffeeMachine();
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
            return;
        }

        // Ask for location
        System.out.println("Enter Location ID:");
        int locationId = sc.nextInt();
        sc.nextLine(); // Consume newline

        // Fetch the location object
        Location location = locationDAO.readLocationById(locationId);

        if (coffeeMachine != null && location != null) {
            // Proceed to create the Manager Entry
            Timestamp timeOfInstalment = new Timestamp(System.currentTimeMillis());
            CoffeeMachineManagerEntry entry = new CoffeeMachineManagerEntry(0, coffeeMachine, location, timeOfInstalment);
            managerEntryDAO.createManagerEntry(entry);

            System.out.println("New Manager Entry added with ID: " + entry.getId());
        } else {
            System.out.println("Invalid Coffee Machine or Location.");
        }
    }

    private static CoffeeMachine createNewCoffeeMachine() throws SQLException {
        // Prompt user for new coffee machine details
        System.out.println("Enter amount of Water (in ml):");
        int water = sc.nextInt();

        System.out.println("Enter amount of Milk (in ml):");
        int milk = sc.nextInt();

        System.out.println("Enter amount of Coffee Beans (in grams):");
        int coffeeBeans = sc.nextInt();

        System.out.println("Enter number of Cups:");
        int cups = sc.nextInt();

        System.out.println("Enter amount of Money (in currency):");
        float money = sc.nextFloat();
        sc.nextLine(); // Consume newline

        // Create a new CoffeeMachine object using the collected data
        CoffeeMachine newCoffeeMachine = new CoffeeMachine(0, water, milk, coffeeBeans, cups, money);

        // Assuming CoffeeMachineDAO has an insert method for creating a new coffee machine
        CoffeeMachineDAO coffeeMachineDAO = new CoffeeMachineDAO(connection);
        coffeeMachineDAO.createCoffeeMachine(newCoffeeMachine); // Create and insert the new coffee machine in the DB

        System.out.println("New Coffee Machine created with ID: " + newCoffeeMachine.getId());

        return newCoffeeMachine;
    }

    // 2. Update Manager Entry
    private static void updateManagerEntry() throws SQLException {
        System.out.println("Enter Manager Entry ID to update:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline

        CoffeeMachineManagerEntry entry = managerEntryDAO.readManagerEntryById(id);
        if (entry != null) {
            System.out.println("Enter new Coffee Machine ID:");
            int coffeeMachineId = sc.nextInt();
            System.out.println("Enter new Location ID:");
            int locationId = sc.nextInt();
            sc.nextLine(); // Consume newline

            CoffeeMachine coffeeMachine = new CoffeeMachineDAO(connection).readCoffeeMachineById(coffeeMachineId);
            Location location = locationDAO.readLocationById(locationId);

            if (coffeeMachine != null && location != null) {
                Timestamp timeOfInstalment = new Timestamp(System.currentTimeMillis());

                // Update Manager Entry
                entry.setCoffeeMachine(coffeeMachine);
                entry.setLocation(location);
                entry.setTimeOfInstalment(timeOfInstalment);

                managerEntryDAO.updateManagerEntry(entry);
                System.out.println("Manager Entry updated.");
            } else {
                System.out.println("Invalid Coffee Machine or Location.");
            }
        } else {
            System.out.println("Manager Entry not found.");
        }
    }

    // 3. List Manager Entries
    private static void listManagerEntries() throws SQLException {
        List<CoffeeMachineManagerEntry> entries = managerEntryDAO.readAllManagerEntries();
        if (entries.isEmpty()) {
            System.out.println("No Manager Entries found.");
        } else {
            entries.forEach(entry -> {
                System.out.println("ID: " + entry.getId() +
                        ", Coffee Machine ID: " + entry.getCoffeeMachine().getId() +
                        ", Location ID: " + entry.getLocation().getId() +
                        ", Time of Instalment: " + entry.getTimeOfInstalment());
            });
        }
    }

    // 4. Delete Manager Entry
    private static void deleteManagerEntry() throws SQLException {
        System.out.println("Enter Manager Entry ID to delete:");
        int id = sc.nextInt();
        managerEntryDAO.deleteManagerEntry(id);
        System.out.println("Manager Entry deleted.");
    }

    // 5. Inspect Coffee Machine
    public static void inspectCoffeeMachine() {
        System.out.println("Enter Coffee Machine ID to inspect:");
        int coffeeMachineId = sc.nextInt();

        try {
            // Query to check if the coffee machine exists in any manager entry
            String sql = "SELECT * FROM CoffeeMachineManagerEntry WHERE coffeeMachineId = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, coffeeMachineId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Found the coffee machine in manager entries, activating the console
                    System.out.println("Coffee Machine ID " + coffeeMachineId + " found in manager entries.");
                    activateCoffeeMachineConsole(coffeeMachineId);
                } else {
                    System.out.println("No manager entries found for Coffee Machine ID " + coffeeMachineId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error inspecting coffee machine: " + e.getMessage());
        }
    }

    public static void activateCoffeeMachineConsole(int coffeeMachineId) {
        // Create the CoffeeMachineConsole and activate it
        CoffeeMachineConsole console = new CoffeeMachineConsole(connection, coffeeMachineId);
        console.activate();
    }


    // 6. Location CRUD Operations
    private static void locationCrudOperations() throws SQLException {
        System.out.println("1. Add Location");
        System.out.println("2. Update Location");
        System.out.println("3. List Locations");
        System.out.println("4. Delete Location");
        System.out.println("Enter option:");
        int option = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (option) {
            case 1:
                addLocation();
                break;
            case 2:
                updateLocation();
                break;
            case 3:
                listLocations();
                break;
            case 4:
                deleteLocation();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Add a Location
    private static void addLocation() throws SQLException {
        System.out.println("Enter Drzava:");
        String drzava = sc.nextLine();
        System.out.println("Enter Zupanija:");
        String zupanija = sc.nextLine();
        System.out.println("Enter Postanski Broj:");
        String postanskiBroj = sc.nextLine();
        System.out.println("Enter Adresa:");
        String adresa = sc.nextLine();

        Location location = new Location(0, drzava, zupanija, postanskiBroj, adresa);
        locationDAO.createLocation(location);
        System.out.println("Location added with ID: " + location.getId());
    }

    // Update a Location
    private static void updateLocation() throws SQLException {
        System.out.println("Enter Location ID to update:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline

        Location location = locationDAO.readLocationById(id);
        if (location != null) {
            System.out.println("Enter new Drzava:");
            String drzava = sc.nextLine();
            System.out.println("Enter new Zupanija:");
            String zupanija = sc.nextLine();
            System.out.println("Enter new Postanski Broj:");
            String postanskiBroj = sc.nextLine();
            System.out.println("Enter new Adresa:");
            String adresa = sc.nextLine();

            location.setDrzava(drzava);
            location.setZupanija(zupanija);
            location.setPostanskiBroj(postanskiBroj);
            location.setAdresa(adresa);

            locationDAO.updateLocation(location);
            System.out.println("Location updated.");
        } else {
            System.out.println("Location not found.");
        }
    }

    // List Locations
    private static void listLocations() throws SQLException {
        List<Location> locations = locationDAO.readAllLocations();
        if (locations.isEmpty()) {
            System.out.println("No Locations found.");
        } else {
            locations.forEach(location -> {
                System.out.println("ID: " + location.getId() +
                        ", Drzava: " + location.getDrzava() +
                        ", Zupanija: " + location.getZupanija() +
                        ", Postanski Broj: " + location.getPostanskiBroj() +
                        ", Adresa: " + location.getAdresa());
            });
        }
    }

    // Delete Location
    private static void deleteLocation() throws SQLException {
        System.out.println("Enter Location ID to delete:");
        int id = sc.nextInt();
        locationDAO.deleteLocation(id);
        System.out.println("Location deleted.");
    }

    // 7. Coffee Type CRUD Operations
    private static void coffeeTypeCrudOperations() throws SQLException {
        System.out.println("1. Add Coffee Type");
        System.out.println("2. Update Coffee Type");
        System.out.println("3. List Coffee Types");
        System.out.println("4. Delete Coffee Type");
        System.out.println("Enter option:");
        int option = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (option) {
            case 1:
                addCoffeeType();
                break;
            case 2:
                updateCoffeeType();
                break;
            case 3:
                listCoffeeTypes();
                break;
            case 4:
                deleteCoffeeType();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Add a Coffee Type
    private static void addCoffeeType() throws SQLException {
        System.out.println("Enter Coffee Type Name:");
        String name = sc.nextLine();
        System.out.println("Enter Water Needed (ml):");
        int waterNeeded = sc.nextInt();
        System.out.println("Enter Milk Needed (ml):");
        int milkNeeded = sc.nextInt();
        System.out.println("Enter Coffee Beans Needed (g):");
        int coffeeBeansNeeded = sc.nextInt();
        System.out.println("Enter Price (in currency):");
        float price = sc.nextFloat();
        sc.nextLine(); // Consume newline

        CoffeeType coffeeType = new CoffeeType(0, name, waterNeeded, milkNeeded, coffeeBeansNeeded, price);
        coffeeTypeDAO.insertCoffeeType(coffeeType, connection);
        System.out.println("Coffee Type added with ID: " + coffeeType.getId());
    }

    // Update a Coffee Type
    private static void updateCoffeeType() throws SQLException {
        System.out.println("Enter Coffee Type ID to update:");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline

        CoffeeType coffeeType = coffeeTypeDAO.getCoffeeTypeById(id, connection);
        if (coffeeType != null) {
            System.out.println("Enter new Coffee Type Name:");
            String name = sc.nextLine();
            System.out.println("Enter new Water Needed (ml):");
            int waterNeeded = sc.nextInt();
            System.out.println("Enter new Milk Needed (ml):");
            int milkNeeded = sc.nextInt();
            System.out.println("Enter new Coffee Beans Needed (g):");
            int coffeeBeansNeeded = sc.nextInt();
            System.out.println("Enter new Price (in currency):");
            float price = sc.nextFloat();
            sc.nextLine(); // Consume newline

            coffeeType.setName(name);
            coffeeType.setWaterNeeded(waterNeeded);
            coffeeType.setMilkNeeded(milkNeeded);
            coffeeType.setCoffeeBeansNeeded(coffeeBeansNeeded);
            coffeeType.setPrice(price);

            coffeeTypeDAO.updateCoffeeType(coffeeType, connection);
            System.out.println("Coffee Type updated.");
        } else {
            System.out.println("Coffee Type not found.");
        }
    }

    // List Coffee Types
    private static void listCoffeeTypes() throws SQLException {
        List<CoffeeType> coffeeTypes = coffeeTypeDAO.getAllCoffeeTypes(connection);
        if (coffeeTypes.isEmpty()) {
            System.out.println("No Coffee Types found.");
        } else {
            coffeeTypes.forEach(coffeeType -> {
                System.out.println("ID: " + coffeeType.getId() +
                        ", Name: " + coffeeType.getName() +
                        ", Water: " + coffeeType.getWaterNeeded() +
                        " ml, Milk: " + coffeeType.getMilkNeeded() +
                        " ml, Coffee Beans: " + coffeeType.getCoffeeBeansNeeded() +
                        " g, Price: " + coffeeType.getPrice());
            });
        }
    }

    // Delete Coffee Type
    private static void deleteCoffeeType() throws SQLException {
        System.out.println("Enter Coffee Type ID to delete:");
        int id = sc.nextInt();
        coffeeTypeDAO.deleteCoffeeType(id, connection);
        System.out.println("Coffee Type deleted.");
    }

}
