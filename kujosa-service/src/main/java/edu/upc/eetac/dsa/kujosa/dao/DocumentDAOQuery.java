package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by juan on 16/12/15.
 */
public interface DocumentDAOQuery {
    //String UUID = "select REPLACE(UUID(),'-','')";
    String CREATE_DOCUMENT = "insert into document (id, userid, name, description, path) values (unhex(?),unhex(?),?,?,?)";
    String GET_DOCUMENTS_AFTER = "select hex(d.id) as id, hex(d.userid) as userid, d.name, d.creation_timestamp, d.last_modified, u.fullname from document d, users u  where creation_timestamp > ? and u.id=d.userid order by creation_timestamp desc limit 25";
    String GET_DOCUMENTS="select hex(d.id) as id, hex(d.userid) as userid, d.name, d.creation_timestamp, d.last_modified, u.fullname from document d, users u where creation_timestamp < ? and d.id=s.userid order by creation_timestamp desc limit 25";
    String UPDATE_DOCUMENT = "update document set description = ifnull(?, description) where id = unhex(?)";
    String DELETE_DOCUMENT = "delete from document where id = unhex(?)";
    String GET_DOCUMENT_BY_ID = "select hex(d.id) as id, hex(d.userid) as userid, d.description, d.name, d.creation_timestamp, d.last_modified, d.path from document d where d.id=unhex(?)";
}
