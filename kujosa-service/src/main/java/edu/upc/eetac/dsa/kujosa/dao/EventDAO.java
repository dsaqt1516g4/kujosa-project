package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Event;
import edu.upc.eetac.dsa.kujosa.entity.EventCollection;

import java.sql.SQLException;

/**
 * Created by juan on 14/12/15.
 */
public interface EventDAO {
    public Event createEvent(String titol, String text, long lat, long lon, long startDate, long endDate, int Ratio)
            throws SQLException;
    public Event getEventByRatio(int Ratio)
            throws SQLException;
    public EventCollection getEvents(long startDate, long endDate)
            throws SQLException;
    public Event updateEvent(int Eventid, String titol, String text, long startDate, long endDate, int Ratio)
            throws SQLException;
    public void deleteEvent(int Eventid)
            throws SQLException;

}
