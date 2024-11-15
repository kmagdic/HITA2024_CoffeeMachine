package zadatak2.booklibrary;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleMain {

    public static void main(String[] args) {

        Library library = new Library();

        Scanner scanner = new Scanner(System.in);
        String choice = "";

        while (!choice.equals("0")) {
            System.out.println("Enter choice:");
            System.out.println("1. - Add new book");
            System.out.println("2. - List all books");
            System.out.println("3. - Remove book by id");
            System.out.println("0. - Exit");

            choice = scanner.nextLine();
            switch (choice) {
                case ("1") : {
                    int bookId = library.getLastId() + 1;
                    System.out.println("  Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.println("  Enter year of publication: ");
                    int yearOfPublication = scanner.nextInt();
                    library.addBook(new Book(bookId, title, yearOfPublication));
                    scanner.nextLine();
                    break;
                }
                case ("2"):
                    ArrayList<Book> books = library.getBookList();
                    for (Book b : books) {
                        System.out.println(b.toString());
                    }
                    break;
                case ("3"):
                    System.out.println("  Enter book id to remove: ");
                    int bookId = scanner.nextInt();
                    library.removeBookById(bookId);
                    break;
            }
        }
    }
}
