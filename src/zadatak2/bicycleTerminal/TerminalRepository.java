package zadatak2.bicycleTerminal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TerminalRepository implements Repository<Terminal> {

    Connection connection;

    public TerminalRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
//        this.id = id;
//        this.name = name;
//        this.maxCapacity = maxCapacity;
//        this.isOpen = true;
//        this.bicycles = new ArrayList<>();
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Terminal (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "name VARCHAR(255) NOT NULL,\n " +
                    "max_capacity integer  NOT NULL,\n" +
                    "is_open boolean  NOT NULL,\n" +
                    "bicycle_id integer NULL\n)";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List getList() {
        String sqlPrint = "SELECT * FROM terminal";
        List<Terminal> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlPrint);

            while (resultSet.next()) {
                Terminal terminal = new Terminal();
                terminal.setId(resultSet.getInt("id"));
                terminal.setName(resultSet.getString("name"));
                terminal.setMaxCapacity(resultSet.getInt("max_capacity"));
                terminal.setOpen(resultSet.getBoolean("is_open"));

                resultList.add(terminal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultList;
    }

    @Override
    public void insert(Terminal terminal) {
        String insertSql = "INSERT INTO terminal (name, max_capacity, is_open) VALUES (?, ?, ?)";
//        "id integer PRIMARY KEY auto_increment, \n" +
//                "name  NOT NULL,\n " +
//                "max_capacity integer  NOT NULL,\n" +
//                "is_open boolean  NOT NULL,\n" +
//                "bicycle_id integer NULL\n)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, terminal.getName());
            preparedStatement.setInt(2, terminal.getMaxCapacity());
            preparedStatement.setBoolean(3, terminal.getIsOpen());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
