package repositories;

import models.SingletonModels.GeoLocation;

import java.util.List;

/**
 * Created by shubham on 16/9/14.
 */
public class GeoLocationsRepository {
    public static List<GeoLocation> get(){
        return GeoLocation.find.all();
    }
}
