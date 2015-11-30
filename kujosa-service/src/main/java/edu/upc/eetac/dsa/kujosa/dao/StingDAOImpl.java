package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Comentari;
import edu.upc.eetac.dsa.kujosa.entity.StingCollection;
import edu.upc.eetac.dsa.kujosa.db.Database;

import java.sql.*;

/**
 * Created by sergio on 9/09/15.
 */
public class StingDAOImpl implements StingDAO {

    @Override
    public Comentari createSting(String userid, String subject, String content) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(StingDAOQuery.CREATE_STING);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, subject);
            stmt.setString(4, content);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getStingById(id);
    }

    @Override
    public Comentari getStingById(String id) throws SQLException {
        Comentari comentari = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(StingDAOQuery.GET_STING_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                comentari = new Comentari();
                comentari.setId(rs.getString("id"));
                comentari.setUserid(rs.getString("userid"));
                comentari.setCreator(rs.getString("fullname"));
                comentari.setSubject(rs.getString("subject"));
                comentari.setContent(rs.getString("content"));
                comentari.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                comentari.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return comentari;
    }

    @Override
    public StingCollection getStings(long timestamp, boolean before) throws SQLException {
        StingCollection stingCollection = new StingCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if (before)
                stmt = connection.prepareStatement(StingDAOQuery.GET_STINGS);
            else
                stmt = connection.prepareStatement(StingDAOQuery.GET_STINGS_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Comentari comentari = new Comentari();
                comentari.setId(rs.getString("id"));
                comentari.setUserid(rs.getString("userid"));
                comentari.setCreator(rs.getString("fullname"));
                comentari.setSubject(rs.getString("subject"));
                comentari.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                comentari.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    stingCollection.setNewestTimestamp(comentari.getLastModified());
                    first = false;
                }
                stingCollection.setOldestTimestamp(comentari.getLastModified());
                stingCollection.getComentaris().add(comentari);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return stingCollection;
    }

    @Override
    public Comentari updateSting(String id, String subject, String content) throws SQLException {
        Comentari comentari = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(StingDAOQuery.UPDATE_STING);
            stmt.setString(1, subject);
            stmt.setString(2, content);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                comentari = getStingById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return comentari;
    }

    @Override
    public boolean deleteSting(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(StingDAOQuery.DELETE_STING);
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
}
