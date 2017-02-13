package models;

import play.db.ebean.Model;

import javax.persistence.Id;
import javax.persistence.Entity;

/**
 * Created by shubham on 22/8/14.
 */

@Entity
public class Query extends Model {
    @Id
    public int id;

    public String name;

    public Query(String name)
    {
        this.name=name;
    }

    public static Finder<Integer,Query> find=new Finder<Integer,Query>(
            Integer.class,Query.class
    );

    public int getId() {
        return id;
    }
}
