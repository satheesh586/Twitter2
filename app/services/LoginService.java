package services;

import play.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import static play.mvc.Controller.session;
import static play.mvc.Results.redirect;

/**
 * Created by deepak on 18/9/14.
 */
public class LoginService {
    public static play.mvc.Result login(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setUseSSL(true);

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("1ARS6pl9NjKMdn56k4I0eZhqk")
                .setOAuthConsumerSecret("phOPbBx1xvB9poYJKjukIYphkH81bvagjMYDFpR7XCIqod5gyV")
                .setOAuthAccessToken(null)
                .setOAuthAccessTokenSecret(null);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        RequestToken requestToken = null;
        try {
//            String callbackURL="http://"+StartupService.serverIPAddress+":9000/getUserToken";
            String callbackURL="http://172.16.25.130:9000/getUserToken";
            Logger.info("Callback URL="+callbackURL);
            requestToken = twitter.getOAuthRequestToken(callbackURL);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        session("token",requestToken.getToken());
        session("secret",requestToken.getTokenSecret());

        Logger.info("signin req token : "+requestToken.toString());
        Logger.info("sign in secret : "+requestToken.getTokenSecret());
        Logger.info("authentication url : " + requestToken.getAuthenticationURL().toString());

        return redirect(requestToken.getAuthenticationURL());
    }
}
