package models.ResultJsonObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 16/9/14.
 */
public class JsonGeoDistribution {
    String id;
    int value;

    public JsonGeoDistribution( String countryCode,int numberOfTweets) {
        this.value = numberOfTweets;
        this.id = countryCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString(){
        List<String> list=new ArrayList<>();
        list.add(id);
        list.add(Integer.toString(value));
        return list.toString();
    }
}
