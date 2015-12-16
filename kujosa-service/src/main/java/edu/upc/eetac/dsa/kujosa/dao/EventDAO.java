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
    public Event getEventByRatio(String id)
            throws SQLException;
    public EventCollection getEvents(long timestamp, boolean before)
            throws SQLException;
    public Event updateEvent(String id, String subject, String content)
            throws SQLException;
    public void deleteEvent(String id)
            throws SQLException;

}
