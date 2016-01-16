package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.db.Database;
import edu.upc.eetac.dsa.kujosa.entity.Comment;
import edu.upc.eetac.dsa.kujosa.entity.CommentCollection;

import java.sql.*;

/**
 * Created by juan on 14/12/15.
 */

/*                      *
*    ACABADO            *
*                       *
* TODO: revisar bugs    *
*                       */
public class CommentDAOImpl implements CommentDAO{

    @Override
    public Comment createComment(String userid, String eventid, String content, String image) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(CommentDAOQuery.INSERT_COMMENT_QUERY);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setInt(3, eventid);
            stmt.setString(4, content);
            stmt.setString(5, image);
            /*stmt.setInt(6, ratio);
             */
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
        return getCommentById(id);
    }

    @Override
    public Comment getCommentById(String id) throws SQLException {
        Comment comment = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(CommentDAOQuery.GET_COMMENT_BY_ID_QUERY);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                comment = new Comment();
                comment.setId(rs.getInt("commentid"));
                comment.setUserid(rs.getInt("userid"));
                comment.setEventid(rs.getInt("eventid"));
                comment.setContent(rs.getString("content"));
                comment.setRatio(rs.getInt("ratio"));
                comment.setImage(rs.getString("image"));
                comment.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                comment.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return comment;
    }

    @Override
    public CommentCollection getComments(int length, String eventid, long before, long after) throws SQLException {
        CommentCollection commentCollection = new CommentCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if (before > 0) {
                stmt = connection.prepareStatement(CommentDAOQuery.GET_COMMENTS_QUERY_FROM_LAST);
                stmt.setTimestamp(1, new Timestamp(before));
            } else {
                stmt = connection.prepareStatement(CommentDAOQuery.GET_COMMENTS_QUERY);
                if (after > 0)
                    stmt.setTimestamp(1, new Timestamp(after));
                else
                    stmt.setTimestamp(1, null);
            }

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("commentid"));
                comment.setUserid(rs.getInt("userid"));
                comment.setContent(rs.getString("content"));
                comment.setRatio(rs.getInt("ratio"));
                comment.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                comment.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    first = false;
                    commentCollection.setNewestTimestamp(comment.getCreationTimestamp());

                }
                commentCollection.setOldestTimestamp(comment.getLastModified());
                commentCollection.getComments().add(comment);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return commentCollection;
    }

    @Override
    public Comment updateComment(String id, String content, String image, int ratio) throws SQLException {
        Comment comment = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(CommentDAOQuery.UPDATE_COMMENT_QUERY);
            stmt.setString(1, content);
            stmt.setString(2, image);
            stmt.setInt(3, ratio);
            stmt.setString(4, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                comment = getCommentById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return comment;
    }

    @Override
    public boolean deleteComment(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(CommentDAOQuery.DELETE_COMMENT_QUERY);
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
