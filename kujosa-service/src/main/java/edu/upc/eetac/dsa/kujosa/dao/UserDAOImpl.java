package edu.upc.eetac.dsa.kujosa.dao;

import com.sun.xml.internal.bind.v2.TODO;
import edu.upc.eetac.dsa.kujosa.entity.User;
import edu.upc.eetac.dsa.kujosa.db.Database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *     DONE:
 *     -createUser
 *     -getUserByUsername
 *     -updateUser
 *     -deleteUser
 */
public class UserDAOImpl implements UserDAO {

    /**
     *
     * @param username serà el nom d'usuari UNIC
     * @param fullname nom del manolo
     * @param email correu de contacte
     * @param password clau d'accès als serveis
     * @return retorna l'usuari un cop creat
     * @throws SQLException
     * @throws UserAlreadyExistsException
     */
    @Override
    public User createUser( String username, String fullname, String email, String password) throws SQLException, UserAlreadyExistsException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            User user = getUserByUsername(username);
            if (user != null)
                throw new UserAlreadyExistsException();

            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(UserDAOQuery.CREATE_USER);
            stmt.setString(1, id);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, fullname);
            stmt.setString(5, email);
            stmt.setString(6, "default_profile.png");
            stmt.executeUpdate();

            stmt.close();
            stmt = connection.prepareStatement(UserDAOQuery.ASSIGN_ROLE_REGISTERED);
            stmt.setString(1, id);
            stmt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getUserById(id);
    }

    /**
     *
     * @param username nom d'usuari s'usara per a fer les cerques
     * @param correu correu si es vol modificar sino null
     * @param pass clau si es vol modificar sino null
     * @param image path de la imatge in server si no es vol modificar null
     * @return l'usuari un cop modificat
     * @throws SQLException
     */
    @Override
    public User updateUser(String username, String correu,String pass, String image) throws SQLException {
        User user = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            user = getUserByUsername(username);

            stmt = connection.prepareStatement(UserDAOQuery.UPDATE_USER);
            //CORREU
            if(correu!=null) {
                stmt.setString(1, correu);
            }
            else{
                stmt.setString(1, user.getEmail());

            }
            //PASS
            if(correu!=null) {
                stmt.setString(2, pass);
            }
            else{
                //TODO
            }
            //IMAGE
            if(correu!=null) {
                stmt.setString(3, image);
            }
            else{
                stmt.setString(3, user.getImage());

            }
            stmt.setString(4, user.getUsername());

            int rows = stmt.executeUpdate();
            if (rows == 1)
                user = getUserByUsername(username);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return user;
    }

    @Override
    public User getUserById(String id) throws SQLException {
        // Modelo a devolver
        User user = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(UserDAOQuery.GET_USER_BY_ID);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, id);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("loginid"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("fullname"));
            }
        } catch (SQLException e) {
            // Relanza la excepción
            throw e;
        } finally {
            // Libera la conexión
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        // Devuelve el modelo
        return user;
    }


    /**
     *
     * @param username nom d'usuari a cercar
     * @return torna la entitat usuari
     * @throws SQLException
     */
    @Override
    public User getUserByUsername(String username) throws SQLException {
        User user = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();


            stmt = connection.prepareStatement(UserDAOQuery.GET_USER_BY_USERNAME);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("userid"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setImage(rs.getString("image"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return user;
    }

    /**
     *
     * @param id ahí va el identificador de usuario
     * @return torna un bool 1 si tot ha anat com el cul un 0 si tot ok
     * @throws SQLException
     */
    @Override
    public boolean deleteUser(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.DELETE_USER);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * +
     * @param username el nom d'usuari
     * @param password la clau d'accés
     * @return torna un bool
     * @throws SQLException
     */
    @Override
    public boolean checkPassword(String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.GET_PASSWORD);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getBytes());
                    String passedPassword = new BigInteger(1, md.digest()).toString(16);

                    return passedPassword.equalsIgnoreCase(storedPassword);
                } catch (NoSuchAlgorithmException e) {
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
