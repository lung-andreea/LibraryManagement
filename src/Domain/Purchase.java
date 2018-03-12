package Domain;

public class Purchase extends BaseEntity<Integer>{
    private Client client;
    private Book book;
    private Integer amountBought;
    private static int ID=1;

    public Purchase(Client client, Book book, Integer amountBought){
        this.client = client;
        this.book = book;
        this.amountBought = amountBought;
        super.setId(ID);
    }

    @Override
    public String toString() {
        return super.toString()+".\t|Client: "+client.getName()+"|\t|Book: "+book.getTitle()+"|\t|Amount bought: "+amountBought+"|";
    }
}
