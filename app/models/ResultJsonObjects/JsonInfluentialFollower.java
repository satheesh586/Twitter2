package models.ResultJsonObjects;

import play.db.ebean.Model;

/**
 * Created by satheesh on 16/9/14.
 */
public class JsonInfluentialFollower extends Model {

    String screenName;
    double score;
    long followercount;


    public JsonInfluentialFollower(String screenName, double score, long followercount) {
        this.screenName = screenName;
        this.score = score;
        this.followercount=followercount;
    }



    public long getFollowercount() {
        return followercount;
    }

    public void setFollowercount(long followercount) {
        this.followercount = followercount;
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
}
