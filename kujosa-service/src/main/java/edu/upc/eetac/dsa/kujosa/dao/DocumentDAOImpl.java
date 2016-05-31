package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.db.Database;
import edu.upc.eetac.dsa.kujosa.entity.Document;
import edu.upc.eetac.dsa.kujosa.entity.DocumentCollection;
import edu.upc.eetac.dsa.kujosa.entity.User;

import javax.ws.rs.InternalServerErrorException;

import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by juan on 16/12/15.
 */
public class DocumentDAOImpl implements DocumentDAO {

    @Override
    public Document createDocument(String userid, String name, String description, InputStream file) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String filepath = uploadFile(file);
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(DocumentDAOQuery.CREATE_DOCUMENT);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, name);
            stmt.setString(4, description);
            stmt.setString(5, filepath);
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
        return getDocumentById(id);
    }

    private String uploadFile(InputStream file) {

        UUID uuid = UUID.randomUUID();
        String filename = uuid.toString() + ".pdf";
        PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("kujosa");

        try {
            OutputStream o = new FileOutputStream(prb.getString("uploadFolder") + filename);
            int bytes=IOUtils.copy(file, o);
            System.out.println("File Written with "+bytes+" bytes");
            IOUtils.closeQuietly(o);
        } catch (IOException e) {
            throw new InternalServerErrorException("Something has been wrong when uploading the file");
        }

        return filename;
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
                doc.setPath(rs.getString("path"));
                doc.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                doc.setLastModified(rs.getTimestamp("last_modified").getTime());
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
    public DocumentCollection getDocuments(long timestamp, boolean before) throws SQLException {
        DocumentCollection documentCollection = new DocumentCollection();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if (before)
                stmt = connection.prepareStatement(DocumentDAOQuery.GET_DOCUMENTS);
            else
                stmt = connection.prepareStatement(DocumentDAOQuery.GET_DOCUMENTS_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Document document = new Document();
                document.setId(rs.getString("id"));
                document.setUserid(rs.getString("userid"));
                document.setName(rs.getString("name"));
                document.setDescription(rs.getString("description"));
                document.setPath(rs.getString("path"));
                document.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                document.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    documentCollection.setNewestTimestamp(document.getLastModified());
                    first = false;
                }
                documentCollection.setOldestTimestamp(document.getLastModified());
                documentCollection.getDocuments().add(document);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return documentCollection;
    }

    @Override
    public Document updateDocument(String id, String name, String description) throws SQLException {

        Document document = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            //UserDAO usd= new UserDAOImpl();
            //User us =  usd.getUserByLoginId(name);
            connection = Database.getConnection();


            stmt = connection.prepareStatement(DocumentDAOQuery.UPDATE_DOCUMENT);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                document = getDocumentById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return document;
    }

    @Override
    public boolean deleteDocument(String id) throws SQLException{

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(DocumentDAOQuery.DELETE_DOCUMENT);
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
