package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;


import com.avaje.ebean.*;



@Entity 
public class Category extends Model {

    @Id
    public Long id;
    
    @Constraints.Required
    public String name;
    
    /**
     * Generic query helper for entity Category with id Long
     */
    public static Model.Finder<Long,Category> find = new Model.Finder<Long,Category>(Long.class, Category.class);

    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Category c: Category.find.orderBy("name").findList()) {
            options.put(c.id.toString(), c.name);
        }
        return options;
    }

}

