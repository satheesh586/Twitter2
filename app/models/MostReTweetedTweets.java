package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by deepak on 4/9/14.
 */
@Entity
@Table(name="Mostretweetedtweets")
public class MostReTweetedTweets extends Model {

    @Column(name="tweetId")
    private long tweetId;

    @Column(name="entityId")
    private int entityId;

    @Column(name="tweettext")
    private String tweetText;

    @Column(name="retweetcount")
    private long reTweetCount;

    @Column(name="parenttweetid")
    private long parentTweetId;


    public MostReTweetedTweets(long tweetId, int entityId, String tweetText, long reTweetCount, long parentTweetId) {
        this.tweetId = tweetId;
        this.entityId = entityId;
        this.tweetText = tweetText;
        this.reTweetCount = reTweetCount;
        this.parentTweetId = parentTweetId;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public int getEntityId() { return entityId; }

    public String getTweetText() {
        return tweetText;
    }

    public long getReTweetCount() {
        return reTweetCount;
    }

    public long getParentTweetId() {
        return parentTweetId;
    }

    public void setEntityId(int entityId) { this.entityId = entityId; }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public void setReTweetCount(long reTweetCount) {
        this.reTweetCount = reTweetCount;
    }

    public void setParentTweetId(long parentTweetId) {
        this.parentTweetId = parentTweetId;
    }

    public static Finder<Long,MostReTweetedTweets> find = new Finder<Long,MostReTweetedTweets>(
            Long.class, MostReTweetedTweets.class
    );
}
