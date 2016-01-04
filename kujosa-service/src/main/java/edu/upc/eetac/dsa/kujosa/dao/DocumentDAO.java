package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Document;
import edu.upc.eetac.dsa.kujosa.entity.DocumentCollection;

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
    public Document createDocument(String userid,String name, String description, String path) throws SQLException;
    public Document getDocumentById (String documentid) throws SQLException;
    public DocumentCollection getDocuments (long lastmodified) throws SQLException;
    public Document updateDocument (String documentid, String description) throws SQLException;
    public void deleteDocumment(String documentid) throws SQLException;


}
