package Tests.DomainTests;

import Domain.Book;
import Domain.Client;
import Domain.Validators.BookValidator;
import Domain.Validators.ClientValidator;
import Exceptions.ValidatorException;

public class TestValidator {
    private BookValidator bookvalid=new BookValidator();
    private ClientValidator clientvalid=new ClientValidator();

    public void testValidator(){
        testBookValidator();
    }

    private void testBookValidator(){
        Book b1=new Book("a","a",-1,10);
        Book b2=new Book("a","a",10,0);
        Book b3=new Book("a","a",10,10);

        try{
            bookvalid.validate(b1);
            throw new RuntimeException("Price validation test failed!");
        }catch(ValidatorException e){}

        try{
            bookvalid.validate(b2);
            throw new RuntimeException("Stock validation test failed!");
        }catch(ValidatorException e){}

        try{
            bookvalid.validate(b3);
        }catch(ValidatorException e){throw new RuntimeException("Validation test failed!");}
    }

    private void testClientValidator(){
        Client c1=new Client("1");
        Client c2=new Client("a");

        try{
            clientvalid.validate(c1);
            throw new RuntimeException("Client Name validation failed!");
        }catch (ValidatorException e){}

        try{
            clientvalid.validate(c2);
        }catch (ValidatorException e){throw new RuntimeException("Validation test ");}
    }
}
