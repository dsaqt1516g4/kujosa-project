package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.User;

import java.sql.SQLException;
/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *     DONE:
 *     -createUser
 *     -getUserByUsername
 *     -updateUser
 *     -deleteUser
 *     -checkPassword
 */
public interface UserDAO {
    public User createUser( String username, String fullname, String email, String password) throws SQLException, UserAlreadyExistsException ;

    public User updateUser(String username, String correu,String pass, String image) throws SQLException;

    public User getUserById(String id) throws SQLException;

    public User getUserByUsername(String username) throws SQLException;

    public boolean deleteUser(String id) throws SQLException;

    public boolean checkPassword(String id, String password) throws SQLException;
}
