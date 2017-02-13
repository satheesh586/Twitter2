package models.SingletonModels;

import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by shubham on 16/9/14.
 */
@Entity
@Table(name="geolocation")
public class GeoLocation extends Model{

    @Column(name="country")
    String country;

    @EmbeddedId
    GeoLocationKey key;

    @Embeddable
    public class GeoLocationKey{
        @Column(name="longitude")
        Double longitude;
        @Column(name="latitude")
        Double latitude;

        public GeoLocationKey(double longitude,double latitude){
            this.latitude=latitude;
            this.longitude=longitude;
        }

        public boolean equals(Object obj){
            if (obj instanceof GeoLocationKey) {
                return (this.latitude.equals(((GeoLocationKey) obj).latitude)&&this.longitude.equals(((GeoLocationKey) obj).longitude));
            }
            return false;
        }
        public int hashCode(){
            return this.longitude.hashCode()+this.latitude.hashCode();
        }
    }

    public double getLongitude() {
        return key.longitude;
    }

    public double getLatitude() {
        return key.latitude;
    }

    public String getCountry() {
        return country;
    }

    public GeoLocation(double longitude, double latitude, String country){
        this.key=new GeoLocationKey(longitude,latitude);
        this.country = country;
    }

    public static Model.Finder<GeoLocationKey,GeoLocation> find = new Model.Finder<GeoLocationKey,GeoLocation>(
            GeoLocationKey.class, GeoLocation.class
    );

}
