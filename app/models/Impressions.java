package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by shubham on 15/9/14.
 */
@Entity
@Table(name="impressions")
public class Impressions extends Model {

    @Column(name="entityId")
    private int entityId;

    @Column(name="numberofimpressions")
    private long numberOfImpressions;

    @Column(name="timestamp")
    private Timestamp timestamp;

    public Impressions(int entityId, long numberOfImpressions, Date timestamp){
        this.entityId=entityId;
        this.numberOfImpressions=numberOfImpressions;
        this.timestamp=new java.sql.Timestamp(timestamp.getTime());
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = new java.sql.Timestamp(timestamp.getTime());
    }

    public long getNumberOfImpressions() {
        return numberOfImpressions;
    }

    public void setNumberOfImpressions(long numberOfImpressions) {
        this.numberOfImpressions = numberOfImpressions;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public static Model.Finder<Integer, Impressions> find=new Model.Finder<Integer,Impressions>(Integer.class,Impressions.class);
}

