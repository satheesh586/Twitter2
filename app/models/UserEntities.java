package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by shubham on 18/9/14.
 */

@Entity
public class UserEntities extends Model {
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="screenName")
    public User user;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id")
    public Query query;

    public UserEntities(User user,Query query){
        this.user=user;
        this.query=query;
    }

    public static Finder<User,UserEntities> find=new Finder<User,UserEntities>(
            User.class,UserEntities.class
    );
}
