package utilities;

/**
 * Created by shubham on 15/9/14.
 */

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by satheesh on 3/9/14.
 */
public class TwitterCredentials {
    private final static String CONSUMER_KEY = "1ARS6pl9NjKMdn56k4I0eZhqk";
    private final static String CONSUMER_KEY_SECRET = "phOPbBx1xvB9poYJKjukIYphkH81bvagjMYDFpR7XCIqod5gyV";
    private String ACCESS_TOKEN=null;//="380714562-r9v9CtrHfM5PTwSe18Do5aXhKorFedw1H2EL9Dq5";
    private String ACCESS_TOKEN_SECRET=null;//="IA60iklXLgBlgI6yjZpzoDwaZR31OlUnt7yzqUeyCJfUR";

    static ConfigurationBuilder cb;
    public ConfigurationBuilder getConfiguration(String accessToken,String accessSecret) {
        cb= new ConfigurationBuilder();
        this.ACCESS_TOKEN=accessToken;
        this.ACCESS_TOKEN_SECRET=accessSecret;
        cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
                .setOAuthAccessToken(this.ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(this.ACCESS_TOKEN_SECRET);
        return cb;
    }
    /*public Twitter getTwitterInstance(){
//        getConfiguration();
//        this.ACCESS_TOKEN=accessToken;
//        this.ACCESS_TOKEN_SECRET=accessSecret;
        TwitterFactory factory = new TwitterFactory(getConfiguration().build());
        Twitter twitter = factory.getInstance();
        return twitter;

    }*/
}

