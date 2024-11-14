package zadatak2.booklibrary;

import java.util.ArrayList;

public class Book {

    private int bookId;
    private String bookTitle;
    private int yearOfPublication;

    private ArrayList<Author> authors;

    public Book (int bookId, String bookTitle, int yearOfPublication) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.yearOfPublication = yearOfPublication;
    }

    public int getBookId() {
        return bookId;
    }

    @Override
    public String toString() {
        return "Book Id: " + bookId + ", Book title: " + bookTitle + ", Year of publication: " + yearOfPublication;

    }
}
