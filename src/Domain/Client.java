package Domain;

public class Client extends BaseEntity<Integer> {
    private String name;
    private Integer amountSpent = 0;

    public Client(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(Integer amountSpent) {
        this.amountSpent = amountSpent;
    }
}
