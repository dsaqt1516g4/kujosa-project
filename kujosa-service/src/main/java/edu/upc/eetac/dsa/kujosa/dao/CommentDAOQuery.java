package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by juan on 09/12/15.
 */

public class CommentDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String GET_COMMENTS_QUERY = "select * from commentsevent where creation_timestamp < ifnull(?, now()) and eventid = unhex(?) order by creation_timestamp desc limit ?";
    public final static String GET_COMMENTS_QUERY_FROM_LAST = "select * from commentsevent where creation_timestamp > ifnull(?, now())  and eventid = unhex(?) order by creation_timestamp desc limit ?";
    public final static String GET_COMMENT_BY_ID_QUERY = "select HEX(c.id) as id, hex(c.userid) as userid, hex(c.eventid) as eventid, c.content, c.creation_timestamp, c.last_modified from commentsevent c where c.id=unhex(?)";
    public final static String INSERT_COMMENT_QUERY = "insert into commentsevent (id, userid, eventid, content) values (unhex(?), unhex(?), unhex(?),?)";
    public final static String UPDATE_COMMENT_QUERY = "update commentsevent set content=? where id = unhex(?)";
    public final static String DELETE_COMMENT_QUERY = "delete from commentsevent where id = unhex(?)";
}
