package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.KujosaMediaType;
import edu.upc.eetac.dsa.kujosa.db.Database;
import edu.upc.eetac.dsa.kujosa.entity.News;
import edu.upc.eetac.dsa.kujosa.entity.NewsCollection;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;

/**
 * Created by twerky on 16/12/15.
 */
public class NewsDAOImpl implements NewsDAO{
    @Override
    public News createNews(String userid, String headline, String body) throws SQLException {
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

            stmt = connection.prepareStatement(NewsDAOQuery.CREATE_NEWS);
            stmt.setString(1, id);
            stmt.setString(2,userid);
            stmt.setString(3, headline);
            stmt.setString(4, body);
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
        return getNewsById(id);
    }


    @Override
    public News getNewsById(String id) throws SQLException {
        News news = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(NewsDAOQuery.GET_NEWS_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                news = new News();
                news.setId(rs.getString("id"));
                news.setUserid(rs.getString("userid"));
                news.setHeadline(rs.getString("headline"));
                news.setBody(rs.getString("body"));
                news.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                news.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return news;
    }

    @Override
    public News getNewsByUser(String id) throws SQLException {
        News news = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(NewsDAOQuery.GET_NEWS_BY_USER);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                news = new News();
                news.setId(rs.getString("id"));
                news.setUserid(rs.getString("userid"));
                news.setHeadline(rs.getString("headline"));
                news.setBody(rs.getString("body"));
                news.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                news.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return news;
    }
    @Path("/{id}")
    @GET
    @Produces(KujosaMediaType.KUJOSA_NEWS)
    public Response getNew(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        News news = null;
        NewsDAO newsDAO = new NewsDAOImpl();
        try {
            news = newsDAO.getNewsById(id);
            if (news == null)
                throw new NotFoundException("News with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(news.getLastModified()));

            // Verify if it matched with etag available in http request
            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);

            // If ETag matches the rb will be non-null;
            // Use the rb to return the response without any further processing
            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }

            // If rb is null then either it is first time request; or resource is
            // modified
            // Get the updated representation and return with Etag attached to it
            rb = Response.ok(news).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Override
    public NewsCollection getNews(long timestamp, boolean before) throws SQLException {
        NewsCollection newsCollection = new NewsCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if (before)
                stmt = connection.prepareStatement(NewsDAOQuery.GET_NEWS);
            else
                stmt = connection.prepareStatement(NewsDAOQuery.GET_NEWS_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                News news = new News();
                news.setId(rs.getString("id"));
                news.setUserid(rs.getString("userid"));
                news.setHeadline(rs.getString("headline"));
                news.setBody(rs.getString("body"));
                news.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                news.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    newsCollection.setNewestTimestamp(news.getLastModified());
                    first = false;
                }
                newsCollection.setOldestTimestamp(news.getLastModified());
                newsCollection.getNews().add(news);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return newsCollection;
    }

    @Override
    public News updateNews(String id, String headline, String body) throws SQLException {
        News news = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(NewsDAOQuery.UPDATE_NEWS);
            stmt.setString(1, headline);
            stmt.setString(2, body);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                news = getNewsById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return news;
    }

    @Override
    public boolean deleteNews(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(NewsDAOQuery.DELETE_NEWS);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
