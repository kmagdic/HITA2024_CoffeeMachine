package zadatak2.booklibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleMain {

    public static void main(String[] args) throws SQLException {
        Author authorBosco = new Author("Don", "Bosco");
        Author authorPio = new Author("Padre", "Pio");

        Book b1 = new Book("Snovi", 1850, authorBosco);// bosco
        Book b2 = new Book("Uvijek budi radostan", 1888, authorBosco); // bosco
        Book b3 = new Book("Riječi nade", 1950, authorPio); // pio
        Book b4 = new Book("Stigma vjere", 1936, authorPio); // pio
        Book b5 = new Book("Glasnik nove zemlje", 1944,authorPio );

        Connection connection = DriverManager.getConnection("jdbc:h2:./docs/bookauthortest");

        AuthorRepository authorRepository = new AuthorRepository(connection);
        BookRepository bookRepository = new BookRepository(connection);

        authorRepository.createTable();
        bookRepository.createTable();

        authorRepository.insertAuthor(authorBosco);
        authorRepository.insertAuthor(authorPio);

        bookRepository.insert(b1);
        bookRepository.insert(b2);
        bookRepository.insert(b3);
        bookRepository.insert(b4);
        bookRepository.insert(b5);

        System.out.println("Author repository: " + authorRepository.getAll());

        // System.out.println("Book repository: " + bookRepository.getList());
        for (Book b : bookRepository.getList()) {
            System.out.println(b.toString());
        }

        System.out.println("saved");
    }
}
