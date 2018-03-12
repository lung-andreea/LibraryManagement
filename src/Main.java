import Controller.BookController;
import Domain.BaseEntity;
import Domain.Book;
import Domain.Client;
import Domain.Purchase;
import Domain.Validators.BookValidator;
import Domain.Validators.ClientValidator;
import Domain.Validators.PurchaseValidator;
import Domain.Validators.Validator;
import Repository.FileRepository;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Repository.XMLRepository;
import Tests.AllTests;
import UI.Console;

public class Main {

    public static void main(String[] args) {
        AllTests tests=new AllTests();
        tests.testAll();

        // In-memory
//        Console console = new Console(new BookController(new InMemoryRepository<>(new BookValidator()), new InMemoryRepository<>(new ClientValidator())));
//        console.runConsole();

        // FileRepo (CSV)
//        IRepository clientsRepo = new FileRepository<Client>(new ClientValidator(),"clients.csv");
//        IRepository booksRepo = new FileRepository<Book>(new BookValidator(),"books.csv");
//        IRepository purchaseRepo=new InMemoryRepository<Integer,Purchase>(new PurchaseValidator());
//        Console console = new Console(new BookController(booksRepo,clientsRepo,purchaseRepo));
//        console.runConsole();

        // XMLRepo
        IRepository clientsRepo = new XMLRepository<Client>(new ClientValidator(),"clients.xml");
        IRepository booksRepo = new XMLRepository<Book>(new BookValidator(),"books.xml");
        IRepository purchaseRepo=new InMemoryRepository<Integer,Purchase>(new PurchaseValidator());
        Console console = new Console(new BookController(booksRepo,clientsRepo,purchaseRepo));
        console.runConsole();
}

    private static void writeClientsToXML()
    {

    }
}
