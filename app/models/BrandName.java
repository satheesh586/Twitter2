package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by shubham on 22/8/14.
 */

@Entity
public class BrandName extends Model {
    public String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    public Query query;

    public BrandName(String name,Query query)
    {
        this.name=name;
        this.query=query;
    }
    public static Finder<Query,BrandName> find=new Finder<Query,BrandName>(
            Query.class,BrandName.class
    );

    public Query getQuery() {
        return query;
    }
}
