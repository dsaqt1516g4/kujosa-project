package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by sergio on 9/09/15.
 */
public class StingDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_STING = "insert into stings (id, userid, subject, content) values (UNHEX(?), unhex(?), ?, ?)";
    public final static String GET_STING_BY_ID = "select hex(s.id) as id, hex(s.userid) as userid, s.content, s.subject, s.creation_timestamp, s.last_modified, u.fullname from stings s, users u where s.id=unhex(?) and u.id=s.userid";
    public final static String GET_STINGS = "select hex(s.id) as id, hex(s.userid) as userid, s.subject, s.creation_timestamp, s.last_modified, u.fullname from stings s, users u where creation_timestamp < ? and u.id=s.userid order by creation_timestamp desc limit 25";
    public final static String GET_STINGS_AFTER = "select hex(s.id) as id, hex(s.userid) as userid, s.subject, s.creation_timestamp, s.last_modified, u.fullname from stings s, users u  where creation_timestamp > ? and u.id=s.userid order by creation_timestamp desc limit 25";
    public final static String UPDATE_STING = "update stings set subject=?, content=? where id=unhex(?) ";
    public final static String DELETE_STING = "delete from stings where id=unhex(?)";
}
