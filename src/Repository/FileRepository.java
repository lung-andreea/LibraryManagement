package Repository;

import Domain.BaseEntity;
import Domain.Book;
import Domain.Client;
import Domain.Validators.Validator;
import Exceptions.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class FileRepository<T extends BaseEntity<Integer>> extends InMemoryRepository<Integer,T> {
    private String fileName;

    public FileRepository(Validator<T> validator, String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                T entity;
                Type type = ((ParameterizedType)getClass().getGenericSuperclass())
                        .getActualTypeArguments()[1];
                ;

                if (type instanceof Client) {
                    Integer id = Integer.parseInt(items.get(0));
                    String name = items.get(1);

                    Client client = new Client(name);
                    client.setId(id);
                    entity = (T)client;

                    try {
                        super.save(entity);
                    } catch (ValidatorException e) {
                        e.printStackTrace();
                    }
                }

                if (type instanceof Book) {
                    Integer id = Integer.parseInt(items.get(0));
                    String title = items.get(1);
                    String author = items.get(2);
                    Integer price = Integer.parseInt(items.get(3));
                    Integer stock = Integer.parseInt(items.get(4));

                    Book book = new Book(title,author,price,stock);
                    book.setId(id);
                    entity = (T)book;

                    try {
                        super.save(entity);
                    } catch (ValidatorException e) {
                        e.printStackTrace();
                    }
                }

            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public  Optional<T> save(T entity) throws ValidatorException {
        Optional<T> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(T entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {

            if (entity instanceof Client) {
                bufferedWriter.write(
                        entity.getId() + "," + ((Client) entity).getName());
                bufferedWriter.newLine();
            }
            if (entity instanceof Book) {
                bufferedWriter.write(
                        entity.getId() + "," + ((Book) entity).getTitle() + "," + ((Book) entity).getAuthor() + "," + ((Book) entity).getPrice()+ "," + ((Book) entity).getStock());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
