package Domain;

import java.util.ArrayList;

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
    public Client constructor(ArrayList<String> items) {
        Integer id=Integer.parseInt(items.get(0));
        String name=items.get(1);
        Integer amount=Integer.parseInt(items.get(2));
        Client client=new Client(name,amount);
        client.setId(id);
        return client;
    }

    @Override
    public String toString() {
        return this.name+", "+this.amountSpent+", "+super.toString();
    }
}
