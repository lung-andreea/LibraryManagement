package Repository;

import Domain.Client;
import Domain.Validators.Validator;
import Exceptions.ValidatorException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JDBCClientRepository implements IRepository<Integer, Client> {
    private Map<Integer,Client> entities;
    private Validator<Client> validator;

    private static final String URL="jdbc:postgresql://baasu.db.elephantsql.com:5432/eezffmje";
    private static final String user="eezffmje";
    private static final String password="vAw_O4YKpZ7VpACnsKtCzwxGwMTvWiIb";


    public JDBCClientRepository(Validator<Client> validator){
        this.entities=new HashMap<>();
        this.validator=validator;
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        String sql = "insert into clients(id,name,amountspent) values (?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,entity.getId());
            statement.setString(2,entity.getName());
            statement.setInt(3,entity.getAmountSpent());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        String sql = "update clients set name=?,amountspent=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(3,entity.getId());
            statement.setString(1,entity.getName());
            statement.setInt(2,entity.getAmountSpent());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Iterable<Client> findAll() {
        String sql = "select * from clients";
        try (Connection connection = DriverManager.getConnection(
                URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name").trim();
                int amountSpent=rs.getInt("amountspent");
                Client c1=new Client(name,amountSpent);
                c1.setId(id);
                this.entities.put(id,c1);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return this.entities.entrySet().stream().map(entry->entry.getValue()).collect(Collectors.toList());
    }

    @Override
    public Optional<Client> findOne(Integer id) {
        String sql = "select * from clients where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int amountSpent=rs.getInt("amountspent");
                Client c1=new Client(name,amountSpent);
                c1.setId(id);
                return Optional.ofNullable(c1);
            }
            rs.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Optional<Client> delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        String sql = "delete from clients where id=?";
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
