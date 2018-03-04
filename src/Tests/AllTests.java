package Tests;

import Tests.DomainTests.TestBook;
import Tests.DomainTests.TestClient;
import Tests.DomainTests.TestValidator;

public class AllTests {
    TestBook tb=new TestBook();
    TestClient tc=new TestClient();
    TestValidator tv=new TestValidator();

    public void testAll(){
        tb.testBook();
        tc.testClient();
        tv.testValidator();
    }
}
