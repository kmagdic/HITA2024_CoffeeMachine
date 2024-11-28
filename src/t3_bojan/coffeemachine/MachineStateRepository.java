package t3_bojan.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MachineStateRepository implements Repository<MachineState> {
    Connection connection;
    private static final String TABLE_NAME = "machine_state";

    public MachineStateRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                "id integer PRIMARY KEY auto_increment, \n" +
                "water integer NOT NULL, \n" +
                "milk integer NOT NULL, \n" +
                "coffee_beans integer NOT NULL, \n" +
                "cups integer NOT NULL, \n" +
                "money float NOT NULL\n" +
                ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MachineState> getList() {
        String sqlSelect = "SELECT * FROM " + TABLE_NAME;

        List<MachineState> machineStates = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelect);

            while (resultSet.next()) {
                MachineState machineState = new MachineState();
                machineState.setId(resultSet.getInt("id"));
                machineState.setWater(resultSet.getInt("water"));
                machineState.setMilk(resultSet.getInt("milk"));
                machineState.setCoffeeBeans(resultSet.getInt("coffee_beans"));
                machineState.setCups(resultSet.getInt("cups"));
                machineState.setMoney(resultSet.getFloat("money"));

                machineStates.add(machineState);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return machineStates;
    }

    @Override
    public void insert(MachineState machineState) {

        String sqlInsert = "INSERT INTO " + TABLE_NAME + " (water, milk, coffee_beans, cups, money) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setInt(1, machineState.getWater());
            statement.setInt(2, machineState.getMilk());
            statement.setInt(3, machineState.getCoffeeBeans());
            statement.setInt(4, machineState.getCups());
            statement.setFloat(5, machineState.getMoney());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(MachineState machineState) {
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET " +
                    "water = ?, " +
                    "milk = ?, " +
                    "coffee_beans = ?, " +
                    "cups = ?, " +
                    "money = ? " +
                    "WHERE id = 1";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, machineState.getWater());
            stmt.setInt(2, machineState.getMilk());
            stmt.setInt(3, machineState.getCoffeeBeans());
            stmt.setInt(4, machineState.getCups());
            stmt.setFloat(5, machineState.getMoney());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isTableIsEmpty() {
        try {
            String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0){
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}