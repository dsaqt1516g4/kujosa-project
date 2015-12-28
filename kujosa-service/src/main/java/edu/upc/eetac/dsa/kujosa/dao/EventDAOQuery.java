package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by juan on 14/12/15.
 */
public class EventDAOQuery {
    public final static String GET_EVENTS_USER_QUERY = "select * from events where dateInitial > ifnull(?, now()) and userid = ? order by dateInitial asc limit ?";
    public final static String GET_EVENTS_USER_QUERY_FROM_LAST = "select * from events where dateInitial < ifnull(?, now()) and userid = ? order by dateInitial asc limit ?";
    public final static String GET_EVENTS_USER_BY_NAME = "select * from events where name LIKE ? and dateInitial > ifnull(?, now()) and userid = ? order by dateInitial asc limit ?";
    public final static String GET_EVENTS_USER_BY_NAME_FROM_LAST = "select * from events where name LIKE ? and dateInitial < ifnull(?, now()) and userid = ? order by dateInitial asc limit ?";
    public final static String GET_EVENT_BY_ID_QUERY = "select * from events where eventid = ?";
    public final static String GET_EVENTS_NOW_USER_QUERY = "select * from events where dateInitial < now() and now() < dateFinish and userid = ? order by dateInitial asc";
    public final static String INSERT_EVENT_USER_QUERY = "insert into events (userid, name, dateInitial, dateFinish) values (?,?,?,?)";
    public final static String UPDATE_EVENT_QUERY = "update events set name = ifnull(?, name), dateInitial = ifnull(?, dateInitial), dateFinish = ifnull(?, dateFinish) where eventid = ?";
    public final static String DELETE_EVENT_QUERY = "delete from events where eventid = ?";
    public final static String GET_USERS_STATE_QUERY = "select u.* from users u, state s where s.eventid = ? and s.state = ? and u.userid = s.userid";
    public final static String UPDATE_STATE_QUERY = "update state set state = ifnull(?, state) where userid = ? and eventid = ?";
    public final static String GET_EVENTS_STATE_QUERY = "select eventid from state where userid = ? and state = ?";
    public final static String VALIDATE_USER = "select * from users where userid = ?";
    public final static String CREATE_EVENT_STATE_PENDING_QUERY = "insert into state values (?, ?, 'pending')";
    public final static String GET_USERS_OF_EVENT = "select userid from events e where e.eventid = ?";
}
