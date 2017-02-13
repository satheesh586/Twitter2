package models;

import play.db.ebean.Model;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by shubham on 9/9/14.
 */
@Entity
public class CompositeKeyExample extends Model{


    @EmbeddedId
    public CKey key;

    public String name;

    public CompositeKeyExample(int id1,int id2,String name) {
        this.key=new CKey(id1,id2);
        this.name=name;
    }
    @Embeddable
    public class CKey {
        public Integer id1;
        public Integer id2;
        public boolean equals(Object obj){
            if (obj instanceof CKey)
            {
                CKey ckeyObj=(CKey) obj;
                return this.id1.equals(ckeyObj.id1)&&this.id2.equals(ckeyObj.id2);
            }
            return false;
        }
        public int hashCode(){
            return this.id1.hashCode()+this.id2.hashCode();
        }
        public CKey(int id1, int id2) {
            this.id1=id1;
            this.id2=id2;
        }
    }

}
