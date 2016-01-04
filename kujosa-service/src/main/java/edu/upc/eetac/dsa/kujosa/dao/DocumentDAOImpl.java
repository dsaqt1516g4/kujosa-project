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
    public Document createDocument(String userid,String name, String description, String path) throws SQLException {
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
        return getDocumentById(id);    }

    @Override
    public Document getDocumentById(String documentid) throws SQLException {
        // Modelo a devolver
        Document doc = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            // Obtiene la conexión del DataSource
            connection = Database.getConnection();

            // Prepara la consulta
            stmt = connection.prepareStatement(UserDAOQuery.GET_USER_BY_ID);
            // Da valor a los parámetros de la consulta
            stmt.setString(1, documentid);

            // Ejecuta la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesa los resultados
            if (rs.next()) {
                doc = new Document();
                doc.setDocid(rs.getString("id"));
                doc.setUsername(rs.getString("loginid"));
                doc.setName(rs.getString("email"));
                doc.setPath(rs.getString("fullname"));
                doc.setDescription(rs.getString(""));
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
        return null;
    }

    @Override
    public Document updateDocument(String documentid, String description) throws SQLException {
        return null;
    }

    @Override
    public void deleteDocumment(String documentid) throws SQLException{

    }
}
