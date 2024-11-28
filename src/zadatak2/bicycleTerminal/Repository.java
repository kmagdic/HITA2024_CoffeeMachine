package zadatak2.bicycleTerminal;


import java.sql.Connection;
import java.util.List;

public interface Repository<T> {

    void createTable();

    List<T> getList();

    void insert(T object);
}
