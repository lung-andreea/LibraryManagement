package Repository;

import Domain.Book;
import Domain.Validators.Validator;
import Exceptions.ValidatorException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JDBCBookRepository implements IRepository<Integer,Book> {
    private Map<Integer,Book> entities;
    private Validator<Book> validator;

    private static final String URL="jdbc:postgresql://baasu.db.elephantsql.com:5432/eezffmje";
    private static final String user="eezffmje";
    private static final String password="vAw_O4YKpZ7VpACnsKtCzwxGwMTvWiIb";


    public JDBCBookRepository(Validator<Book> validator){
        this.entities=new HashMap<>();
        this.validator=validator;
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        String sql = "insert into books(id,title,author,price,stock) values (?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(2,entity.getTitle());
            statement.setString(3,entity.getAuthor());
            statement.setInt(4,entity.getPrice());
            statement.setInt(5,entity.getStock());
            statement.setInt(1,entity.getId());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        String sql = "update books set title=?,author=?,price=?,stock=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,entity.getTitle());
            statement.setString(2,entity.getAuthor());
            statement.setInt(3,entity.getPrice());
            statement.setInt(4,entity.getStock());
            statement.setInt(5,entity.getId());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Iterable<Book> findAll() {
        String sql = "select * from books";
        try (Connection connection = DriverManager.getConnection(
                URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String title = rs.getString("title").trim();
                String author=rs.getString("author").trim();
                int price=rs.getInt("price");
                int stock = rs.getInt("stock");
                Book b1=new Book(title,author,price,stock);
                b1.setId(id);
                this.entities.put(id,b1);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return this.entities.entrySet().stream().map(entry->entry.getValue()).collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOne(Integer id) {
        String sql = "select * from books where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(1,id);
             ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String author=rs.getString("author");
                int price=rs.getInt("price");
                int stock = rs.getInt("stock");
                Book b1=new Book(title,author,price,stock);
                b1.setId(id);
                return Optional.ofNullable(b1);
            }
            rs.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Optional<Book> delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "delete from books where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(1,id);
            statement.executeUpdate();
            }
        catch (SQLException e){
                 System.out.println(e);
        }
        return null;
    }
}
