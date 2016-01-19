package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by Kushal on 16/12/15.
 */
public class NewsDAOQuery {
    //public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_NEWS = "insert into news (id,userid, headline, body) values (UNHEX(?),UNHEX(?), ?, ?)";
    public final static String GET_NEWS_BY_USER = "select HEX(n.id) as id ,hex(n.headline) as headline, hex(n.userid) as userid, n.headline, n.body, n.creation_timestamp, n.last_modified from news n, users u where u.id=unhex(?) and u.id=n.userid";
    public final static String GET_NEWS_BY_ID = "select HEX(n.id) as id ,hex(n.headline) as headline, hex(n.userid) as userid, n.headline, n.body, n.creation_timestamp, n.last_modified from news n where n.id=unhex(?)";
    public final static String GET_NEWS = "select hex(s.userid) as userid, s.headline, s.body, s.creation_timestamp, s.last_modified from news s, users u where creation_timestamp < ? and u.id=s.userid order by creation_timestamp desc limit 25";
    public final static String GET_NEWS_AFTER = "select HEX(s.id) as id, hex(s.userid) as userid, s.headline, s.body, s.creation_timestamp, s.last_modified from news s where creation_timestamp  >  ?  order by creation_timestamp desc limit 25";
    public final static String UPDATE_NEWS = "update news set headline=?, body=?  where id=UNHEX(?)";
    public final static String DELETE_NEWS = "delete from news where headline=unhex(?)";
}


