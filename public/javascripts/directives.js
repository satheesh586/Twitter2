/**
 * Created by shubham on 11/9/14.
 */
'use strict';

var tweetisticsDirectives=angular.module('tweetisticsDirectives',[]);

tweetisticsDirectives.directive('addEntityForm', function () {
    return{
        restrict: 'E',
        templateUrl: '/assets/javascripts/addEntityForm.html'
    };
});
tweetisticsDirectives.directive('editEntityForm', function () {
    return{
        restrict: 'E',
        templateUrl: '/assets/javascripts/editEntityForm.html'
    };
});