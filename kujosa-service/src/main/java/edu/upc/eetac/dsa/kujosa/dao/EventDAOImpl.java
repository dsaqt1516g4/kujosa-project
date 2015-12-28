package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.db.Database;
import edu.upc.eetac.dsa.kujosa.entity.Event;
import edu.upc.eetac.dsa.kujosa.entity.EventCollection;
import edu.upc.eetac.dsa.kujosa.entity.User;
import edu.upc.eetac.dsa.kujosa.entity.UserCollection;

import javax.ws.rs.NotFoundException;
import java.sql.*;

/**
 * Created by juan on 14/12/15.
 */
public class EventDAOImpl implements EventDAO {

    @Override
    public Event createEvent(String titol, String text, long lat, long lon, long startDate, long endDate, int Ratio) throws SQLException {
        Connection connection = null;
        return null;

    }

    @Override
    public Event getEventByRatio(int Ratio) throws SQLException {
        return null;
    }

    @Override
    public Event getEvent(int eventid) throws SQLException {
        return null;
    }

    @Override
    public EventCollection getEventsUser(int lenght, long before, long after, String titol, int userid) throws SQLException {
        return null;
    }

    @Override
    public EventCollection getEventsNow(int userid) throws SQLException {
        return null;
    }

    @Override
    public EventCollection getEventsNowUser(EventCollection events, int userid) throws SQLException {
        return null;
    }

    @Override
    public EventCollection getEvents(long startDate, long endDate) throws SQLException {

    }

    @Override
    public Event updateEvent(int eventid, String titol, String text, long startDate, long endDate, int Ratio) throws SQLException {
        Event event = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EventDAOQuery.UPDATE_EVENT_QUERY);
            stmt.setString(1, titol);
            stmt.setLong(2, startDate);
            stmt.setLong(3, endDate);
            stmt.setInt(4, eventid);

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
    public boolean deleteEvent(int eventid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(EventDAOQuery.DELETE_EVENT_QUERY);
            stmt.setInt(1, eventid);

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
    public void createEvento(int eventid) throws SQLException {
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
                stmt.setInt(2, eventid);
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
    public UserCollection getUsersofEventId(int eventid) throws SQLException {
        UserCollection users = new UserCollection();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(EventDAOQuery.GET_USERS_OF_EVENT);
            stmt.setInt(1, eventid);
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
    public UserCollection getUsersState(int eventid, String state) throws SQLException {
        Event event = null;
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
                    //user.setAge(rs.getInt("age"));
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
    public void updateState(String state, int eventid, int userid) throws SQLException {
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
}
