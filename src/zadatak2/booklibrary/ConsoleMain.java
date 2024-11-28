package zadatak2.booklibrary;

import java.util.List;
import java.util.Scanner;

public class ConsoleMain {
//    public static void main(String[] args) {
//
//        BookAuthorManager bookManager = new BookAuthorManager();
//        Scanner sc = new Scanner(System.in);
//        boolean isRunning = true;
//
//        while (isRunning) {
//            System.out.println("Book Menu");
//            System.out.println("1. - Add new book");
//            System.out.println("2. - Display all books");
//            System.out.println("3. - Search book by title");
//            System.out.println("4. - Search book by author");
//            System.out.println("0. - Exit");
//            System.out.print("Enter choice: ");
//
//            String choice = sc.nextLine();
//
//            switch (choice) {
//                case "1":
//                    viewAddNewBook(bookManager, sc);
//                    break;
//                case "2":
//                    viewDisplayAllBooks(bookManager);
//                    break;
//                case "3":
//                    viewSearchBookByTitle(bookManager, sc);
//                    break;
//                case "4":
//                    viewSearchBookByAuthor(bookManager, sc);
//                    break;
//                case "0":
//                    isRunning = false;
//                    break;
//            }
//        }
//    }
//
//    public static void viewAddNewBook(BookAuthorManager bookManager, Scanner sc) {
//        System.out.println("\nAdd a new book");
//        System.out.print("Enter book title: ");
//        String title =  sc.nextLine();
//        System.out.print("Enter year of publication: ");
//        int yearOfPublication = sc.nextInt();
//        sc.nextLine();
//        Book book = new Book(title, yearOfPublication);
//        while (true) {
//            System.out.print("Enter author's first name: ");
//            String firstName = sc.nextLine();
//            System.out.print("Enter author's last name: ");
//            String lastName = sc.nextLine();
//            book.addAuthor(new Author(firstName, lastName));
//            System.out.print("Add more authors (Yes|No): ");
//            String moreAuthors = sc.nextLine();
//            // TODO - popraviti logiku kod unosa vise autora
//            if (moreAuthors.equalsIgnoreCase("no"))
//                break;
//        }
//        bookManager.addBook(book);
//    }
//
//    public static void viewDisplayAllBooks(BookAuthorManager bookManager) {
//        printBookAndAuthor(bookManager.getAllBooks());
//    }
//
//    public static void viewSearchBookByTitle(BookAuthorManager bookManager, Scanner sc){
//        System.out.println("\nSearch book by title");
//        System.out.print("Enter book title: ");
//        String title = sc.nextLine();
//        List<Book> books = bookManager.findBookByTitle(title);
//        if (books.isEmpty()) {
//            System.out.println("No books found with title: " + title);
//        } else {
//            printBookAndAuthor(books);
//        }
//    }
//
//    public static void viewSearchBookByAuthor(BookAuthorManager bookManager, Scanner sc){
//        System.out.println("\nSearch book by author");
//        System.out.print("Enter author first name: ");
//        String firstName = sc.nextLine();
//        System.out.print("Enter author last name: ");
//        String lastName = sc.nextLine();
//        Author author = new Author(firstName, lastName);
//        List<Book> books = bookManager.findBooksByAuthor(author);
//        if (books.isEmpty()) {
//            System.out.println("No books found from author: " + author.getFirstName() + " " + author.getLastName());
//        } else {
//            printBookAndAuthor(books);
//        }
//    }
//
//    private static void printBookAndAuthor(List<Book> books) {
//        for (Book book : books) {
//            System.out.println(book.toString());
//        }
//    }
}
