package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Event;
import edu.upc.eetac.dsa.kujosa.entity.EventCollection;
import edu.upc.eetac.dsa.kujosa.entity.UserCollection;

import java.sql.SQLException;

/**
 * Created by juan on 14/12/15.
 */
public interface EventDAO {
    public Event createEvent(String titol, String text, long lat, long lon, long startDate, long endDate, int Ratio)
            throws SQLException;
    public Event getEventByRatio(int Ratio)
            throws SQLException;
    public Event getEvent(int eventid)
        throws SQLException;
    public EventCollection getEventsUser (int lenght, long before, long after, String titol, int userid)
        throws SQLException;
    public EventCollection getEventsNow (int userid)
        throws SQLException;
    public EventCollection getEventsNowUser (EventCollection events, int userid)
        throws SQLException;
    public EventCollection getEvents(long startDate, long endDate)
            throws SQLException;
    public Event updateEvent(int eventid, String titol, String text, long startDate, long endDate, int Ratio)
            throws SQLException;
    public boolean deleteEvent(int eventid)
            throws SQLException;
    public void createEvento(int eventid)
        throws SQLException;
    public UserCollection getUsersofEventId(int eventid)
        throws SQLException;
    public UserCollection getUsersState(int eventid, String state)
        throws SQLException;
    public void updateState(String state, int eventid, int userid)
        throws SQLException;
}
