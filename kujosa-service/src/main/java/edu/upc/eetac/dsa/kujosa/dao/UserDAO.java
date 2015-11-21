package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.User;

import java.sql.SQLException;

/**
 * Created by sergio on 7/09/15.
 */
public interface UserDAO {
    public User createUser(String loginid, String password, String email, String fullname) throws SQLException, UserAlreadyExistsException;

    public User updateProfile(String id, String email, String fullname) throws SQLException;

    public User getUserById(String id) throws SQLException;

    public User getUserByLoginid(String loginid) throws SQLException;

    public boolean deleteUser(String id) throws SQLException;

    public boolean checkPassword(String id, String password) throws SQLException;
}
