package models.ResultJsonObjects;

import play.db.ebean.Model;

/**
 * Created by satheesh on 11/9/14.
 */
public class JsonMostreTweeted extends Model {

    private long reTweetCount;
     private String tweetText;

    public long getReTweetCount() {
        return reTweetCount;
    }

    public void setReTweetCount(long reTweetCount) {
        this.reTweetCount = reTweetCount;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public JsonMostreTweeted(String tweettext, long retweetcount) {
        this.reTweetCount=retweetcount;
        this.tweetText=tweettext;
    }
}
