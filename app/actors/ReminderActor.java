package actors;

import akka.actor.UntypedActor;
import models.Query;
import play.Logger;
import services.FetchTweets;

/**
 * Created by shubham on 3/9/14.
 */
public class ReminderActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        Logger.info("onReceive called.");
        Query query= Query.find.byId(1);
        Logger.info("Query name = "+query.name);
        FetchTweets.upDate(1);
    }

    public void stopActor()
    {
        context().stop(self());
    }
}
