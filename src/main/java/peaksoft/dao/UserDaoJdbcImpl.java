package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    private final Connection connection;


    public UserDaoJdbcImpl() throws SQLException {
        connection = new Util().getConnection();
    }

    public void createUsersTable() {
        String query = """
                create table if not exists users(
                id serial primary key,
                name varchar(50) not null,
                last_name varchar(50) not null,
                age smallint not null
                );
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String query = "drop table if exists users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println(" you have successfully dropped table users");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String last_name, byte age) {
        String query = """
                insert into users (name, last_name, age) values (?,?,?)
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println(name + " successfully saved");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        String query = "delete from users where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            System.out.printf("user %d successfully deleted",id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String query = "select * from users";
        List<User> allUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String query = "truncate table users";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println(" you have successfully cleaned all users in users table");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean existsByFirstName(String firstName) {
        String query = "select case when name notnull then true else false end from users where name like ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}