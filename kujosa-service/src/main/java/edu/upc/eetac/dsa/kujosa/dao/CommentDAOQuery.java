package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by juan on 09/12/15.
 */
public class CommentDAOQuery {
    private String GET_COMMENTS_QUERY = "select * from commentsevent where creation_timestamp < ifnull(?, now()) and eventid = ? order by creation_timestamp desc limit ?";
    private String GET_COMMENTS_QUERY_FROM_LAST = "select * from commentsevent where creation_timestamp > ifnull(?, now())  and eventid = ? order by creation_timestamp desc limit ?";
    private String GET_COMMENT_BY_ID_QUERY = "select * from commentsevent where commentid = ?";
    private String INSERT_COMMENT_QUERY = "insert into commentsevent (userid, eventid, content) values (?, ?, ?)";
    private String UPDATE_COMMENT_QUERY = "update commentsevent set content = ifnull(?, content) where commentid = ?";
    private String DELETE_COMMENT_QUERY = "delete from commentsevent where commentid = ?";
}
