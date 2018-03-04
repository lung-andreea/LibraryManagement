package Tests.DomainTests;

import Domain.Book;

public class TestBook {
    private static final Integer ID=1;
    private static final String TITLE="Tom Sawyer";
    private static final String AUTHOR="Mark Twain";
    private static final Integer PRICE=30;
    private static final Integer STOCK=20;

    private static Book book=new Book(TITLE,AUTHOR,PRICE,STOCK);

    public void testBook(){
        testGetAuthor();
        testGetPrice();
        testGetStock();
        testGetTitle();
        testSetAuthor();
        testSetPrice();
        testSetStock();
        testSetTitle();
    }

    private void testGetAuthor(){
        if(book.getAuthor()!=AUTHOR)
            throw new RuntimeException("getAuthor test failed!");
    }

    private void testGetTitle(){
        if(book.getTitle()!=TITLE)
            throw new RuntimeException("getTitle test failed!");
    }

    private void testGetPrice(){
        if(book.getPrice()!=PRICE)
            throw new RuntimeException("getPrice test failed!");
    }

    private void testGetStock(){
        if(book.getStock()!=STOCK)
            throw new RuntimeException("getStock test failed!");
    }

    private void testSetAuthor(){
        book.setAuthor("Samuel Langhorne Clemens");

        if(book.getAuthor()!="Samuel Langhorne Clemens")
            throw new RuntimeException("setAuthor test failer!");
    }

    private void testSetTitle(){
        book.setTitle("Samuel Sawyer");

        if(book.getTitle()!="Samuel Sawyer")
            throw new RuntimeException("setTitle test failer!");
    }

    private void testSetPrice(){
        book.setPrice(20);

        if(book.getPrice()!=20)
            throw new RuntimeException("setPrice test failer!");
    }

    private void testSetStock(){
        book.setStock(10);

        if(book.getStock()!=10)
            throw new RuntimeException("setStock test failer!");
    }
}
