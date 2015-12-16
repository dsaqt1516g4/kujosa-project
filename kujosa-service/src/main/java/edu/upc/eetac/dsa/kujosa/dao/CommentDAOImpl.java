package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Comment;
import edu.upc.eetac.dsa.kujosa.entity.CommentCollection;

import java.sql.SQLException;

/**
 * Created by juan on 14/12/15.
 */
public class CommentDAOImpl implements CommentDAO{


    @Override
    public Comment createComment(String userid, String subject, String content) throws SQLException {
        return null;
    }

    @Override
    public Comment getCommentById(String id) throws SQLException {
        return null;
    }

    @Override
    public CommentCollection getComments(long timestamp, boolean before) throws SQLException {
        return null;
    }

    @Override
    public Comment updateComment(String id, String subject, String content) throws SQLException {
        return null;
    }

    @Override
    public void deleteComment(String id) throws SQLException {

    }
}
