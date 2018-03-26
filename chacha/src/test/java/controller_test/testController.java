package controller_test;

import Controller.BookController;
import Domain.Book;
import Domain.Client;
import Domain.Purchase;
import Domain.Validators.BookValidator;
import Domain.Validators.ClientValidator;
import Domain.Validators.PurchaseValidator;
import Exceptions.ValidatorException;
import Repository.IRepository;
import Repository.InMemoryRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class testController {
    private static final IRepository<Integer,Book> bookRepo = new InMemoryRepository<>(new BookValidator());
    private static final IRepository<Integer, Client> clientRepo = new InMemoryRepository<>(new ClientValidator());
    private static final IRepository<Integer, Purchase> purchaseRepo = new InMemoryRepository<>(new PurchaseValidator());
    private static final Book b1 = new Book("b1","a1",12,20);
    private static final Client c1 = new Client("c",0);

    private BookController controller;


    @Before
    public void setUp() {
            controller = new BookController(bookRepo,clientRepo,purchaseRepo);
            b1.setId(1);
            c1.setId(1);

        try {
            controller.addBook(b1);
            controller.addClient(c1);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        controller = null;
    }

    @Test
    public void testIOBook() {
        //GETALL
        assert controller.getAllBooks().size()==1;
        //ADD
        Book b = new Book("dsa","dsa",12,12);
        b.setId(2);
        try {
            controller.addBook(b);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
        assert controller.getAllBooks().size()==2;
        //UPDATE
        try {
            controller.updateBook(1,100,100);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
        assert bookRepo.findOne(1).get().getPrice()==100;
        //DELETE
        try {
            controller.deleteBook(1);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
        assert controller.getAllBooks().size()==1;
    }

    @Test
    public void testIOClient() {
        //GETALL
        assert controller.getAllClients().size()==1;
        //ADD
        Client b = new Client("jdlsa",0);
        b.setId(2);
        try {
            controller.addClient(b);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
        assert controller.getAllClients().size()==2;
        //UPDATE
        try {
            controller.updateClient(1,"Mi-am schimbat numele sunt un boss fac ce vreau cu viata mea");
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
        assert clientRepo.findOne(1).get().getName()=="Mi-am schimbat numele sunt un boss fac ce vreau cu viata mea";
        //DELETE
        try {
            controller.deleteClient(1);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
        assert controller.getAllClients().size()==1;
    }

    @Test
    public void testPurchase() {
        //GET ALL PURCHASES
        assert controller.getAllPurchases().size()==0;
        //TEST BUY
        try {
            controller.buy(1,1,5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert controller.getAllPurchases().size()==1;
        assert b1.getStock()==15;
        assert c1.getAmountSpent()==60;
    }

    @Test
    public void testFilters() {
        //Books by author
        assert controller.filterBooksByAuthor("roooo").size()==0;
        assert controller.filterBooksByAuthor("a1").size()==1;
        //Clients by name
        assert controller.filterClientsByName("name").size()==0;
        assert controller.filterClientsByName("c").size()==1;
    }

    @Test
    public void testSorts() {
        Book b2 = new Book("aa","dsa",1,10);
        Client c2 = new Client("Maricica",0);
        c2.setId(2);
        b2.setId(2);
        try {
            controller.addBook(b2);
            controller.addClient(c2);
            controller.buy(2,2,4);
            controller.buy(1,1,2);
        } catch (ValidatorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Sort books by title
        assert controller.sortBooks().get(0)==b2;
        //Sort clients by amount spent
        assert controller.sortClients().get(0).getId()==2;
        //Sort clients by number of books bought
        List<Client> l = controller.sortClientsByAmountBought();
        assert controller.sortClientsByAmountBought().get(0).getId()==2;
    }
}
