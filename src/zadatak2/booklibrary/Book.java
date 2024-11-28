package zadatak2.booklibrary;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int id;
    private String title;
    private int yearOfPublication;
    private Author author;

    public Book() {
    }

    public Book(String title) {
        this.title = title;

    }

    public Book(String title, int yearOfPublication, Author author){
        this.title = title;
        this.yearOfPublication = yearOfPublication;
        this.author = author;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public void setAuthors(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", author=" + author +
                '}';
    }
}
