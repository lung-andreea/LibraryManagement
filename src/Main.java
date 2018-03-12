import Controller.BookController;
import Domain.Book;
import Domain.Client;
import Domain.Validators.BookValidator;
import Domain.Validators.ClientValidator;
import Repository.FileRepository;
import Repository.IRepository;
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
        IRepository clientsRepo = new FileRepository<Client>(new ClientValidator(),"clients.csv");
        IRepository booksRepo = new FileRepository<Book>(new BookValidator(),"books.csv");
        Console console = new Console(new BookController(booksRepo,clientsRepo));
        console.runConsole();

//          CE CACAT IS ASTEA ZI-MI SI MIE =]]]]
////        IRepository<Integer, Book> repo=new InMemoryRepository<>(new BookValidator());
////
////        Book b1=new Book("a","b",10,12);
////        b1.setId(1);
////        Book b2=new Book("b","c",12,12);
////        b2.setId(2);
////        try {
////            repo.save(b1);
////            repo.save(b2);
////            for(Book b:repo.findAll())
////                System.out.println(b);
////            Book b3=new Book("c","c",-1,10);
////            b3.setId(2);
////            repo.update(b3);
////            for(Book b:repo.findAll())
////                System.out.println(b);
////        }catch(ValidatorException e){System.out.println(e);}
//
//        IRepository<Integer, Client> repo=new InMemoryRepository<>(new ClientValidator());
//
//        Client c1=new Client("Roman");
//        c1.setId(1);
//        Client c2=new Client("Roman2");
//        c2.setId(2);
//        try {
//            repo.save(c1);
//            repo.save(c2);
//        }catch (ValidatorException e){System.out.println(e);}
//    }
}}
