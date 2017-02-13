package services;

import models.SingletonModels.GeoLocation;
import play.Logger;
import repositories.GeoLocationsRepository;
import repositories.GeographicalDistributionRepository;
import twitter4j.Status;

import java.util.List;

/**
 * Created by shubham on 16/9/14.
 */
public class GeoLocationService {
    public static void updateDatabase(Status tweetInfo, int entityId){
        String countryCode = null;
        if(tweetInfo.getGeoLocation()==null) {

//            approximateWithUserLocation(tweetInfo,entityId);
            /*countryCode = tweetInfo.getUser().getLocation();
            if (RepositoryGeographicalDistribution.isPresent(entityId, countryCode) == true)
                RepositoryGeographicalDistribution.update(entityId, countryCode);
            else
                RepositoryGeographicalDistribution.insert(entityId, countryCode);*/
        }
        else {
            countryCode = getGeoLocationCountry(tweetInfo);
            Logger.info("Tweet's origin country = "+countryCode);
            if (GeographicalDistributionRepository.isPresent(entityId, countryCode) == true)
                GeographicalDistributionRepository.update(entityId,countryCode);
            else
                GeographicalDistributionRepository.insert(entityId,countryCode);
        }
    }

    public static String getGeoLocationCountry(Status tweetInfo) {
        double longitude;
        double latitude;
        String country = null;
        List<GeoLocation> geoLocationList = GeoLocationsRepository.get();
        longitude = tweetInfo.getGeoLocation().getLongitude();
        latitude = tweetInfo.getGeoLocation().getLatitude();
        double distance = Double.MAX_VALUE;
        double newDistance;
        for(GeoLocation geoLocation : geoLocationList){
            newDistance = getDistance(longitude,latitude,geoLocation.getLongitude(),geoLocation.getLatitude());
            if(newDistance < distance){
                distance = newDistance;
                country = geoLocation.getCountry();
            }
        }
        return country;
    }

    public static double getDistance(double longitude1,double latitude1, double longitude2, double latitude2){
        return Math.sqrt(Math.pow(longitude1 - longitude2, 2) + Math.pow(latitude1 - latitude2, 2));
    }

    public static void approximateWithUserLocation(Status tweetInfo,int entityId){
        String countryCode = tweetInfo.getUser().getLocation();

        if(countryCode!=null) {
            System.out.println("countrycode"+countryCode);
            /*if (RepositoryGeographicalDistribution.isPresent(entityId, countryCode) == true)
                RepositoryGeographicalDistribution.update(entityId, countryCode);
            else
                RepositoryGeographicalDistribution.insert(entityId, countryCode);*/
        }
    }


}

