package logic;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Shariar (Shawn) Emami
 */
public class LogicFactory {
    private  static final String PACKAGE = "logic";
    private static final String SUFFIX = "Logic";
    
    private LogicFactory(){}
    
 public static <T>T getFor(String entityName){
     try{
        T newInstance = getFor((Class<T>)Class.forName(PACKAGE + entityName + SUFFIX));
        return newInstance;
     }catch(ClassNotFoundException ex){
        ex.getMessage();
        return null;
     }
      
    }
 
 public static <T> T getFor(Class<T> type){
     try{
       T newInstance = type.getDeclaredConstructor().newInstance();
       return newInstance;
     }catch(InstantiationException|IllegalAccessException|NoSuchMethodException
             |InvocationTargetException|SecurityException ex){
         ex.getMessage();
        return null;}
 }
   //this is just a place holder to keep the code working.
// public static AccountLogic getFor(String account) {
//       return new AccountLogic();
//  }
    
}
