package Domain.Validators;

import Domain.Client;
import Exceptions.ValidatorException;

public class ClientValidator implements Validator<Client>{
    String[] forbidden={"1","2","3","4","5","6","7","8","9","0","!","@","#","$","%","^","&","*","(",")","=","+","*","/"};
    @Override
    public void validate(Client entity) throws ValidatorException {

        for(String c:forbidden)
        if(entity.getName().contains(c))
            throw new ValidatorException("Invalid client name!");
    }
}
