package t3_bojan.coffeemachine;

import java.sql.Connection;
import java.util.List;

public class CoffeeMachineWithDB extends CoffeeMachine{

    private MachineStateRepository machineStateRepository;

    public CoffeeMachineWithDB(Connection connection, int water, int milk, int coffeeBeans, int cups, float money) {
        super(water, milk, coffeeBeans, cups, money);
        machineStateRepository = new MachineStateRepository(connection);
        machineStateRepository.createTable();
    }

    public boolean start(Connection connection) {
        isLoadedFromDB = loadFromDB();
        super.start(connection);
        return isLoadedFromDB;
    }

    private boolean loadFromDB() {
        List<MachineState> machineStateList = machineStateRepository.getList();

        if (machineStateList.size() > 0) {
            MachineState machineState = machineStateList.get(0);
            this.water = machineState.getWater();
            this.milk = machineState.getMilk();
            this.coffeeBeans = machineState.getCoffeeBeans();
            this.cups = machineState.getCups();
            this.money = machineState.getMoney();
            return true;
        } else {
            return false;
        }
    }

    public void stop() {
        if (machineStateRepository.isTableIsEmpty()) {
            machineStateRepository.insert(new MachineState(this.water, this.milk, this.coffeeBeans, this.cups, this.money));
        }
        else {
            machineStateRepository.update(new MachineState(this.water, this.milk, this.coffeeBeans, this.cups, this.money));
        }
    }
}
