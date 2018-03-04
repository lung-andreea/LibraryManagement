package Domain.Validators;

import Domain.Book;
import Exceptions.ValidatorException;

public class BookValidator implements Validator<Book>{
    @Override
    public void validate(Book entity) throws ValidatorException {
        if(entity.getStock()<1)
            throw new ValidatorException("Invalid stock!");
        if(entity.getPrice()<0)
            throw new ValidatorException("Price must be greater than zero!");
    }
}
