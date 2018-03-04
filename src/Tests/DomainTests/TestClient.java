package Tests.DomainTests;

import Domain.Client;

public class TestClient {
    private static final String NAME="Client1";
    private static final String NAME2="Client";

    private Client client=new Client(NAME);

    public void testClient(){
        testGetAmountSpent();
        testGetName();
        testSetAmountSpent();
        testSetName();
    }

    private void testGetName(){
        if(client.getName()!=NAME)
            throw new RuntimeException("getName test failed!");
    }

    private void testGetAmountSpent(){
        if(client.getAmountSpent()!=0)
            throw new RuntimeException("getAmountSpent test failed!");
    }

    private void testSetName(){
        client.setName(NAME2);
        if(client.getName()!=NAME2)
            throw new RuntimeException("setName test failed!");
    }

    private void testSetAmountSpent(){
        client.setAmountSpent(10);
        if(client.getAmountSpent()!=10)
            throw new RuntimeException("setAmountSpent test failed!");
    }
}
