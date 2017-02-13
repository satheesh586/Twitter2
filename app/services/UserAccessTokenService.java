package services;

import play.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import static play.mvc.Controller.session;

/**
 * Created by deepak on 18/9/14.
 */
public class UserAccessTokenService {
    public static AccessToken get(String oauth_token,String oauth_verifier) throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setUseSSL(true);

        //the following is set without accesstoken- desktop client
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("1ARS6pl9NjKMdn56k4I0eZhqk")
                .setOAuthConsumerSecret("phOPbBx1xvB9poYJKjukIYphkH81bvagjMYDFpR7XCIqod5gyV")
                .setOAuthAccessToken(null)
                .setOAuthAccessTokenSecret(null);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        String token = session("token");
        String secret = session("secret");
        RequestToken requestToken = new RequestToken(token,secret);

        System.out.println("auth token : "+oauth_token);
        System.out.println("callback req token : "+requestToken.toString());

        Logger.info(token+" "+secret);
        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);

        System.out.println("Access token" + accessToken.getToken());
        System.out.println("Access token secret"+accessToken.getTokenSecret());
        System.out.println("get user id" + accessToken.getUserId()); //id1664367438
        System.out.println(accessToken.getScreenName());
        System.out.println();

        return accessToken;
    }
}
