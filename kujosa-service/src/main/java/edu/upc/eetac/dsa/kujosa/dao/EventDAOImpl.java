package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.db.Database;
import edu.upc.eetac.dsa.kujosa.entity.Event;
import edu.upc.eetac.dsa.kujosa.entity.EventCollection;
import edu.upc.eetac.dsa.kujosa.entity.User;
import edu.upc.eetac.dsa.kujosa.entity.UserCollection;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import java.sql.*;

/**
 * Created by juan on 14/12/15.
 */
public class EventDAOImpl implements EventDAO {
    @Override
    public Event createEvent(String userid, String titol, String text, long lat, long lon, long startDate, long endDate, int Ratio) throws SQLException {
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

            stmt = connection.prepareStatement(EventDAOQuery.CREATE_EVENT_STATE_PENDING_QUERY);
            stmt.setString(1, id);
            stmt.setString(2,userid);
            stmt.setString(3, titol);
            stmt.setString(4, text);
            stmt.setLong(5,lat);
            stmt.setLong(6,lon);
            stmt.setLong(7,startDate);
            stmt.setLong(8,endDate);
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
        return getEvent(id);
    }


    @Override
    public Event getEventByRatio(int Ratio) throws SQLException {
        return null;
    }

    @Override
    public Event getEvent(String eventid) throws SQLException {
        Event event = new Event();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(EventDAOQuery.GET_EVENT_BY_ID_QUERY);
            stmt.setInt(1, Integer.valueOf(eventid));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                event.setEventid(rs.getInt("eventid"));
                event.setUserid(rs.getString("userid"));
                event.setTitol(rs.getString("titol"));
                event.setStartDate(rs.getTimestamp("startdate").getTime());
                event.setEndDate(rs.getTimestamp("enddate").getTime());
                event.setLastModified(rs.getTimestamp("last_modified")
                        .getTime());

            } else {
                throw new NotFoundException();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return event;
    }

    @Override
    public EventCollection getEventsUser(int length, long before, long after, String titol, int userid) throws SQLException {
        validateUser(userid);
        EventCollection events = new EventCollection();

        Connection connection = null;

        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            if (titol != null) {
                if (before > 0) {
                    stmt = connection.prepareStatement(EventDAOQuery.GET_EVENTS_USER_BY_NAME_FROM_LAST);
                    stmt.setString(1, "%" + titol + "%");
                    stmt.setTimestamp(2, new Timestamp(before));
                } else {
                    stmt = connection.prepareStatement(EventDAOQuery.GET_EVENTS_USER_BY_NAME);
                    stmt.setString(1, "%" + titol + "%");
                    if (after > 0) {
                        stmt.setTimestamp(2, new Timestamp(after));
                    } else
                        stmt.setTimestamp(2, null);
                }
                stmt.setInt(3, Integer.valueOf(userid));
                length = (length <= 0) ? 5 : length;
                stmt.setInt(4, length);
            } else {
                if (before > 0) {
                    stmt = connection.prepareStatement(EventDAOQuery.GET_EVENTS_USER_QUERY_FROM_LAST);
                    stmt.setTimestamp(1, new Timestamp(before));
                } else {
                    stmt = connection.prepareStatement(EventDAOQuery.GET_EVENTS_USER_QUERY);
                    if (after > 0)
                        stmt.setTimestamp(1, new Timestamp(after));
                    else
                        stmt.setTimestamp(1, null);
                }
                stmt.setInt(2, Integer.valueOf(userid));
                length = (length <= 0) ? 5 : length;
                stmt.setInt(3, length);
            }
            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            long lastTimestamp = 0;

            while (rs.next()) {
                Event event = new Event();
                event.setEventid(rs.getInt("eventid"));
                event.setUserid(rs.getString("userid"));
                event.setTitol(rs.getString("name"));
                event.setStartDate(rs.getTimestamp("dateInitial").getTime());
                event.setEndDate(rs.getTimestamp("dateFinish").getTime());
                event.setLastModified(rs.getTimestamp("last_modified")
                        .getTime());
                lastTimestamp = rs.getTimestamp("dateInitial").getTime();

                if (first) {
                    first = false;
                    events.setFirstTimestamp(event.getStartDate());
                }
                events.addEvent(event);
            }
            events.setLastTimestamp(lastTimestamp);
            if (events.getEvents().size() == 0)
                throw new NotFoundException("There aren't events");
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return events;
    }

