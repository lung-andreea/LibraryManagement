package Domain;

public class Purchase {
    private Client client;
    private Book book;
    private Integer amountBought;

    public Purchase(Client client, Book book, Integer amountBought) throws Exception{
        this.client = client;
        this.book = book;
        this.amountBought = amountBought;
        client.setAmountSpent(client.getAmountSpent()+book.getPrice());
        if(book.getStock()<amountBought)
            throw new Exception("The book is not in stock! Try with a smaller amount ...");
        book.setStock(book.getStock()-amountBought);
    }
}
