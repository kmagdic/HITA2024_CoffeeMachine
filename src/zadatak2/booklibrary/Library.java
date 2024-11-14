package zadatak2.booklibrary;

import java.util.ArrayList;
import java.util.List;

public class Library {

    // list of books
    private ArrayList<Book> inventory = new ArrayList<>();

    public Library() {
        initInventory();
    }

    private void initInventory() {
        inventory.add(new Book(1, "Snovi", 1850));
        inventory.add(new Book(2, "Uvijek budi radostan", 1888));
        inventory.add(new Book(3,"Riječi nade", 1950));
        inventory.add(new Book(4, "Stigma vjere", 1936));
        inventory.add(new Book(5,"Glasnik nove zemlje", 1944 ));
    }

    public int getLastId() {
        return inventory.getLast().getBookId();
    }

    public ArrayList<Book> getInventory() {
        return inventory;
    }

    // add book to list
    public void addBook(Book book) {
        inventory.add(book);
    }

    // remove by id
    public void removeBookById(int id) {
        for (Book b : inventory) {
            if (b.getBookId() == id) {
                inventory.remove(b);
            }
        }
    }




}
