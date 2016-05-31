package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Document;
import edu.upc.eetac.dsa.kujosa.entity.DocumentCollection;

import java.io.InputStream;
import java.sql.SQLException;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *     DONE:
 *     -createDocument
 *     -getDocumentById
 *     -getDocuments
 *     -updateDocument
 *     -deleteDocumment
 *
 */
public interface DocumentDAO {
    Document createDocument(String userid, String name, String description, InputStream file) throws SQLException;
    Document getDocumentById (String id) throws SQLException;
    DocumentCollection getDocuments (long timestamp, boolean before) throws SQLException;
    Document updateDocument (String id, String name, String description) throws SQLException;
    boolean deleteDocument(String id) throws SQLException;
}
