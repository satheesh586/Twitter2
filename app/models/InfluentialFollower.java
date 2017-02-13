package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by satheesh on 16/9/14.
 */
@Entity
@Table(name="influentialfollower")
public class InfluentialFollower extends Model {

    @Column(name="userid")
    Long userid;


    @Column(name="entityid")
    int entityId;

    @Column(name="screenname")
    String screenName;

    @Column(name="score")
    double score;

    @Column(name="timestamp")
    Timestamp timestamp;

    @Column(name="followercount")
    long followercount;

    public InfluentialFollower(Long userid, int entityId, String screenName, double score, Timestamp timestamp, long followercount) {
        this.userid = userid;
        this.entityId = entityId;
        this.screenName = screenName;
        this.score = score;
        this.timestamp = timestamp;
        this.followercount=followercount;
    }

    public long getFollowercount() {
        return followercount;
    }

    public void setFollowercount(long followercount) {
        this.followercount = followercount;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public static Model.Finder<Long,InfluentialFollower> find= new Model.Finder<Long,InfluentialFollower>(Long.class, InfluentialFollower.class);
}
