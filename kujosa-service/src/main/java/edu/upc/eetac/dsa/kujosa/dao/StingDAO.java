package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Comentari;
import edu.upc.eetac.dsa.kujosa.entity.StingCollection;

import java.sql.SQLException;

/**
 * Created by sergio on 9/09/15.
 */
public interface StingDAO {
    public Comentari createSting(String userid, String subject, String content) throws SQLException;
    public Comentari getStingById(String id) throws SQLException;
    public StingCollection getStings(long timestamp, boolean before) throws SQLException;
    public Comentari updateSting(String id, String subject, String content) throws SQLException;
    public boolean deleteSting(String id) throws SQLException;
}
