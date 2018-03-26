package UI;

import Controller.BookController;
import Domain.Book;
import Domain.Client;
import Domain.Purchase;
import Domain.Validators.Validator;
import Exceptions.ValidatorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
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
        try {
            String author = bufferRead.readLine();
            Set<Book> books = ctrl.filterBooksByAuthor(author);
            books.stream().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterClients() {
        System.out.println("Give the string by which the filter will be done: ");
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        System.out.println("filtered clients (the ones whose name contains " + str + "):");
        Set<Client> clients = ctrl.filterClientsByName(str);
        clients.stream().forEach(System.out::println);
    }

    private void buyBooks() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Enter client ID: ");
            Integer clientID = Integer.parseInt(bufferRead.readLine());
            System.out.println("Enter book ID: ");
            Integer bookID = Integer.parseInt(bufferRead.readLine());
            System.out.println("Enter number of books bought: ");
            Integer amount = Integer.parseInt(bufferRead.readLine());
            ctrl.buy(clientID, bookID, amount);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void printPurchases() {
        Set<Purchase> bought = ctrl.getAllPurchases();
        bought.stream().forEach(System.out::println);
    }

    private void printAllBooks() {
        Set<Book> books = ctrl.getAllBooks();
        books.stream().forEach(System.out::println);
    }

    private void printAllClients() {
        Set<Client> clients = ctrl.getAllClients();
        clients.stream().forEach(System.out::println);
    }

    private void sortBooks() {
        ctrl.sortBooks().stream().forEach(System.out::println);
    }

    private void sortClients() {
        ctrl.sortClients().stream().forEach(System.out::println);
    }

    private void sortClientsByAmountBought() {
        ctrl.sortClientsByAmountBought().stream().forEach(System.out::println);
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
        } catch (ValidatorException e) {
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
        } catch (ValidatorException e) {
            System.out.println(e);
        }
        return null;
    }

    private void updateBook() {
        System.out.println("ID of the book to be updated: ");
        Scanner sc = new Scanner(System.in);
        Integer id = sc.nextInt();
        System.out.println("New price: ");
        Integer price = sc.nextInt();
        System.out.println("New stock: ");
        Integer stock = sc.nextInt();
        try {
            ctrl.updateBook(id,price,stock);
        }
        catch (ValidatorException ex)
        {
            ex.printStackTrace();
        }
    }

    private void updateClient() {
        System.out.println("ID of the client to be updated: ");
        Scanner sc = new Scanner(System.in);
        Integer id = sc.nextInt();
        System.out.println("New name: ");
        String name = sc.next();
        try {
            ctrl.updateClient(id,name);
        }
        catch (ValidatorException ex)
        {
            ex.printStackTrace();
        }
    }

    private void deleteBook() {
        System.out.println("ID of the book to be deleted: ");
        Scanner sc = new Scanner(System.in);
        Integer id = sc.nextInt();
        try
        {
            ctrl.deleteBook(id);
        }
        catch (ValidatorException e)
        {
            e.printStackTrace();
        }
    }

    private void deleteClient() {
        System.out.println("ID of the client to be deleted: ");
        Scanner sc = new Scanner(System.in);
        Integer id = sc.nextInt();
        try
        {
            ctrl.deleteClient(id);
        }
        catch (ValidatorException e)
        {
            e.printStackTrace();
        }
    }

    private void filterAndSort(){
        System.out.println("Enter author name: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String author = bufferRead.readLine();
            List<Book> books = ctrl.filterAndSort(author);
            books.stream().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runMenu() {
        while (true) {
            System.out.print("\n1 - Operations with BOOKS\n2 - Operations with CLIENTS\n3 - Operations with PURCHASES\n0 - Exit\n");
            Scanner sc = new Scanner(System.in);
            int cmd = sc.nextInt();
            switch (cmd) {
                case 0: {
                    System.exit(0);
                }
                case 1:
                    while (true) {
                        System.out.print("\n1 - Read new Book\n" +
                                "2 - Update book\n" +
                                "3 - Delete book\n" +
                                "4 - Print all Books\n" +
                                "5 - Filter books\n" +
                                "6 - Sort books by title\n" +
                                "7 - Filter by author and sort by title\n"+
                                "0 - Back\n");
                        int cmd2 = sc.nextInt();
                        if (cmd2 == 0)
                            break;
                        switch (cmd2) {
                            case 1:
                                readBook();
                                break;
                            case 2:
                                updateBook();
                                break;
                            case 3:
                                deleteBook();
                                break;
                            case 4:
                                printAllBooks();
                                break;
                            case 5:
                                filterBooks();
                                break;
                            case 6:
                                sortBooks();
                                break;
                            case 7:
                                filterAndSort();
                                break;
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.print("\n1 - Read new Client\n" +
                                "2 - Update client\n" +
                                "3 - Delete client\n" +
                                "4 - Print all clients\n" +
                                "5 - Filter clients\n" +
                                "6 - Sort clients by amount spent\n" +
                                "7 - Sort clients by number of books bought\n" +
                                "0 - Back\n");
                        int cmd2 = sc.nextInt();
                        if (cmd2 == 0)
                            break;
                        switch (cmd2) {
                            case 1:
                                readClient();
                                break;
                            case 2:
                                updateClient();
                                break;
                            case 3:
                                deleteClient();
                                break;
                            case 4:
                                printAllClients();
                                break;
                            case 5:
                                filterClients();
                                break;
                            case 6:
                                sortClients();
                                break;
                            case 7:
                                sortClientsByAmountBought();
                                break;
                        }
                    }
                    break;
                case 3:
                    while (true) {
                        System.out.print("\n1 - Buy books\n" +
                                "2 - Print purchases\n" +
                                "0 - Back\n");
                        int cmd2 = sc.nextInt();
                        if (cmd2 == 0)
                            break;
                        switch (cmd2) {
                            case 1:
                                buyBooks();
                                break;
                            case 2:
                                printPurchases();
                                break;
                        }
                    }
                    break;
                default: {
                    System.out.println("Invalid command!\n");
                }
            }
        }
    }
}
