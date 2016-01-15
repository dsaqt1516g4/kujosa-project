package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by juan on 16/12/15.
 */
public interface DocumentDAOQuery {
    String UUID = "select REPLACE(UUID(),'-','')";
    String CREATE_DOCUMENT = "insert into events (id, userid, name, description, path) values (?,?,?,?,?)";
    String GET_NEWS_QUERY = "select * from document where creation_timestamp < ifnull(?, now()) desc limit ?";
    String GET_NEWS_QUERY_FROM_LAST = "select * from document where creation_timestamp > ifnull(?, now()) desc limit ?";
    String INSERT_NEWS_QUERY="insert into document (userid, name, description) values (?, ?, ?)";
    String UPDATE_NEWS_QUERY = "update document set description = ifnull(?, description) where docid = ?";
    String DELETE_NEWS_QUERY = "delete from document where docid = ?";
    String GET_DOCUMENT_BY_ID = "";
}
