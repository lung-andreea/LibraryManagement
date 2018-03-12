package Domain;

public class Book extends BaseEntity<Integer>{
    private String title;
    private String author;
    private Integer price;
    private Integer stock;

    public Book(String title, String author, Integer price, Integer stock) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return super.toString()+".\tTitle: "+title+"\tAuthor: "+author+"\tPrice: "+price+"\tStock: "+stock+"\t";
    }
}
