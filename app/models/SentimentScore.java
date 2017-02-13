package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by shubham on 16/9/14.
 */
@Entity
@Table(name="entitysentimentscore")
public class SentimentScore extends Model {
    @Column(name="entityId")
    private int entityId;
    @Column(name="positivetweetscount")
    private int numberOfPositiveTweets;
    @Column(name="negativetweetscount")
    private int numberOfNegativeTweets;

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getNumberOfPositiveTweets() {
        return numberOfPositiveTweets;
    }

    public void setNumberOfPositiveTweets(int numberOfPositiveTweets) {
        this.numberOfPositiveTweets = numberOfPositiveTweets;
    }

    public int getNumberOfNegativeTweets() {
        return numberOfNegativeTweets;
    }

    public void setNumberOfNegativeTweets(int numberOfNegativeTweets) {
        this.numberOfNegativeTweets = numberOfNegativeTweets;
    }

    @Column(name="timestamp")

    private Timestamp timestamp;

    public SentimentScore(int entityId, int positive, int negative, Timestamp timestamp) {
        this.entityId =entityId;
        this.numberOfPositiveTweets=positive;
        this.numberOfNegativeTweets =negative;
        this.timestamp=timestamp;
    }

    public void setTimestamp(Timestamp timestamp){
        this.timestamp=timestamp;
    }

    public Timestamp getTimestamp(){
        return timestamp;
    }

    public static Model.Finder<Integer, SentimentScore> find=new Model.Finder<Integer,SentimentScore>(Integer.class,SentimentScore.class);

}
