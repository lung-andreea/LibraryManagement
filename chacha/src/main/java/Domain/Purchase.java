package Domain;

public class Purchase extends BaseEntity<Integer>{
    private Integer clientID;
    private Integer bookID;
    private Integer amountBought;
    private static int ID=1;

    public static void setInitialID(int id){ID=id;}

    public Purchase(Integer clientID, Integer bookID, Integer amountBought){
        this.clientID = clientID;
        this.bookID = bookID;
        this.amountBought = amountBought;
        super.setId(ID);
        ID++;
    }

    @Override
    public String toString() {
        return super.toString()+".\t|Client: "+clientID+"|\t|Book: "+bookID+"|\t|Amount bought: "+amountBought+"|";
    }

//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }
//
//    public Book getBook() {
//        return book;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//    }

    public Integer getAmountBought() {
        return amountBought;
    }

    public void setAmountBought(Integer amountBought) {
        this.amountBought = amountBought;

    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }
}
