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
    Event createEvent(String userid, String titol, String text, long lat, long lon, long startDate, long endDate) throws SQLException;
    Event getEventByRatio(int ratio) throws SQLException;
    Event getEvent(String eventid) throws SQLException;
    EventCollection getEvents(int length, long before, long after) throws SQLException;
    Event updateEvent(String id, String titol, String text, long startDate, long endDate) throws SQLException;
    boolean deleteEvent(String id) throws SQLException;
    UserCollection getUsersofEventId(String id) throws SQLException;

}
