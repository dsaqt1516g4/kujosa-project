package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.User;

import java.io.InputStream;
import java.sql.SQLException;
/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *     DONE:
 *     -createUser
 *     -getUserByLoginid
 *     -updateUser
 *     -deleteUser
 *     -checkPassword
 */
public interface UserDAO {
    User createUser(String loginid, String password, String email, String fullname, InputStream image) throws SQLException, UserAlreadyExistsException ;

    User updateUser(String loginid, String email ,String password, String image) throws SQLException;

    User getUserById(String id) throws SQLException;

    User getUserByLoginid(String loginid) throws SQLException;

    boolean deleteUser(String id) throws SQLException;

    boolean checkPassword(String id, String password) throws SQLException;
}
