import Controller.BookController;
import Domain.Book;
import Domain.Client;
import Domain.Purchase;
import Domain.Validators.BookValidator;
import Domain.Validators.ClientValidator;
import Domain.Validators.PurchaseValidator;
import Repository.*;
import UI.Console;

public class Main {

    public static void main(String[] args) {

        // In-memory
//        Console console = new Console(new BookController(new InMemoryRepository<>(new BookValidator()), new InMemoryRepository<>(new ClientValidator())));
//        console.runConsole();

        // FileRepo (CSV)
//        Console console = new Console(new BookController(new FileRepository<Book>(new BookValidator(),"books.csv"),new FileRepository<Client>(new ClientValidator(),"clients.csv"),new InMemoryRepository<Integer, Purchase>(new PurchaseValidator())));
//        console.runConsole();

        // XMLRepo
        Console console = new Console(new BookController(new XMLRepository<Book>(new BookValidator(),"books.xml"),new XMLRepository<Client>(new ClientValidator(),"clients.xml"),new InMemoryRepository<Integer,Purchase>(new PurchaseValidator())));
        console.runConsole();

        //JDBC
//        Console console=new Console(new BookController(new JDBCBookRepository(new BookValidator()),new JDBCClientRepository(new ClientValidator()),new JDBCPurchaseRepository(new PurchaseValidator())));
//        console.runConsole();


}

}
