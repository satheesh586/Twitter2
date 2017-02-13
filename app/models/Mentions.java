package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by shubham on 16/9/14.
 */
@Entity
@Table(name="mentions")
public class Mentions extends Model {

    @EmbeddedId
    public MentionsKey key;

    @Column(name="numberofmentions")
    int numberOfMentions;

    public Mentions(int entityId, int numberOfMentions, Timestamp timeStamp) {
        this.numberOfMentions = numberOfMentions;
        this.key=new MentionsKey(entityId,timeStamp);
    }

    @Embeddable
    public class MentionsKey {
        public Integer entityId;
        public Timestamp timestamp;
        public MentionsKey(int entityId,Timestamp timestamp) {
            this.entityId=entityId;
            this.timestamp=timestamp;
        }
        public boolean equals(Object obj) {
            if (obj instanceof MentionsKey){
                MentionsKey mkey=(MentionsKey) obj;
                return (this.entityId==mkey.entityId && this.timestamp==((MentionsKey) obj).timestamp);
            }
            return false;
        }
        public int hashCode() {
            return this.entityId.hashCode()+this.timestamp.hashCode();
        }
    }

    public int getNumberOfMentions() {
        return numberOfMentions;
    }

    public void setNumberOfMentions(int numberOfMentions) {
        this.numberOfMentions = numberOfMentions;
    }

    public int getEntityId() {
        return this.key.entityId;
    }

    public void setEntityId(int entityId) {
       this.key.entityId = entityId;
    }

    public Timestamp getTimeStamp() {
        return this.key.timestamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.key.timestamp = timeStamp;
    }


    public static Model.Finder<MentionsKey,Mentions> find= new Model.Finder<MentionsKey,Mentions>(MentionsKey.class, Mentions.class);

}
