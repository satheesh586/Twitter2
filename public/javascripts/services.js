/**
 * Created by shubham on 11/9/14.
 */
'use strict';

var tweetisticsServices=angular.module('tweetisticsServices',[]);

tweetisticsServices.factory('RestService',['$http',function($http){
    return{
        getEntityList:function() {
            console.log("getting entity list");
            return $http.get('/entities');
        },
        getHandleList:function(entityId) {
            console.log("getting handle list");
            return $http.get('/handles?id='+entityId);
        },
        getBrandNameList:function(entityId) {
            console.log("getting brand name list");
            return $http.get('/brandnames?id='+entityId);
        },
        getImpressionsGraphData:function(entityId) {
            console.log("getting impressions graph data");
            return $http.get('/analytics/impression/'+entityId);
        },
        getMentionsGraphData:function(entityId) {
            console.log("getting mentions graph data");
            return $http.get('/analytics/mentions/'+entityId);
        },
        getPositiveSentimentGraphData:function(entityId) {
            return $http.get('analytics/psentiment/'+entityId);
        },
        getNegativeSentimentGraphData:function(entityId) {
            return $http.get('analytics/nsentiment/'+entityId);
        },
        getGeographicalDistributionGraphData:function(entityId) {
            return $http.get('analytics/geographicaldistribution/'+entityId);
        },
        getMostRetweetedTweets:function(entityId){
            return $http.get('analytics/mostretweetedtweets/'+entityId);
        },
        getTrendingHashtags:function(entityId){
            return $http.get('analytics/trendinghashtags/'+entityId);
        },
        logout:function() {
            return $http.get('logout')
        },
        getUserScreenName:function(){
            return $http.get('getuserscreenname');
        }
    }
}]);
