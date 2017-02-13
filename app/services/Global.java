package services;

import actors.ReminderActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by shubham on 1/9/14.
 */
public class Global extends GlobalSettings{

    @Override
    public void onStart(Application application)
    {
        Logger.info("Server started. :D");
        StartupService.init();
//        reminderDaemon();
    }

    public void reminderDaemon()
    {
        Logger.info("Scheduling the reminder daemon");
        ActorRef reminderActor= Akka.system().actorOf(Props.create(ReminderActor.class));
        Akka.system().scheduler().schedule(
                Duration.create(0, TimeUnit.MILLISECONDS),
                Duration.create(1, TimeUnit.MINUTES),
                reminderActor,
                "tick",
                Akka.system().dispatcher(),
                null
        );
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FetchTweets.getTweets();
    }

}