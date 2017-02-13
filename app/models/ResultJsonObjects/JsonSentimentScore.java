package models.ResultJsonObjects;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 16/9/14.
 */
public class JsonSentimentScore {
    private Timestamp timestamp;
    private int numberOfPositiveTweets;
    private int numberOfNegativeTweets;

    public JsonSentimentScore(Timestamp timestamp,int numberOfPositiveTweets,int numberOfNegativeTweets){
        this.timestamp=timestamp;
        this.numberOfNegativeTweets=numberOfNegativeTweets;
        this.numberOfPositiveTweets=numberOfPositiveTweets;
    }

    public String toString(){
        List<Long> list=new ArrayList<>();
        list.add(this.timestamp.getTime());
        list.add((long)this.numberOfNegativeTweets);
        list.add((long)this.numberOfPositiveTweets);
        return list.toString();
    }
}
