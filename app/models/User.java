package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by shubham on 17/9/14.
 */
@Entity
public class User extends Model {
    @Id
    String screenName;

    String accessToken;

    String accessSecret;

    public User(String screenName,String accessToken,String accessSecret) {
        this.screenName=screenName;
        this.accessSecret=accessSecret;
        this.accessToken=accessToken;
    }

    public static Finder<String,User> find=new Finder<String,User>(
            String.class,User.class
    );


    public String getScreenName(){
        return this.screenName;
    }

    public String getAccessToken(){
        return this.accessToken;
    }

    public String getAccessSecret(){
        return this.accessSecret;
    }

    public User(){

    }
}
