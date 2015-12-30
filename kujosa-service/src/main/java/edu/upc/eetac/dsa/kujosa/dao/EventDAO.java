package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Event;
import edu.upc.eetac.dsa.kujosa.entity.EventCollection;
import edu.upc.eetac.dsa.kujosa.entity.UserCollection;

import java.sql.SQLException;

/**
 * Created by juan on 14/12/15.
 */
public interface EventDAO {
    public Event createEvent(String userid, String titol, String text, long lat, long lon, long startDate, long endDate, int Ratio)
            throws SQLException;
    public Event getEventByRatio(int Ratio)
            throws SQLException;
    public Event getEvent(String eventid)
        throws SQLException;
    public EventCollection getEventsUser (int lenght, long before, long after, String titol, int userid)
        throws SQLException;
    public EventCollection getEventsNow (int userid)
        throws SQLException;
    public EventCollection getEventsNowUser (EventCollection events, int userid)
        throws SQLException;
    public EventCollection getEvents(int length, long before, long after)
            throws SQLException;
    public Event updateEvent(String eventid, String titol, String text, long startDate, long endDate, int Ratio)
            throws SQLException;
    public boolean deleteEvent(String eventid)
            throws SQLException;
    public void createEvento(String eventid)
        throws SQLException;
    public UserCollection getUsersofEventId(String eventid)
        throws SQLException;
    public UserCollection getUsersState(String eventid, String state)
        throws SQLException;
    public void updateState(String state, String eventid, int userid)
        throws SQLException;
    public Event getEventById(String id);
}
