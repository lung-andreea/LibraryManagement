package Domain.Validators;

import Domain.Client;
import Exceptions.ValidatorException;
import com.sun.xml.internal.fastinfoset.util.CharArray;

public class ClientValidator implements Validator<Client>{
    @Override
    public void validate(Client entity) throws ValidatorException {
        if(entity.getName().contains("1234567890!@#$%^&*()+="))
            throw new ValidatorException("Invalid client name!");
    }
}
