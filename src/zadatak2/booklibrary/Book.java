package zadatak2.booklibrary;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private int yearOfPublication;
    private List<Author> authors;

    public Book(String title) {
        this.title = title;
        this.authors = new ArrayList<>();
    }

    public Book(String title, int yearOfPublication){
        this.title = title;
        this.yearOfPublication = yearOfPublication;
        this.authors = new ArrayList<>();
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    @Override
    public String toString() {
        return this.title + " " + this.yearOfPublication + " " + authors;
    }
}
