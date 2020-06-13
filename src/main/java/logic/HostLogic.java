/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.ValidationException;
import dal.HostDAL;
import entity.Host;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;

/**
 *
 * @author junej
 */
public class HostLogic extends GenericLogic<Host,HostDAL> {
    
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String EXTRACT_TYPE = "extract_type";
    
    HostLogic(){
        super(new HostDAL());
    }
    public Host getHostWithName(String name){
         return get(() -> dal().findByName(name));
    }
    
    public Host getHostWithURL(String url){
         return get(() -> dal().findByUrl(url));
    }
    
    public List<Host> getHostWithExtractionType(String type){
         return get(() -> dal().findByExtractionType(type));
    }

    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("id", "name", "url", "extract_type");    }

    @Override
    public List<String> getColumnCodes() {
         return Arrays.asList(ID, NAME, URL, EXTRACT_TYPE);
    }

    @Override
    public List<?> extractDataAsList(Host e) {
        return Arrays.asList(e.getId(), e.getName(), e.getUrl(), e.getExtractionType());
    }

    @Override
    public Host createEntity(Map<String, String[]> parameterMap) {
        Objects.requireNonNull(parameterMap, "parameterMap cannot be null");
        //same as if condition below
//        if (parameterMap == null) {
//            throw new NullPointerException("parameterMap cannot be null");
//        }

        //create a new Entity object
        Host entity = new Host();

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

        //before using the values in the map, make sure to do error checking.
        //simple lambda to validate a string, this can also be place in another
        //method to be shared amoung all logic classes.
        ObjIntConsumer< String> validator = (value, length) -> {
            if (value == null || value.trim().isEmpty() || value.length() > length) {
                throw new ValidationException("value cannot be null, empty or larger than " + length + " characters");
            }
        };

        //extract the date from map first.
        //everything in the parameterMap is string so it must first be
        //converted to appropriate type. have in mind that values are
        //stored in an array of String; almost always the value is at
        //index zero unless you have used duplicated key/name somewhere.
        String name = parameterMap.get(NAME)[0];
        String url = parameterMap.get(URL)[0];
        String extractType = parameterMap.get(EXTRACT_TYPE)[0];

        //validate the data
        validator.accept(name, 100);
        validator.accept(url, 255);
        validator.accept(extractType, 45);

        //set values on entity
        entity.setExtractionType(extractType);
        entity.setName(name);
        entity.setUrl(url);

        return entity;
    }

    @Override
    public List<Host> getAll() {
        return get(() -> dal().findAll());
    }

    @Override
    public Host getWithId(int id) {
        return get(() -> dal().findById(id));
        
    }
    
    
}
