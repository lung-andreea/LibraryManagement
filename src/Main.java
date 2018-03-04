import Domain.Book;
import Domain.Client;
import Domain.Validators.BookValidator;
import Domain.Validators.ClientValidator;
import Exceptions.ValidatorException;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Tests.AllTests;

public class Main {

    public static void main(String[] args) {
        //System.out.println("HELOOOO BTS HEREEE");
        AllTests tests=new AllTests();
        tests.testAll();
        System.out.println("All went well");
//
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
