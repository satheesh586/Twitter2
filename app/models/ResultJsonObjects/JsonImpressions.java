package models.ResultJsonObjects;

import play.db.ebean.Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shubham on 15/9/14.
 */
public class JsonImpressions extends Model {

    private long numberOfImpressions;
    private String timestamp;

    public long getNumberOfImpressions() {
        return numberOfImpressions;
    }

    public void setNumberOfImpressions(long numberOfImpressions) {
        this.numberOfImpressions = numberOfImpressions;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getString(Timestamp timestamp){
        SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd HH-mm");
        return ft.format(new Date(timestamp.getTime()));
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = getString(timestamp);
    }

    public JsonImpressions(Timestamp timestamp, long numberOfImpressions) {
        this.timestamp = getString(timestamp);
        this.numberOfImpressions = numberOfImpressions;
    }

    public String toString() {
        List<Long> list=new ArrayList<>();
//        list.add(getTimestamp().getTime());
        list.add(getNumberOfImpressions());
        return list.toString();
//        return "["+this.getTimestamp().getTime()+","+this.getNumberOfImpressions()+"]";
    }

}
