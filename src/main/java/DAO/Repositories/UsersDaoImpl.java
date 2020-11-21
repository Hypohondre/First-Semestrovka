package DAO.Repositories;

import DAO.Interfaces.RowMapper;
import DAO.Interfaces.UsersDao;
import models.User;
import services.ConnectionService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDaoImpl implements UsersDao {
    public Connection connection;
    private RowMapper<User> userRowMapper = row -> new User(
            row.getString("name"),
            row.getString("password"),
            row.getString("email"),
            row.getDate("birthdate")
    );

    public UsersDaoImpl() {
        this.connection = ConnectionService.getConnection();
    }

    //language=SQL
    private final String FIND_USER_BY_ID = "SELECT * FROM USERS WHERE id = ?";

    @Override
    public Optional<User> find(Long id) {
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet set = preparedStatement.executeQuery();

            if (set.next()) {
                return Optional.ofNullable(userRowMapper.mapRow(set));
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    //language=SQL
    private final String LOGIN_USER = "SELECT id FROM users WHERE name = ?, password = ?";

    @Override
    public Optional<Long> login(String name, String password) {
        Long id;
        try(PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            id = resultSet.getLong("id");
            resultSet.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.ofNullable(id);
    }

    //language=SQL
    private final String SAVE = "INSERT INTO users (id, name, password, birthdate, photo) values (default,?,?,?,?)";

    @Override
    public void save(User model) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, model.getUsername());
            preparedStatement.setString(2, model.getPassword());
            preparedStatement.setDate(3, model.getBirthdate());
            preparedStatement.setString(4, model.getPhoto());

            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException();
            }
            try(ResultSet set = preparedStatement.getGeneratedKeys()) {
                if (set.next()) {
                    model.setId(set.getLong("id"));
                } else {
                    throw new SQLException();
                }
                set.close();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("что-то пошло не так");
        }
    }

    //language=SQL
    private final String UPDATE_USER = "UPDATE users SET name = ?, password = ?, birthdate = ?, photo = ? WHERE id = ?";

    @Override
    public void update(User model) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, model.getUsername());
            preparedStatement.setString(2, model.getPassword());
            preparedStatement.setDate(3, model.getBirthdate());
            preparedStatement.setString(4, model.getPhoto());
            preparedStatement.setLong(5, model.getId());

            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    //language=SQL
    private final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    @Override
    public void delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);

            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    //language=SQL
    private final String FIND_ALL_USERS_BY_NAME = "SELECT * FROM users WHERE name = ?";

    @Override
    public List<User> findAll(String name) {
        List<User> users = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USERS_BY_NAME)) {
            preparedStatement.setString(1, name);

            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                User user = userRowMapper.mapRow(set);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return users;
    }
}
