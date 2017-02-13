package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by satheesh on 5/9/14.
 */
@Entity
@Table(name="trendinghashtags")
public class TrendingHashTags extends Model implements Serializable{

    @EmbeddedId
    public TrendingHashTagKey key;

     @Column(name="score")
     public double score;


    @Embeddable
    public class TrendingHashTagKey{
        @Column(name="entityId")
        public Integer entityId;
        @Column(name="hashTag")
        public String hashTag;
        @Column(name="timestamp")
        public Timestamp timestamp;
        public TrendingHashTagKey(Integer entityId,String hashTag,Timestamp timestamp){
            this.entityId=entityId;
            this.hashTag=hashTag;
            this.timestamp=timestamp;
        }
        public boolean equals(Object obj){
            if (obj instanceof TrendingHashTagKey){
                TrendingHashTagKey tkey=(TrendingHashTagKey) obj;
                return this.entityId.equals(tkey.entityId)&&this.hashTag.equals(tkey.hashTag)&&this.timestamp.equals(tkey.timestamp);
            }
            return false;
        }
        public int hashCode(){
            return this.entityId.hashCode()+this.hashTag.hashCode()+this.timestamp.hashCode();
        }
    }

    public Timestamp getTimestamp() {
        return key.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.key.timestamp = timestamp;
    }

    public TrendingHashTags(int entityId, String hashTag, double score, Timestamp timestamp){
        this.score=score;
        this.key=new TrendingHashTagKey(entityId,hashTag,timestamp);
    }

    public Integer getEntityId() {
        return key.entityId;
    }

    public void setEntityId(Integer entityId) {
        this.key.entityId = entityId;
    }

    public String getHashTag() {
        return key.hashTag;
    }

    public void setHashTag(String hashTag) {
        this.key.hashTag = hashTag;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public static Model.Finder<TrendingHashTagKey,TrendingHashTags> find = new Model.Finder<TrendingHashTagKey,TrendingHashTags>(TrendingHashTagKey.class, TrendingHashTags.class);
}
