package t2_patricija.coffeemachine;
import java.sql.SQLException;
import java.util.List;

//Java recognizes a class, interface, or method as generic because of the angle brackets (<>) used in its definition.
// When you declare a type with angle brackets, such as <T>, <E>, or any custom name,
// Java understands that this is a type parameter that can represent different types
// when the generic class, interface, or method is instantiated or invoked.

public interface GenericDao<T> {
    List<T> findAll(); // Retrieve a list of all entities of type T
    T findById(int id); // Find an entity by its ID
    void add(T entity); // Add a new entity
    void update(T entity); // Update an existing entity
    void deleteById(int id) throws SQLException; // Delete an entity by its ID


}
