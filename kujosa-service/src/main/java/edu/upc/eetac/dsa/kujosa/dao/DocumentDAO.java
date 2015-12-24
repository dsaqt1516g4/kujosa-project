package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Document;
import edu.upc.eetac.dsa.kujosa.entity.DocumentCollection;

import java.sql.SQLException;

/**
 * Created by juan on 16/12/15.
 */
public interface DocumentDAO {
    public Document createDocument (String userid, String description) throws SQLException;
    public Document getDocumentById (String documentid) throws SQLException;
    public DocumentCollection getDocuments (long lastmodified) throws SQLException;
    public Document updateDocument (String documentid, String description) throws SQLException;
    public void deleteComment(String documentid) throws SQLException;


    /*
    public Comment createComment(String userid, String content) throws SQLException;
    public Comment getCommentById(String commentid) throws SQLException;
    public CommentCollection getComments(long lastModified) throws SQLException;
    public Comment updateComment(String commentid, String content) throws SQLException;
    public void deleteComment(String commentid) throws SQLException;
     */
}
