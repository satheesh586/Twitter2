package controllers;

import models.*;
import models.ResultJsonObjects.JsonEntities;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import play.libs.Json;

import repositories.*;
import services.*;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import views.html.*;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    /*public static Result index() {
        return ok(index.render("Your new application is ready."));
    }*/

    public static Result index() {
//        return ok(login.render());
        if (StartupService.getCurrentUser()==null) return ok(login.render());
        else{
            //TODO: fetch entity list, handle list, brand name list
            return ok(index.render("Your new application is ready."));
        }
    }

//    public static Result index(String page)
//    {
//        return ok("You entered "+page);
//    }

    public static Result test() {
        return ok(test.render());
    }

    public static Result addQuery()
    {
        DynamicForm requestData= Form.form().bindFromRequest();
        String query_name=requestData.get("query_name");
        String handle=requestData.get("handle");
        String brand_name=requestData.get("brand_name");
        int entityId=EntityRepository.addEntity(query_name);
        BrandNameRepository.addBrandName(entityId,brand_name);
        HandleRepository.addHandle(entityId,handle);

        String currentUser=StartupService.getCurrentUser();
        Thread userThread=StartupService.map.get(currentUser);
        if (userThread!=null){
            Logger.info("thread ain't null");
            Logger.info("thread state="+userThread.getState());
            userThread.interrupt();
        }
        userThread=StartupService.createUserThread(UserRepository.getUser(currentUser));
        UserEntitiesRepository.add(currentUser,entityId);

        userThread.start();
        return ok("query_name="+query_name+" handle="+handle+" brand_name="+brand_name);
    }

    public static Result addCKey()
    {
        CompositeKeyExample ckeyexample=new CompositeKeyExample(1,2,"Shubham");
        ckeyexample.save();
        return ok("Worked");
    }

    public static Result getEntities()
    {
        List<JsonEntities> entities=EntityInfoService.getEntityList();
        return ok(Json.toJson(entities));
    }

    public static Result getHandles(String entityId)
    {
        int id=Integer.parseInt(entityId);
        List<String> handles= EntityInfoService.getHandleNames(id);
        return ok(Json.toJson(handles));
    }

    public static Result getBrandNames(String entityId)
    {
        int id=Integer.parseInt(entityId);
        List<String> brandNames= EntityInfoService.getBrandNamesText(id);
        return ok(Json.toJson(brandNames));
    }

    public static Result impressions(int entityId) {
//        int[] arr=new int[10];
        List<Integer> arr=new ArrayList<Integer>();
        for(int i=0;i<10;i++) arr.add(i);
//        return ok(arr.toString());
//        return ok((ImpressionsRepository.getAllImpressions(entityId)).toString());

                return ok(Json.toJson(ImpressionsRepository.getAllImpressions(entityId)));
    }

    public static Result mentions(int entityId) {
        return ok(Json.toJson(MentionsRepository.getAllMentions(entityId)));
//        return ok(MentionsRepository.getAllMentions(entityId).toString());
    }

    public static Result sentimentAnalysis(int entityId) {
        return ok(SentimentScoreRepository.getSentimentScore(entityId).toString());
    }

    public static Result positiveSentimentScore(int entityId) {
        return ok(SentimentScoreRepository.getPositiveSentimentScore(entityId).toString());
    }

    public static Result negativeSentimentScore(int entityId) {
        return ok(SentimentScoreRepository.getNegativeSentimentScore(entityId).toString());
    }

    public static Result geographicalDistribution(int entityId) {
        return ok(Json.toJson(GeographicalDistributionRepository.getAllGeoGraphicDistriBution(entityId)));
    }

    public static Result mostReTweetedTweets(int entityId){
        return ok(Json.toJson(RepositoryMostReTweetedTweets.getListOfMostReTweetedTweets(entityId)));
    }

    public static Result trendingHashTags(int entityId){
        return ok(Json.toJson(RepositoryTrendingHashTags.getListOfTrendingHashTags(entityId)));
    }

    public static Result login() {
        return LoginService.login();
//        return ok(login.render("Your new application is ready."));
    }

    public static Result addUser(String screenName,String accessToken,String accessSecret){
        User user=new User(screenName,accessToken,accessSecret);
        UserRepository.addUser(user);
        return ok("user added");
    }

    public static Result getUserToken(String oauth_token, String oauth_verifier) throws TwitterException {
        AccessToken accessToken = UserAccessTokenService.get(oauth_token, oauth_verifier);
        String screenName=accessToken.getScreenName();
        String token=accessToken.getToken();
        String secret=accessToken.getTokenSecret();

        User user = new User(screenName, token, secret);
        StartupService.setCurrentUser(screenName);
        if (!UserRepository.isPresent(screenName)) {
            UserRepository.addUser(user);
        }

        return redirect("/");
//        return ok(index.render("Welcome "+accessToken.getScreenName()));
    }

    public static Result influentialFollower(int entityId) {
        return ok(Json.toJson(InfluentialFollowerRepository.getInfluentialFollower(entityId)));
    }

    public static Result logout(){
        session().remove("currentUser");
        return redirect("/");
    }

    public static Result getUserScreenName(){
        return ok(StartupService.getCurrentUser());
    }

    public static Result dashboard(){
        return ok(dashboard.render());
    }
}
