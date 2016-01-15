package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.db.Database;
import edu.upc.eetac.dsa.kujosa.entity.Document;
import edu.upc.eetac.dsa.kujosa.entity.DocumentCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juan on 16/12/15.
 */
public class DocumentDAOImpl implements DocumentDAO {

    @Override
    public Document createDocument(String userid, String name, String description, String path) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {

            connection = Database.getConnection();

            stmt = connection.prepareStatement(DocumentDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(DocumentDAOQuery.CREATE_DOCUMENT);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, name);
            stmt.setString(4, description);
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
        return getDocumentById(id);
    }

    @Override
    public Document getDocumentById(String id) throws SQLException {
        // Modelo a devolver
        Document doc = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(DocumentDAOQuery.GET_DOCUMENT_BY_ID);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, id);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next()) {
                doc = new Document();
                doc.setId(rs.getString("id"));
                doc.setUserid(rs.getString("userid"));
                doc.setName(rs.getString("name"));
                doc.setDescription(rs.getString("description"));
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
        return doc;
    }

    @Override
    public DocumentCollection getDocuments(long lastmodified) throws SQLException {
/*
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
                Sting sting = new Sting();
                sting.setId(rs.getString("id"));
                sting.setUserid(rs.getString("userid"));
                sting.setCreator(rs.getString("fullname"));
                sting.setSubject(rs.getString("subject"));
                sting.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                sting.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    stingCollection.setNewestTimestamp(sting.getLastModified());
                    first = false;
                }
                stingCollection.setOldestTimestamp(sting.getLastModified());
                stingCollection.getStings().add(sting);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return stingCollection;
 */
        return null;
    }

    @Override
    public Document updateDocument(String id, String description) throws SQLException {
        /*
        Sting sting = null;

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
                sting = getStingById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return sting;
         */
        return null;
    }

    @Override
    public boolean deleteDocumment(String id) throws SQLException{
        /*
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
         */
        return false;

    }
}
