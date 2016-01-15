package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Comment;
import edu.upc.eetac.dsa.kujosa.entity.CommentCollection;

import java.sql.SQLException;

/**
 * Created by juan on 09/12/15.
 */
public interface CommentDAO {
    Comment createComment(String userid, int eventid, String content, String image) throws SQLException;
    Comment getCommentById(String id) throws SQLException;
    CommentCollection getComments(int length, String eventid, long before, long after) throws SQLException; //en aquest tinc dubtes
    Comment updateComment(String id, String content, String image, int ratio) throws SQLException;
    boolean deleteComment(String id) throws SQLException;
}
