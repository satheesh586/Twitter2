package models;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by shubham on 16/9/14.
 */
@Entity
@Table(name="geographicaldistribution")
public class GeographicalDistribution extends Model{

    @EmbeddedId
    private GeographicalDistributionKey key;

    @Column(name="numberoftweets")
    private int numberOfTweets;

    @Embeddable
    public class GeographicalDistributionKey {
        @Column(name="entityId")
        public Integer entityId;
        @Column(name="countrycode")
        public String countryCode;
        public GeographicalDistributionKey(int entityId, String countryCode){
            this.entityId=entityId;
            this.countryCode=countryCode;
        }
        public boolean equals(Object obj){
            if (obj instanceof GeographicalDistributionKey) {
                GeographicalDistributionKey gdKey=(GeographicalDistributionKey) obj;
                return (gdKey.entityId.equals(this.entityId)&&gdKey.countryCode.equals(this.countryCode));
            }
            return false;
        }
        public int hashCode(){
            return this.entityId.hashCode()+this.countryCode.hashCode();
        }
    }

    public GeographicalDistribution(int entityId, String countryCode, int numberOfTweets) {
        this.key=new GeographicalDistributionKey(entityId,countryCode);
        this.numberOfTweets = numberOfTweets;
    }

    public int getEntityId() {
        return this.key.entityId;
    }

    public void setEntityId(int entityId) {
        this.key.entityId = entityId;
    }

    public String getCountryCode() {
        return key.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.key.countryCode = countryCode;
    }

    public int getNumberOfTweets() {
        return numberOfTweets;
    }

    public void setNumberOfTweets(int numberOfTweets) {
        this.numberOfTweets = numberOfTweets;
    }

    public static Model.Finder<GeographicalDistributionKey,GeographicalDistribution> find = new Model.Finder<GeographicalDistributionKey,GeographicalDistribution>(
            GeographicalDistributionKey.class, GeographicalDistribution.class
    );
}
