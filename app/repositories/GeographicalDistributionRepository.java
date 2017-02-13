package repositories;

import com.avaje.ebean.Ebean;
import models.GeographicalDistribution;
import models.ResultJsonObjects.JsonGeoDistribution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 16/9/14.
 */
public class GeographicalDistributionRepository {
    public static boolean isPresent(int entityId,String countryCode){
        int resultSize = GeographicalDistribution.find.where().eq("entityId",entityId).eq("countrycode",countryCode).findRowCount();
        if(resultSize>0)
            return true;
        return false;
    }

    public static GeographicalDistribution getGeographicalDistribution(int entityId,String countrycode){
        return GeographicalDistribution.find.where().eq("entityId",entityId).eq("countrycode",countrycode).findList().get(0);
    }

    public static void update(int entityId, String countryCode) {
        GeographicalDistribution geographicalDistribution=getGeographicalDistribution(entityId,countryCode);
        geographicalDistribution.setNumberOfTweets(geographicalDistribution.getNumberOfTweets() + 1);
        geographicalDistribution.update();
    }

    public static List<GeographicalDistribution> getGeographicDistributionOfEntity(int entityId){
        return GeographicalDistribution.find.where().eq("entityId",entityId).findList();
    }

    public static void insert(int entityId, String countryCode) {
        Ebean.save(new GeographicalDistribution(entityId, countryCode, 1));
    }

    public static List<JsonGeoDistribution> getAllGeoGraphicDistriBution(int entityId) {

        List<JsonGeoDistribution> jsonGeoDistributions=new ArrayList<>();
        for(GeographicalDistribution geographicalDistribution:getGeographicDistributionOfEntity(entityId))
        {
            jsonGeoDistributions.add(new JsonGeoDistribution(geographicalDistribution.getCountryCode(),geographicalDistribution.getNumberOfTweets()));
        }

        return jsonGeoDistributions;
    }
}
