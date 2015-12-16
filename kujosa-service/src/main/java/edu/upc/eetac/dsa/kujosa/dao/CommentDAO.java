package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Comment;
import edu.upc.eetac.dsa.kujosa.entity.CommentCollection;

import java.sql.SQLException;

/**
 * Created by juan on 09/12/15.
 */
public interface CommentDAO {
    public Comment createComment(String userid, String subject, String content) throws SQLException;
    public Comment getCommentById(String id) throws SQLException;
    public CommentCollection getComments(long timestamp, boolean before) throws SQLException;
    public Comment updateComment(String id, String subject, String content) throws SQLException;
    public void deleteComment(String id) throws SQLException;
}
