package repositories;

import com.avaje.ebean.Ebean;
import models.Mentions;
import models.ResultJsonObjects.JsonMentions;
import play.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 16/9/14.
 */
public class MentionsRepository {
    public static List<Mentions> getMention(int entityId) {
        return Mentions.find.where().eq("key.entityId",entityId).order().desc("key.timestamp").findList();
    }

    public static List<Mentions> getMentionInAscOrder(int entityId) {
        return Mentions.find.where().eq("key.entityId",entityId).order().asc("key.timestamp").findList();
    }

    public static void save(Mentions mention) {
        Ebean.save(mention);
    }

    public static void upDateRepository(int entityId) {
        Mentions mentions=getMention(entityId).get(0);
        mentions.setNumberOfMentions(mentions.getNumberOfMentions()+1);
        mentions.update();
    }

    public static List<JsonMentions> getAllMentions(int entityId) {
        List<JsonMentions> jsonMentions=new ArrayList<>();
        Logger.info("Inside geallmentions");
        for (Mentions mentions:getMentionInAscOrder(entityId))
        {
            Timestamp ISTtimestamp=new Timestamp(mentions.getTimeStamp().getTime()+11L*30L*60000L);
            jsonMentions.add(new JsonMentions(ISTtimestamp, mentions.getNumberOfMentions()));
        }

        return jsonMentions;

    }
}
