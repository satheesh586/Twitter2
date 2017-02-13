package repositories;

import com.avaje.ebean.Ebean;
import models.Impressions;
import models.ResultJsonObjects.JsonImpressions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 15/9/14.
 */
public class ImpressionsRepository {

    public static Timestamp getLargestTimestamp(int entityId) {
        return getAllImpressionsInOrder(entityId).get(0).getTimestamp();

    }

    public static boolean isPresent(int entityId){
        int resultsize= Impressions.find.where().eq("entityId", entityId).findRowCount();
        if(resultsize>=1)
            return true;
        return false;
    }

    public static List<Impressions> getAllImpressionsInOrder(int entityId) {
        List<Impressions> impressions = Impressions.find.where().eq("entityId", entityId).order().desc("timestamp").findList();
        return impressions;
    }


    public static void upDateRepository(int entityId,long followerCount) {

        Impressions impressions=getAllImpressionsInOrder(entityId).get(0);
        impressions.setNumberOfImpressions(impressions.getNumberOfImpressions()+followerCount);
        impressions.update();
    }

    public static void insert(Impressions impressions) {
        Ebean.save(impressions);
    }

    public static List<Impressions> getAllImpressionsInAscOrder(int entityId) {
        List<Impressions> impressions = Impressions.find.where().eq("entityId", entityId).order().asc("timestamp").findList();
        return impressions;
    }

    public static List<JsonImpressions> getAllImpressions(int entityId){
        List<JsonImpressions> jsonImpressionses=new ArrayList<>();
        for(Impressions impressions:getAllImpressionsInAscOrder(entityId)){
            Timestamp ISTtimestamp=new Timestamp(impressions.getTimestamp().getTime()+11L*30L*60000L);
            jsonImpressionses.add(new JsonImpressions(ISTtimestamp,impressions.getNumberOfImpressions()));
        }
        return jsonImpressionses;
    }


}
