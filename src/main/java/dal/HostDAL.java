/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import entity.Host;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author junej
 */
public class HostDAL extends GenericDAL<Host>{
    public HostDAL(){
    super(Host.class);
            }

    @Override
    public List<Host> findAll() {
        return findResults( "Host.findAll", null);
    }

    @Override
    public Host findById(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return findResult( "Host.findById", map);    
    }
    public Host findByName(String name){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        return findResult("Host.findByName", map);
    }
    public Host findByUrl(String url) {
        Map<String, Object> map = new HashMap<>();
        map.put("url", url);
        return findResult( "Host.findByUrl", map);
    }
    public List<Host> findByExtractionType(String type){
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        return findResults( "Host.findByExtractionType", map);
    }
    
}
