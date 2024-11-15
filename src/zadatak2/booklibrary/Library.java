package zadatak2.booklibrary;

import java.util.ArrayList;

public class Library {

    // list of books
    private ArrayList<Book> bookList = new ArrayList<>();

    public Library() {
        initInventory();
    }

    private void initInventory() {
        bookList.add(new Book(1, "Snovi", 1850));
        bookList.add(new Book(2, "Uvijek budi radostan", 1888));
        bookList.add(new Book(3,"Riječi nade", 1950));
        bookList.add(new Book(4, "Stigma vjere", 1936));
        bookList.add(new Book(5,"Glasnik nove zemlje", 1944 ));
    }

    public int getLastId() {
        return 0; // TODO treba ispraviti
        // bookList.getLast().getBookId();
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    // add book to list
    public void addBook(Book book) {
        bookList.add(book);
    }

    // remove by id
    public void removeBookById(int id) {
        for (Book b : bookList) {
            if (b.getBookId() == id) {
                bookList.remove(b);
            }
        }
    }




}
