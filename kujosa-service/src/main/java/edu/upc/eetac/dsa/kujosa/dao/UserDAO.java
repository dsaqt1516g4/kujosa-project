package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.User;

import java.io.InputStream;
import java.sql.SQLException;
/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *     DONE:
 *     -createUser
 *     -getUserByLoginId
 *     -updateProfile
 *     -deleteUser
 *     -checkPassword
 */
public interface UserDAO {
    User createUser(String loginid, String password, String email, String fullname, InputStream image) throws SQLException, UserAlreadyExistsException;
    //User updateProfile(String id, String email, String fullname, String image) throws SQLException;
    User getUserById(String id) throws SQLException;
    User getUserByLoginId(String loginid) throws SQLException;
    boolean deleteUser(String id) throws SQLException;
    boolean checkPassword(String id, String password) throws SQLException;
    boolean isAdmin(String id) throws SQLException;

    User updateProfile(String id, String email, String fullname, InputStream image) throws SQLException;

}
