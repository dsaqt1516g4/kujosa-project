package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by juan on 09/12/15.
 */

/* TODO: falta probar */
public class CommentDAOQuery {
    public final static String GET_COMMENTS_QUERY = "select * from commentsevent where creation_timestamp < ifnull(?, now()) and eventid = ? order by creation_timestamp desc limit ?";
    public final static String GET_COMMENTS_QUERY_FROM_LAST = "select * from commentsevent where creation_timestamp > ifnull(?, now())  and eventid = ? order by creation_timestamp desc limit ?";
    public final static String GET_COMMENT_BY_ID_QUERY = "select * from commentsevent where commentid = ?";
    public final static String INSERT_COMMENT_QUERY = "insert into commentsevent (commentid, userid, eventid, content) values (?, ?, ?)";
    public final static String UPDATE_COMMENT_QUERY = "update commentsevent set content = ifnull(?, content) where commentid = ?";
    public final static String DELETE_COMMENT_QUERY = "delete from commentsevent where commentid = ?";
}
