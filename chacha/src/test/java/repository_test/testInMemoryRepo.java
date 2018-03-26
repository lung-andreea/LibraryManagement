package repository_test;

import Domain.Book;
import Domain.Validators.BookValidator;
import Domain.Validators.Validator;
import Exceptions.ValidatorException;
import Repository.InMemoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class testInMemoryRepo {
    private static final Validator validator = new BookValidator();
    private static final Book b1 = new Book("b1","a1",12,20);
    private static final Book b2 = new Book("b2","a2",30,40);
    private InMemoryRepository repo;


    @Before
    public void setUp() {
        try {
            repo = new InMemoryRepository<>(validator);
            b1.setId(1);
            b2.setId(2);
            repo.save(b1);
            repo.save(b2);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        repo = null;
    }

    @Test
    public void testFindOne() {
        assert repo.findOne(1).get() == b1;
    }

    @Test
    public void testFindAll() {
        Iterator<Book> it = repo.findAll().iterator();
        int i = 0;
        for ( ; it.hasNext() ; ++i ) it.next();
        assert i==2;
    }

    @Test
    public void testSave() {
        Book b3 = new Book("ffsa","dsa",12,16);
        b3.setId(3);
        try {
            repo.save(b3);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
        assert repo.getSize()==3;
    }

    @Test
    public void testUpdate() {
        Book b4 = new Book("dsa","dsa",1,1);
        b4.setId(2);
        try {
            repo.update(b4);
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
        Book b = (Book) repo.findOne(2).get();
        assert b.getPrice()==1;
    }

    @Test
    public void testDelete() {
        repo.delete(1);
        assert repo.getSize()==1;
    }
}
