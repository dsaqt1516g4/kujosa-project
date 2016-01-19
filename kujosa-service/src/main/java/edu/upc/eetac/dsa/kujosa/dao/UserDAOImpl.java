package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Role;
import edu.upc.eetac.dsa.kujosa.entity.User;
import edu.upc.eetac.dsa.kujosa.db.Database;

import javax.imageio.ImageIO;
import javax.ws.rs.InternalServerErrorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.UUID;

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
public class UserDAOImpl implements UserDAO {

    /**
     *
     * @param loginid serà el nom d'usuari UNIC
     * @param fullname nom del manolo
     * @param email correu de contacte
     * @param password clau d'accès als serveis
     * @param image
     * @return retorna l'usuari un cop creat
     * @throws SQLException
     * @throws UserAlreadyExistsException
     */
    @Override
    public User createUser(String loginid, String fullname, String email, String password, InputStream image)
            throws SQLException, UserAlreadyExistsException {
        Connection connection = null;
        PreparedStatement stmt = null;
        UUID uuid =writeAndConvertImage(image);
        String id = null;
        try {
            User user = getUserByLoginid(loginid);
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
            stmt.setString(2, loginid);
            stmt.setString(3, password);
            stmt.setString(4, fullname);
            stmt.setString(5, email);
            stmt.setString(6, uuid.toString());
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


    private UUID writeAndConvertImage(InputStream file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);

        } catch (IOException e) {
            throw new InternalServerErrorException(
                    "Something has been wrong when reading the file.");
        }
        UUID uuid = UUID.randomUUID();
        String filename = uuid.toString() + ".png";

        try {
            PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("kujosa");
            ImageIO.write(image, "png", new File(prb.getString("uploadFolder") + filename));
        } catch (IOException e) {
            throw new InternalServerErrorException("Something has been wrong when converting the file.");
        }

        return uuid;
    }
    /**
     *
     * @param loginid nom d'usuari s'usara per a fer les cerques
     * @param email correu si es vol modificar sino null
     * @param password clau si es vol modificar sino null
     * @param image path de la imatge in server si no es vol modificar null
     * @return l'usuari un cop modificat
     * @throws SQLException
     */
    @Override
    public User updateUser(String loginid, String email, String password, String image) throws SQLException {
        User user = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            user = getUserByLoginid(loginid);

            stmt = connection.prepareStatement(UserDAOQuery.UPDATE_USER);
            //CORREU
            if(email !=null) {
                stmt.setString(1, email);
            }
            else{
                stmt.setString(1, user.getEmail());

            }
            //PASS
            if(password !=null) {
                stmt.setString(2, password);
            }
            else{
                //TODO
            }
            //IMAGE
            if(image !=null) {
                stmt.setString(3, image);
            }
            else{
                stmt.setString(3, user.getImageURL());

            }
            stmt.setString(4, user.getLoginid());

            int rows = stmt.executeUpdate();
            if (rows == 1)
                user = getUserByLoginid(loginid);
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
       //getUserById acccepta username

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
                user.setLoginid(rs.getString("loginid"));
                user.setEmail(rs.getString("email"));
                user.setFullname(rs.getString("fullname"));
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
     * @param loginid nom d'usuari a cercar
     * @return torna la entitat usuari
     * @throws SQLException
     */
    @Override
    public User getUserByLoginid(String loginid) throws SQLException {
        User user = null;
        PropertyResourceBundle prop = (PropertyResourceBundle) ResourceBundle.getBundle("kujosa");

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();


            stmt = connection.prepareStatement(UserDAOQuery.GET_USER_BY_USERNAME);
            stmt.setString(1, loginid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setLoginid(rs.getString("loginid"));
                user.setEmail(rs.getString("email"));
                user.setFullname(rs.getString("fullname"));
                user.setFilename(rs.getString("image")+ ".png");
                user.setImageURL(prop.getString("imgBaseURL")+ user.getFilename());            }
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
     * @param id ahí va el id de usuario que sera cogido de la URI.
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

    @Override
    public boolean isAdmin(String id) throws SQLException {

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            String r=null;
            stmt = connection.prepareStatement(UserDAOQuery.IS_ADMIN);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                 r = (rs.getString("role"));
            }
            if (Role.admin.equals(r)){
                    return true;
                }
            else {
                return false;}

        }

        catch ( SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}







