package DAO.Interfaces;

import models.User;
import java.util.Optional;

public interface UsersDao extends CrudDao<User> {

    Optional<User> findById(Long id);
}
