package t1_mateo.coffeemachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CoffeeMachineWithStatusInFile extends CoffeeMachine{


    public CoffeeMachineWithStatusInFile(int water, int milk, int coffeeBeans, int cups, float money) {
        super(water, milk, coffeeBeans, cups, money);
    }

    public boolean loadFromFile(String fileName)  {
        FileReader reader = null;

        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }

        Scanner fileScanner = new Scanner(reader);

        // FILE format:
        // <water_status>; <milk_status>; <coffee_beans_status>; <cups_status>; <money_status>
        // <admin_username>; <admin_password>

        fileScanner.useDelimiter("; |\n"); // delimiter is "; " or "\n" (for the last value)

        super.setWater(fileScanner.nextInt());
        super.setMilk(fileScanner.nextInt());
        super.setCoffeeBeans(fileScanner.nextInt());
        super.setCups(fileScanner.nextInt());
        super.setMoney(Float.parseFloat(fileScanner.next()));

        super.setAdminUsername(fileScanner.next());
        super.setAdminPassword((fileScanner.next().trim()));

        return true;
    }

    public void saveToFile(String fileName){
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(super.getWater() + "; " +  super.getMilk() + "; " + super.getCoffeeBeans() + "; " + super.getCups() + "; " + super.getMoney());
            writer.write("\n");
            writer.write(super.getAdminUsername() + "; " + super.getAdminPassword());
            writer.write("\n");

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean start() {
        return loadFromFile(getStatusFileName());
    }

    public void stop() {
        saveToFile(getStatusFileName());
    }

}
