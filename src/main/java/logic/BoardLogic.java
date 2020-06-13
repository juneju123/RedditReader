/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import common.ValidationException;
import dal.BoardDAL;

import entity.Board;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;

/**
 *
 * @author junej
 */
public class BoardLogic  extends GenericLogic<Board, BoardDAL>{
     public static final String ID = "id";
     public static final String URL = "url";
     public static final String NAME = "name";
     public static final String HOST_ID = "hostId";
      BoardLogic() {
        super(new BoardDAL());
    }
      
     public List<Board> getBoardsWithHostID(int hostId){
         return get(() -> dal().findByHostid(hostId));
     }
     public List<Board> getBoardsWithName(String name){
         return get(() -> dal().findByName(name));
     }
     public Board getBoardWithUrl(String url){
         return get(() -> dal().findByUrl(url));
     }
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("id", "Host_id", "url", "name");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, HOST_ID, NAME, URL);
    }

    @Override
    public List<?> extractDataAsList(Board e) {
        return Arrays.asList(e.getId(), e.getHostid(), e.getUrl(), e.getName());

    }

    @Override
    public Board createEntity(Map<String, String[]> parameterMap) {
          Objects.requireNonNull(parameterMap, "parameterMap cannot be null");
        //same as if condition below
//        if (parameterMap == null) {
//            throw new NullPointerException("parameterMap cannot be null");
//        }

        //create a new Entity object
        Board entity = new Board();

        //ID is generated, so if it exists add it to the entity object
        //otherwise it does not matter as mysql will create an if for it.
        //the only time that we will have id is for update behaviour.
        if (parameterMap.containsKey(ID)) {
            try {
                entity.setId(Integer.parseInt(parameterMap.get(ID)[0]));
            } catch (java.lang.NumberFormatException ex) {
                throw new ValidationException(ex);
            }
    }
        ObjIntConsumer< String> validator = (value, length) -> {
            if (value == null || value.trim().isEmpty() || value.length() > length) {
                throw new ValidationException("value cannot be null, empty or larger than " + length + " characters");
            }
        };
         String url = parameterMap.get(URL)[0];
        String name = parameterMap.get(NAME)[0];
       int hostId = Integer.parseInt(parameterMap.get(HOST_ID)[0]);

        //validate the data
        validator.accept(url, 255);
        validator.accept(name, 100);
        //validator.accept(hostId, 45);

        //set values on entity
        entity.setName(name);
        entity.setUrl(url);
        entity.setHostid(new HostLogic().getWithId(hostId));
        
       

        return entity;
    }
    @Override
    public List<Board> getAll() {
        return get(() -> dal().findAll());    }

    @Override
    public Board getWithId(int id) {
        return get(() -> dal().findById(id));
    }
    
}
