package domain_test;

import Domain.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;


public class testBook {
    private static final int ID=1;
    private static final String TITLE="title";
    private static final String AUTHOR="author";
    private static final int PRICE=20;
    private static final int STOCK=10;

    private Book book;
    @Before
    public void setUp(){
        book=new Book(TITLE, AUTHOR,PRICE,STOCK);
        book.setId(ID);
    }

    @After
    public void tearDown(){
        book=null;
    }

    @Test
    public void testGetID(){
        assert book.getId()==1;
    }

    @Test
    public void testSetID(){
        book.setId(2);
        assert book.getId()==2;
    }
}
