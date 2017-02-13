package models.ResultJsonObjects;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 16/9/14.
 */
public class JsonIndividualSentiment {
    private int numberOfTweets;
    private Timestamp timestamp;
    public JsonIndividualSentiment(Timestamp timestamp,int numberOfTweets){
        this.numberOfTweets=numberOfTweets;
        this.timestamp=timestamp;
    }
    public String toString(){
        List<Long> list=new ArrayList<>();
        list.add(timestamp.getTime());
        list.add((long)numberOfTweets);
        return list.toString();
    }
}
