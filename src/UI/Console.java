package UI;

import Controller.BookController;
import Domain.Book;
import Domain.Client;
import Exceptions.ValidatorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Set;

/**
 * @author CeleDouaFantastice.
 */
public class Console {
    private BookController ctrl;

    public Console(BookController ctrl) {
        this.ctrl = ctrl;
    }

    public void runConsole() {
        runMenu();
    }

    private void filterBooks() {
        System.out.println("Enter author name: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try{
            String author=bufferRead.readLine();
            Set<Book> books = ctrl.filterBooksByAuthor(author);
            books.stream().forEach(System.out::println);
        }
        catch(IOException e){
            e.printStackTrace();
        }
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

    private void runMenu() {
        while(true){
            System.out.print("1 - Read new Book\n" +
                    "2 - Read new Client\n" +
                    "3 - Print all Books\n" +
                    "4 - Print all clients\n" +
                    "5 - Filter books\n" +
                    "6 - Filter clients\n" +
                    "0 - Exit\n");
            Scanner sc = new Scanner(System.in);
            int cmd = sc.nextInt();
            switch (cmd)
            {
                case 0:
                {System.exit(0);}
                case 1:
                { readBook();
                    break; }
                case 2:
                { readClient();
                    break; }
                case 3:
                { printAllBooks();
                    break; }
                case 4:
                { printAllClients();
                    break; }
                case 5:
                { filterBooks();
                    break; }
                case 6:
                { filterClients();
                    break; }
                default:
                { System.out.println("Invalid command!\n"); }
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
            ctrl.addBook(b);
            return b;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (ValidatorException e){
            System.out.println(e);
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
            ctrl.addClient(c);
            return c;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        catch(ValidatorException e){
            System.out.println(e);
        }
        return null;
    }
}
