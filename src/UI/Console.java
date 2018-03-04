package UI;

import Controller.BookController;
import Domain.Book;
import Domain.Client;
import Exceptions.ValidatorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * @author radu.
 */
public class Console {
    private BookController ctrl;

    public Console(BookController ctrl) {
        this.ctrl = ctrl;
    }

    public void runConsole() {
        addBooks();
        addClients();
        printAllBooks();
        printAllClients();
        filterBooks();
        filterClients();
    }

    private void filterBooks() {
        System.out.println("filtered books (the ones written by 'Ken Follett'):");
        Set<Book> books = ctrl.filterBooksByAuthor("Ken Follett");
        books.stream().forEach(System.out::println);
    }

    private void filterClients() {
        System.out.println("filtered clients (the ones whose name contains 'c2'):");
        Set<Client> clients = ctrl.filterClientsByName("c2");
        clients.stream().forEach(System.out::println);
    }

    private void printAllBooks() {
        Set<Book> books = ctrl.getAllBooks();
        books.stream().forEach(System.out::println);
    }

    private void printAllClients() {
        Set<Client> clients = ctrl.getAllClients();
        clients.stream().forEach(System.out::println);
    }

    private void addBooks() {
        while (true) {
            Book b = readBook();
            if (b == null || b.getId() < 0) {
                break;
            }
            try {
                ctrl.addBook(b);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    private void addClients() {
        while (true) {
            Client c = readClient();
            if (c == null || c.getId() < 0) {
                break;
            }
            try {
                ctrl.addClient(c);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    private Book readBook() {
        System.out.println("Read book {id, title, author, price, stock}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Integer id = Integer.valueOf(bufferRead.readLine());// ...
            String title = bufferRead.readLine();
            String author = bufferRead.readLine();
            Integer price = Integer.valueOf(bufferRead.readLine());// ...
            Integer stock = Integer.valueOf(bufferRead.readLine());

            Book b = new Book(title, author, price, stock);
            b.setId(id);

            return b;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Client readClient() {
        System.out.println("Read client {id, name}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Integer id = Integer.valueOf(bufferRead.readLine());// ...
            String name = bufferRead.readLine();

            Client c = new Client(name);
            c.setId(id);

            return c;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
