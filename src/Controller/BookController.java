package Controller;

import Domain.Book;
import Domain.Client;
import Exceptions.ValidatorException;
import Repository.IRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookController {

    private IRepository<Integer, Book> bookRepo;
    private IRepository<Integer, Client> clientRepo;

    public BookController(IRepository<Integer, Book> bookRepo, IRepository<Integer, Client> clientRepo) {
        this.bookRepo = bookRepo;
        this.clientRepo = clientRepo;
    }

    public void addBook(Book b) throws ValidatorException {
        bookRepo.save(b);
    }

    public Set<Book> getAllBooks() {
        Iterable<Book> books = bookRepo.findAll();
        return StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
    }

    public void addClient(Client c) throws ValidatorException {
        clientRepo.save(c);
    }

    public Set<Client> getAllClients() {
        Iterable<Client> clients = clientRepo.findAll();
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Returns all books who have the author specified by the String s
     *
     * @param s
     * @return
     */
    public Set<Book> filterBooksByAuthor(String s) {
        Iterable<Book> students = bookRepo.findAll();
        //version 1
        Set<Book> filteredBooks = StreamSupport.stream(students.spliterator(), false)
                .filter(book -> book.getAuthor().equals(s)).collect(Collectors.toSet());
        return filteredBooks;
    }

    /**
     * Returns all clients whose name contain the given string.
     *
     * @param s
     * @return
     */
    public Set<Client> filterClientsByName(String s) {
        Iterable<Client> clients = clientRepo.findAll();

        Set<Client> filteredClients = StreamSupport.stream(clients.spliterator(), false)
                .filter(client -> client.getName().contains(s)).collect(Collectors.toSet());

        return filteredClients;
    }

}
