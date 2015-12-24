package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Document;
import edu.upc.eetac.dsa.kujosa.entity.DocumentCollection;

import java.sql.SQLException;

/**
 * Created by juan on 16/12/15.
 */
public class DocumentDAOImpl implements DocumentDAO {

    @Override
    public Document createDocument(String userid, String description) throws SQLException {
        return null;
    }

    @Override
    public Document getDocumentById(String documentid) throws SQLException {
        return null;
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
    public void deleteComment(String documentid) throws SQLException {

    }
}
