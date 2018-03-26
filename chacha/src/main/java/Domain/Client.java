package Domain;

public class Client extends BaseEntity<Integer> {
    private String name;
    private Integer amountSpent = 0;

    public Client(String name) {
        this.name = name;
    }

    public Client(String name, Integer amount){
        this.name=name;
        this.amountSpent=amount;
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

    @Override
    public String toString() {
        return super.toString()+".\t|Name: "+this.name+"|\t|Amount spent:  "+this.amountSpent+"|";
    }
}