    private void validateUser(int userid) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(EventDAOQuery.VALIDATE_USER);
            stmt.setInt(1, Integer.valueOf(userid));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                //if (!security.getUserPrincipal().getName().equals(username))
                //    throw new ForbiddenException("You aren't userid = "
                //            + userid);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public EventCollection getEventsNow(int userid) throws SQLException {
        validateUser(userid);
        EventCollection events = new EventCollection();
        events = getEventsNowUser(events, userid);
        if (events.getEvents().size() == 0)
            throw new NotFoundException("There aren't events");
        return events;
    }

    @Override
    public EventCollection getEventsNowUser(EventCollection events, int userid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EventDAOQuery.GET_EVENTS_NOW_USER_QUERY);
            stmt.setInt(1, Integer.valueOf(userid));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                event.setEventid(rs.getInt("eventid"));
                event.setUserid(rs.getString("userid"));
                event.setTitol(rs.getString("titol"));
                event.setStartDate(rs.getTimestamp("startdate").getTime());
                event.setEndDate(rs.getTimestamp("enddate").getTime());
                event.setLastModified(rs.getTimestamp("last_modified")
                        .getTime());
                events.addEvent(event);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return events;
    }

    @Override
    public EventCollection getEvents(int length, long before, long after) throws SQLException {
        EventCollection events = new EventCollection();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            boolean updateFromLast = after > 0;
            if (updateFromLast=true){
            stmt = connection.prepareStatement(EventDAOQuery.GET_EVENT_FROM_LAST);}
            else{
                stmt = connection.prepareStatement(EventDAOQuery.GET_EVENT_FROM_FIRST);}
            if (updateFromLast) {
                stmt.setTimestamp(1, new Timestamp(after));
            } else {
                if (before > 0)
                    stmt.setTimestamp(1, new Timestamp(before));
                else
                    stmt.setTimestamp(1, null);
                length = (length <= 0) ? 10 : length;
                stmt.setInt(2, length);
            }
            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            long oldestTimestamp = 0;
            while (rs.next()) {
                Event event = new Event();
                event.setEventid(rs.getInt("id"));
                event.setTitol(rs.getString("titol"));
                event.setText(rs.getString("text"));
                event.setLat(rs.getLong("latitud"));
                event.setLon(rs.getLong("longitud"));
                event.setRatio(rs.getInt("ratio"));
                oldestTimestamp = rs.getTimestamp("startdate").getTime();
                if (first) {
                    first = false;
                    events.setFirstTimestamp(event.getStartDate());
                }
                events.addEvent(event);
            }
            events.setLastTimestamp(oldestTimestamp);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return events;
    }

    @Override
    public Event updateEvent(String eventid, String titol, String text, long startDate, long endDate, int Ratio) throws SQLException {
        Event event = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EventDAOQuery.UPDATE_EVENT_QUERY);
            stmt.setString(1, titol);
            stmt.setLong(2, startDate);
            stmt.setLong(3, endDate);
            stmt.setString(4, eventid);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                event = getEvent(eventid);
            else {
                throw new NotFoundException("There's no event with eventid = "
                        + eventid);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return event;
    }

    @Override
    public boolean deleteEvent(String eventid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EventDAOQuery.DELETE_EVENT_QUERY);
            stmt.setString(1, eventid);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

    }

    @Override
    public void createEvento(String eventid) throws SQLException {
        UserCollection users;
        users = getUsersofEventId(eventid);
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            for (int i = 0; i < users.getUsers().size(); i++) {
                stmt = null;
                stmt = connection.prepareStatement(EventDAOQuery.CREATE_EVENT_STATE_PENDING_QUERY,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, users.getUsers().get(i).getId());
                stmt.setString(2, eventid);
                stmt.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public UserCollection getUsersofEventId(String eventid) throws SQLException {
        UserCollection users = new UserCollection();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(EventDAOQuery.GET_USERS_OF_EVENT);
            stmt.setString(1, eventid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("userid"));
                users.addUser(user);
            }
        }
        catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return users;
    }

    @Override
    public UserCollection getUsersState(String eventid, String state) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        UserCollection users = new UserCollection();
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EventDAOQuery.GET_USERS_STATE_QUERY);
            stmt.setInt(1, Integer.valueOf(eventid));
            if (state.equals("join") || state.equals("pending")
                    || state.equals("decline")) {
                stmt.setString(2, state);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getString("userid"));
                    user.setUsername(rs.getString("username"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    users.addUser(user);
                }
            } else
                throw new NotFoundException("Incorrect URL");
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return users;
    }

    @Override
    public void updateState(String state, String eventid, int userid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(EventDAOQuery.UPDATE_STATE_QUERY);
            if (state.equals("join") || state.equals("pending")
                    || state.equals("decline")) {
                stmt.setString(1, state);
                stmt.setInt(2, Integer.valueOf(userid));
                stmt.setInt(3, Integer.valueOf(eventid));
                int rows = stmt.executeUpdate();
                if (rows != 1) {
                    throw new NotFoundException(
                            "There's no state with userid = " + userid
                                    + " and eventid = " + eventid);
                }
            } else {
                throw new NotFoundException("Wrong state");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public Event getEventById(String id) {
        return null;
    }
}
