package Controller;

import Domain.Book;
import Domain.Client;
import Domain.Purchase;
import Exceptions.ValidatorException;
import Repository.IRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookController {

    private IRepository<Integer, Book> bookRepo;
    private IRepository<Integer, Client> clientRepo;
    private IRepository<Integer, Purchase> purchaseRepo;

    public BookController(IRepository<Integer, Book> bookRepo, IRepository<Integer, Client> clientRepo, IRepository<Integer, Purchase> purchaseRepo) {
        this.bookRepo = bookRepo;
        this.clientRepo = clientRepo;
        this.purchaseRepo=purchaseRepo;
    }

    /**
     * Adds a book to the in-memory repository
     * @param b - a book
     * @throws ValidatorException
     */
    public void addBook(Book b) throws ValidatorException {
        bookRepo.save(b);
    }

    /**
     * Updates a book from the repository
     * @param id - the id of the book
     * @param price - new price
     * @param stock - new stock
     * @throws ValidatorException
     */
    public void updateBook(Integer id, Integer price, Integer stock) throws ValidatorException{
        Book initial = bookRepo.findOne(id).get();
        Book b = new Book(initial.getTitle(),initial.getAuthor(),price,stock);
        b.setId(id);
        bookRepo.update(b);
    }

    /**
     * Deletes a book from the repository
     * @param id - id of the book
     * @throws ValidatorException
     */
    public void deleteBook(Integer id) throws ValidatorException{
        bookRepo.delete(id);
    }

    /**
     * Updates a client from the repository
     * @param id
     * @param name - new name
     * @throws ValidatorException
     */
    public void updateClient(Integer id, String name) throws ValidatorException{
        Client initial = clientRepo.findOne(id).get();
        Client c = new Client(name, initial.getAmountSpent());
        c.setId(id);
        clientRepo.update(c);
    }

    /**
     * Deletes a client from the repository
     * @param id
     * @throws ValidatorException
     */
    public void deleteClient(Integer id) throws ValidatorException{
        clientRepo.delete(id);
    }

    /**
     * Returns all the books in the repository
     * @return a list of books
     */
    public Set<Book> getAllBooks() {
        Iterable<Book> books = bookRepo.findAll();
        return StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Adds a client to the repository
     * @param c - a client
     * @throws ValidatorException
     */
    public void addClient(Client c) throws ValidatorException {
        clientRepo.save(c);
    }

    /**
     * Returns all clients in the repository
     * @return a list of clients
     */
    public Set<Client> getAllClients() {
        Iterable<Client> clients = clientRepo.findAll();
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Returns all purchases
     * @return a list of purchases
     */
    public Set<Purchase> getAllPurchases(){
        Iterable<Purchase> bought=purchaseRepo.findAll();
        return StreamSupport.stream(bought.spliterator(),false).collect(Collectors.toSet());
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
                .filter(book -> book.getAuthor().startsWith(s)).collect(Collectors.toSet());
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

    /**
     * Adds a purchase to the repository
     * @param clientID
     * @param bookID
     * @param amount - number of books with the given id bought by the client
     * @throws Exception
     */
    public void buy(Integer clientID,Integer bookID, Integer amount) throws Exception{
        Book book=bookRepo.findOne(bookID).get();
        Client client=clientRepo.findOne(clientID).get();
        if(book.getStock()<amount)
            throw new Exception("The book is not in stock! Try with a smaller amount ...");
        if(book.getStock()==amount)
            bookRepo.delete(bookID);
        else{
            book.setStock(book.getStock()-amount);
            bookRepo.update(book);
        }
        Integer amountSpent=client.getAmountSpent()+amount*book.getPrice();
        client.setAmountSpent(amountSpent);
        clientRepo.update(client);
        Purchase p=new Purchase(clientID,bookID,amount);
        purchaseRepo.save(p);
    }

    /**
     * Sorts books by title
     * @return
     */
    public List<Book> sortBooks(){
        return StreamSupport.stream(bookRepo.findAll().spliterator(),false)
                .sorted((book1,book2)->book1.getTitle().compareTo(book2.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Book> filterAndSort(String s){
        return this.filterBooksByAuthor(s).stream().sorted((b1,b2)->b2.getTitle().compareTo(b1.getTitle())).collect(Collectors.toList());
    }
    /**
     * Sorts clients by amount spent
     * @return
     */
    public List<Client> sortClients(){
        return StreamSupport.stream(clientRepo.findAll().spliterator(),false)
                .sorted((client1,client2)->client2.getAmountSpent().compareTo(client1.getAmountSpent()))
                .collect(Collectors.toList());
    }

    /**
     * Sorts clients by the number of books bought
     * @return
     */
    public List<Client> sortClientsByAmountBought(){
        return StreamSupport.stream(clientRepo.findAll().spliterator(),false)
                .sorted((client1,client2)->StreamSupport.stream(purchaseRepo.findAll().spliterator(),false)
                        .filter(p->p.getClientID().equals(client1.getId()))
                        .map(p->p.getAmountBought())
                        .reduce(0,(aux,am)->aux+am).compareTo(StreamSupport.stream(purchaseRepo.findAll().spliterator(),false)
                                .filter(p->p.getClientID()==client2.getId())
                                .map(p->p.getAmountBought())
                                .reduce(0,(aux,am)->aux+am)))
                .collect(Collectors.toList());
    }
}
