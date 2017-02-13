'use strict';

var tweetisticsControllers=angular.module('tweetisticsControllers',[]);

tweetisticsControllers.controller('TweetisticsController',['$http','$log','$scope','RestService','$timeout',function($http,$log,$scope,RestService,$timeout) {

    $scope.init=function(){
        $scope.getEntityList();
        $scope.getUserScreenName();
        $scope.plotGraph([],'placeholder');
        $scope.plotGraph([],'mentionsPlaceholder');
    }

    $scope.getEntityList=function(){
        RestService.getEntityList().success(function(data){
            $scope.entityList=data;
            console.log(data);
        });
    };

    $scope.getUserScreenName=function(){
        RestService.getUserScreenName().success(function(data){
            $scope.userScreenName=data;
            console.log("screen name="+data);
        });
    };

    $scope.updateCurEntity = function (entity) {
        $scope.curEntity = entity;
    }
    $scope.curEntity = {};

    $scope.print = function (arr) {
        var text = "";
        var n = arr.length;
        for (var i = 0; i < n; i++)
        {
            text += arr[i] + " ";
        }
        return text;
    };

    $scope.updateHandleList=function(){
        if ($scope.curEntity.id === 'undefined') return;
        RestService.getHandleList($scope.curEntity.id).success(function(data){
        $scope.brandHandles=data;
        $scope.brandHandlesText=$scope.print($scope.brandHandles);
    })};$scope.negativeSentimentGraphData

    $scope.updateBrandNameList=function(){
        if ($scope.curEntity.id === 'undefined') return;
        RestService.getBrandNameList($scope.curEntity.id).success(function(data){
            $scope.brandNames=data;
            $scope.brandNamesText=$scope.print($scope.brandNames);
        })};

    $scope.graphData=[];
    $scope.mentionsGraphData=[];
    $scope.positiveSentimentGraphData=[];
    $scope.negativeSentimentGraphData=[];
    $scope.geographicalDistributionGraphData=[];
    $scope.mostRetweetedTweets=[];
    $scope.trendingHashtags=[];
    $scope.getGraphData=function(){
//        if (typeof $scope.curEntity.id ==='undefined') return;
//
        RestService.getImpressionsGraphData(4).success(function(data){
//            $scope.graphData= [[0, 0], [0.5,0.6], [1, 1]];
            $scope.graphData=data;
//            console.log(data);
            $scope.plotAMGraph(data,"chartDiv","numberOfImpressions");
            $scope.plotGraph($scope.graphData,'placeholder');
        });

        RestService.getMentionsGraphData(4).success(function(data){
            $scope.mentionsGraphData=data;
            console.log(data);
            $scope.plotAMGraph(data,"mentionsChartDiv","numberofmentions");
            $scope.plotGraph($scope.mentionsGraphData,'mentionsPlaceholder');
        });

        RestService.getPositiveSentimentGraphData($scope.curEntity.id).success(function(data){
            $scope.positiveSentimentGraphData=data;
            RestService.getNegativeSentimentGraphData($scope.curEntity.id).success(function(data2){
                $scope.negativeSentimentGraphData=data2;
                var result=new Array();
                result.push({data:data,label:'positive'});
                result.push({data:data2,label:'negative'});
//                result.push(data2);
                $scope.plotGraph2(result,'sentimentsPlaceholder');
            });

//            $scope.plotGraph($scope.positiveSentimentGraphData,'sentimentsPlaceholder');
        });

        RestService.getGeographicalDistributionGraphData($scope.curEntity.id).success(function(data){
            $scope.geographicalDistributionGraphData=data;
        });

        RestService.getMostRetweetedTweets($scope.curEntity.id).success(function(data){
            $scope.mostRetweetedTweets=data;

        });

        RestService.getTrendingHashtags($scope.curEntity.id).success(function(data){
            $scope.trendingHashtags=data;
        });
    };


    $scope.plotGraph=function(data,divId){
        $.plot($('#'+divId), [{
            data: data,label:$scope.curEntity.name}], {
//            yaxis: { max: 1 },
            lines:{
                show:true
            },
            points:{
                show:true
            },
            grid:{
                hoverable:true
            },
            xaxis:{
                mode: 'time'
            }
        });
    };
    $scope.plotGraph2=function(dataInfo,divId){
        $.plot($('#'+divId), dataInfo, {
//            yaxis: { max: 1 },
            lines:{
                show:true
            },
            points:{
                show:true
            },
            grid:{
                hoverable:true
            },
            xaxis:{
                mode: 'time'
            }
        });
    };
    $scope.$watch('curEntity',function(){
        if (typeof $scope.curEntity.id ==='undefined') return;
//        console.log('curEntity='+$scope.curEntity.name);
        $scope.updateHandleList();
        $scope.updateBrandNameList();
        $scope.getGraphData();
//        $scope.plotGraph($scope.graphData);
    });

    $scope.intervalFunction = function(){
        $timeout(function(){
            if (typeof $scope.curEntity.id === 'undefined') {}
            else {
                $scope.updateHandleList();
                $scope.updateBrandNameList();
                $scope.getGraphData();
                $scope.drawMap();

            }
            $scope.intervalFunction();
        },10000)
    };

    $scope.intervalFunction();

    $scope.drawMap=function(){
        var map=new AmCharts.AmMap();
        map.pathToImages="assets/images/";
        var dataProvider = {
            map: "worldLow",
            areas:$scope.geographicalDistributionGraphData
        };
        map.dataProvider = dataProvider;
        map.areasSettings = {
            autoZoom: true,
            selectedColor: "#CC0000"
        };
//        map.smallMap = new AmCharts.SmallMap();
        map.colorSteps=10;
        map.valueLegend={
            right: 10,
            minValue: "little",
            maxValue: "a lot!"
        };
        map.write("mapdiv");

    };

    $scope.drawMap();
// generate some random data, quite different range
    $scope.generateChartData=function () {
        var chartData = [];
        var firstDate = new Date();
        firstDate.setDate(firstDate.getDate() - 100);

        for (var i = 0; i < 100; i++) {
            // we create date objects here. In your data, you can have date strings
            // and then set format of your dates using chart.dataDateFormat property,
            // however when possible, use date objects, as this will speed up chart rendering.
            var newDate = new Date(firstDate);
            newDate.setDate(newDate.getDate() + i);

            var visits = Math.round(Math.random() * 40) + 100;
            var hits = Math.round(Math.random() * 80) + 500;
            var views = Math.round(Math.random() * 6000);

            chartData.push({
                timestamp: newDate,
                numberOfImpressions: visits,
                hits: hits,
                views: views
            });
        }
        return chartData;
    }

//    $scope.chartData = $scope.generateChartData();

    $scope.plotAMGraph=function(chartData,chartDiv,yaxis){
        /*var n=chartData.length;
        alert(n);
        var chartData2=[];
        for(var i=0;i<n;i++)
        {
            var obj={
                timestamp: chartData[i].timestamp,
                numberOfImpressions: chartData[i].numberOfImpressions
            };
            if (i<2){
                alert(obj.timestamp);
                alert(obj.numberOfImpressions);
            }

            chartData2.push(obj);
        }*/

//        chartData=chartData2;
        $scope.chart = AmCharts.makeChart(chartDiv, {
            "type": "serial",
            "theme": "none",
            "pathToImages": "assets/images/",
            "dataDateFormat":"YYYY-MM-DD JJ-NN",
            "legend": {
                "useGraphSettings": true
            },
            "mouseWheelZoomEnabled":true,
            "dataProvider": chartData,
            "valueAxes": [{
                "id":"v1",
                "axisColor": "#FF6600",
                "axisThickness": 2,
                "gridAlpha": 0,
                "axisAlpha": 1,
                "position": "left"
            }/*, {
             "id":"v2",
             "axisColor": "#FCD202",
             "axisThickness": 2,
             "gridAlpha": 0,
             "axisAlpha": 1,
             "position": "right"
             }, {
             "id":"v3",
             "axisColor": "#B0DE09",
             "axisThickness": 2,
             "gridAlpha": 0,
             "offset": 50,
             "axisAlpha": 1,
             "position": "left"
             }*/],
            "graphs": [{
                "id": "g1",
                "valueAxis": "v1",
                "lineColor": "#FF6600",
                "bullet": "round",
                "bulletBorderThickness": 1,
                "hideBulletsCount": 30,
                "title": "red line",
                "valueField": yaxis,
                "type": "smoothedLine",
                "fillAlphas": 0
            }/*, {
                "valueAxis": "v2",
                "lineColor": "#FCD202",
                "bullet": "square",
                "bulletBorderThickness": 1,
                "hideBulletsCount": 30,
                "title": "yellow line",
                "valueField": "hits",
                "fillAlphas": 0
            }, {
                "valueAxis": "v3",
                "lineColor": "#B0DE09",
                "bullet": "triangleUp",
                "bulletBorderThickness": 1,
                "hideBulletsCount": 30,
                "title": "green line",
                "valueField": "views",
                "fillAlphas": 0
            }*/],
            "chartScrollbar": {
                "graph": "g1",
                "scrollbarHeight":30
            },
            "chartCursor": {
                "cursorPosition": "mouse"
            },
            "categoryField": "timestamp",
            "categoryAxis": {
                "parseDates": true,
                "axisColor": "#DADADA",
                "minorGridEnabled": true,
                "minPeriod": "mm"
            }
        });

        $scope.zoomChart=function (){
            $scope.chart.zoomToIndexes($scope.chart.dataProvider.length - 20, $scope.chart.dataProvider.length - 1);
        };

        $scope.chart.addListener("dataUpdated", $scope.zoomChart);

        $scope.zoomChart();
    }

    $scope.getGraphData();
//    $scope.plotAMGraph($scope.generateChartData());

}]);
