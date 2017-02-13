package models.ResultJsonObjects;

import play.db.ebean.Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shubham on 16/9/14.
 */
public class JsonMentions extends Model {

    public String timestamp;
    public int numberofmentions;

    public JsonMentions(Timestamp timestamp, int numberofimpressions) {
        this.timestamp = getString(timestamp);
        this.numberofmentions = numberofimpressions;
    }

    public String getString(Timestamp timestamp){
        SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd HH-mm");
        return ft.format(new Date(timestamp.getTime()));
    }
    public String toString(){
        List<Long> list=new ArrayList<>();
//        list.add(this.timestamp.getTime());
        list.add((long)this.numberofmentions);
        return list.toString();
    }
}