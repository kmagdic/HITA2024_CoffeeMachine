package t2_tomislav.coffeemachine;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CoffeeMachineWithStatusInFile extends CoffeeMachine {

    private final String statusFileName;

    public CoffeeMachineWithStatusInFile(int water, int milk, int coffeeBeans, int cups, float money, String statusFileName) {
        super(water, milk, coffeeBeans, cups, money);
        this.statusFileName = statusFileName;
    }

    public boolean loadFromFile() {
        try {
            File file = new File(statusFileName);
            if (!file.exists()) {
                System.out.println("File not found. Using default values.");
                saveToFile();
                return true;
            }
            FileReader reader = new FileReader(file);
            Scanner fileScanner = new Scanner(reader);
            fileScanner.useDelimiter("; |\\n");

            setWater(fileScanner.nextInt());
            setMilk(fileScanner.nextInt());
            setCoffeeBeans(fileScanner.nextInt());
            setCups(fileScanner.nextInt());
            setMoney(Float.parseFloat(fileScanner.next()));
            reader.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error while loading file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error in file format: " + e.getMessage());
            return false;
        }
    }

    public void saveToFile() {
        try {
            File file = new File(statusFileName);
            if (file.getParentFile() != null && !file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                System.out.println("Failed to create directories for file: " + statusFileName);
            }
            if (!file.exists() && file.createNewFile()) {
                System.out.println("File successfully created: " + statusFileName);
            }
            FileWriter writer = new FileWriter(file);
            writer.write(getWater() + "; " + getMilk() + "; " + getCoffeeBeans() + "; " + getCups() + "; " + getMoney() + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error saving file: " + e.getMessage());
        }
    }
}
