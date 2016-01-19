package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by juan on 14/12/15.
 */
public class EventDAOQuery {
    public final static String GET_EVENTS_USER_QUERY = "select * from events where startdate > ifnull(?, now()) and userid = unhex(?) order by startdate asc limit ?";
    public final static String GET_EVENTS_USER_QUERY_FROM_LAST = "select * from events where startdate < ifnull(?, now()) and userid = unhex(?) order by startdate asc limit ?";
    public final static String GET_EVENTS_USER_BY_NAME = "select * from events where startdate > ifnull(?, now()) and userid = unhex(?) order by startdate asc limit ?";
    public final static String GET_EVENTS_USER_BY_NAME_FROM_LAST = "select * from events where name LIKE ? and startdate < ifnull(?, now()) and userid = unhex(?) order by startdate asc limit ?";
    public final static String GET_EVENT_BY_ID_QUERY = "select * from events where id = hex(?)";
    public final static String GET_EVENTS_NOW_USER_QUERY = "select * from events where startdate < now() and now() < enddate and userid = unhex(?) order by startdate asc";
    public final static String INSERT_EVENT_USER_QUERY = "insert into events (userid, titol, startdate, enddate) values (unhex(?),?,?,?)";
    public final static String UPDATE_EVENT_QUERY = "update events set titol = ifnull(?, titol), startdate = ifnull(?, startdate), enddate = ifnull(?, enddate) where id = unhex(?)";
    public final static String DELETE_EVENT_QUERY = "delete from events where id = unhex(?)";
    public final static String GET_USERS_STATE_QUERY = "select u.* from users u, state s where s.eventid = unhex(?) and s.state = ? and u.id = s.userid";
    public final static String UPDATE_STATE_QUERY = "update state set state = ifnull(?, state) where userid = unhex(?) and eventid = unhex(?)";
    public final static String GET_EVENTS_STATE_QUERY = "select eventid from state where userid = unhex(?) and state = ?";
    public final static String VALIDATE_USER = "select * from users where userid = unhex(?)";
    public final static String CREATE_EVENT = "insert into events (id, userid, titol, text, lat, lon, start_date, end_date, ratio) values (unhex(?),unhex(?),?,?,?,?,?,?,?)";
    public final static String GET_USERS_OF_EVENT = "select userid from events e where e.id = unhex(?)";
    public final static String GET_EVENT_FROM_LAST="SELECT * FROM events WHERE creation_date > ? ORDER BY creation_date DESC";
    public final static String GET_EVENT_FROM_FIRST="SELECT * FROM events WHERE creation_date < ifnull(?, now()) ORDER BY creation_date DESC LIMIT ?";
    public final static String GET_EVENT_BY_RATIO="SELECT * FROM events WHERE ratio >?";
}
