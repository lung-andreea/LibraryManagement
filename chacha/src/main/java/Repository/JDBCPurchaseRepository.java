package Repository;

import Domain.Purchase;
import Domain.Validators.Validator;
import Exceptions.ValidatorException;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class JDBCPurchaseRepository implements IRepository<Integer, Purchase>{
    private Map<Integer, Purchase> entities;
    private Validator<Purchase> validator;

    private static final String URL="jdbc:postgresql://baasu.db.elephantsql.com:5432/eezffmje";
    private static final String user="eezffmje";
    private static final String password="vAw_O4YKpZ7VpACnsKtCzwxGwMTvWiIb";


    public JDBCPurchaseRepository(Validator<Purchase> validator){
        this.entities=new HashMap<>();
        this.validator=validator;
        List<Integer> list=new ArrayList<>();
        this.findAll().forEach(p->list.add(p.getId()));
        Purchase.setInitialID(list.get(list.size()-1)+1);

    }

    @Override
    public Optional<Purchase> save(Purchase entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        String sql = "insert into purchase(id,clientID,bookID,amountBought) values (?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,entity.getId());
            statement.setInt(2,entity.getClientID());
            statement.setInt(3,entity.getBookID());
            statement.setInt(4,entity.getAmountBought());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }


    @Override
    public Optional<Purchase> update(Purchase entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        String sql = "update purchase set clientID=?,bookID=?,amountbought=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(4,entity.getId());
            statement.setInt(2,entity.getBookID());
            statement.setInt(1,entity.getClientID());
            statement.setInt(3,entity.getAmountBought());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Iterable<Purchase> findAll() {
        String sql = "select * from purchase";
        try (Connection connection = DriverManager.getConnection(
                URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer clientID=rs.getInt("clientID");
                Integer bookID=rs.getInt("bookID");
                Integer amount=rs.getInt("amountBought");
                Purchase purchase=new Purchase(clientID,bookID,amount);
                purchase.setId(id);
                this.entities.put(id,purchase);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return this.entities.entrySet().stream().map(entry->entry.getValue()).collect(Collectors.toList());
    }

    @Override
    public Optional<Purchase> findOne(Integer id) {
        String sql = "select * from purchase where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Integer clientID=rs.getInt("clientID");
                Integer bookID=rs.getInt("booksID");
                Integer amount=rs.getInt("amountBought");
                Purchase purchase=new Purchase(clientID,bookID,amount);
                purchase.setId(id);
                return Optional.ofNullable(purchase);
            }
            rs.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Optional<Purchase> delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "delete from purchase where id=?";
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
