package t3_bojan.coffeemachine;

import java.util.List;

public interface Repository<T> {
    void createTable();

    List<T> getList();

    void insert(T object);
}

