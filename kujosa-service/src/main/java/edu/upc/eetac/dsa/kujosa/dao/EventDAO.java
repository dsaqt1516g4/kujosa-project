package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Event;
import edu.upc.eetac.dsa.kujosa.entity.EventCollection;
import edu.upc.eetac.dsa.kujosa.entity.UserCollection;

import java.sql.SQLException;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *     DONE:
 *     -createEvent
 *     -getEventByRatio
 *     -getEvent
 *     -getEvents
 *     -updateEvent
 *     -deleteEvent
 *     -getUsersofEventId
 */
public interface EventDAO {
    public Event createEvent(String userid, String titol, String text, long lat, long lon, long startDate, long endDate) throws SQLException;
    public Event getEventByRatio(int Ratio) throws SQLException;
    public Event getEvent(String eventid) throws SQLException;
    public EventCollection getEvents(int length, long before, long after) throws SQLException;
    public Event updateEvent(String eventid, String titol, String text, long startDate, long endDate) throws SQLException;
    public boolean deleteEvent(String eventid) throws SQLException;
    public UserCollection getUsersofEventId(String eventid) throws SQLException;

}
