package repositories;

import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import models.TrendingHashTags;
import models.ResultJsonObjects.JsonTrendingHashTags;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by satheesh on 5/9/14.
 */
public class RepositoryTrendingHashTags implements Serializable {

    public static TrendingHashTags isPresent(String hashTag,int entityId){

        List<TrendingHashTags> trendingHashTags=TrendingHashTags.find.where().eq("entityId",entityId).eq("hashTag",hashTag).order().desc("timestamp").findList();

         if(trendingHashTags.size()==0)
                return null;
          return trendingHashTags.get(0);

    }


    public static List<TrendingHashTags> getHashTag(int entityId,String hashTag)
    {
       return TrendingHashTags.find.where().eq("entityId",entityId).eq("hashTag", hashTag).orderBy("timestamp desc").findList();
    }

    public static List<TrendingHashTags> getAllHashTags(int entityId){
        //use the group by------not working for me
        String sql
                = " select entityid,hashtag, sum(score) as score,timestamp "
                + " from trendinghashtags"+ " where entityid="+entityId
                + " group by entityid,hashtag"+ " order by score desc" ;

        RawSql rawSql =
                RawSqlBuilder
                        .parse(sql)
                        .columnMapping("entityid","key.entityId")
                        .columnMapping("hashtag",  "key.hashTag")
                        .columnMapping("sum(score)",    "score")
                        .columnMapping("timestamp" ,"key.timestamp")
                        .create();

        return TrendingHashTags.find.query().setRawSql(rawSql).setMaxRows(10).findList();
    }


    public static void insert(TrendingHashTags trendingHashTags){
       trendingHashTags.save();
    }

    public static void upDateHashTagScore(String hashTag,int entityid) {
        TrendingHashTags trendingHashTags = getHashTag(entityid, hashTag).get(0);
        System.out.println("Time stamp:" + trendingHashTags.getTimestamp());
        trendingHashTags.setScore(trendingHashTags.getScore()+1);
        trendingHashTags.update();
    }

    public static List<JsonTrendingHashTags> getListOfTrendingHashTags(int entityId){
        List<JsonTrendingHashTags> jsonTrendingHashTags=new ArrayList<>();

        for(TrendingHashTags trendingHashTags:getAllHashTags(entityId)){
            jsonTrendingHashTags.add(new JsonTrendingHashTags(trendingHashTags.getHashTag(), trendingHashTags.getScore()));

        }
        return jsonTrendingHashTags;
    }

}
