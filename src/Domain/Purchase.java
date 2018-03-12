package Domain;

public class Purchase extends BaseEntity<Integer>{
    private Client client;
    private Book book;
    private Integer amountBought;

    public Purchase(Client client, Book book, Integer amountBought){
        this.client = client;
        this.book = book;
        this.amountBought = amountBought;
    }
}
