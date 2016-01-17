'use strict';

/**
 * @ngdoc function
 * @name deltahacksApp.controller:PatientsCtrl
 * @description
 * # AboutCtrl
 * Controller of the deltahacksApp
 */
angular.module('deltahacksApp')
  .controller('PatientsCtrl', function ($scope, $http) {
  	$scope.people = [];
  	$scope.phone = "";
  	$scope.gender = "";
  	$scope.address = "";
  	$scope.ename = "";
  	$scope.enumber = "";
  	$http({
    			'method': 'GET',
    			'url' : 'http://172.17.148.27:3000/getPatients',
    			'header' : 'application/json'
    		}).then(function successCallback(response) {
    			for (var i = 0; i < response.data.length; i++){
    				$scope.people.push(response.data[i]);
            $scope.people[i].push({});
    			}

    		}, function errorCallback(response) {
    			console.log(response);
    		});
    $scope.info = function(person, i){
	    console.log(person[0]);
    	$http({
    		'method' : 'POST',
    		'url' : 'http://172.17.148.27:3000/patient',
    		'header' : 'application/json',
    		'data' : {
    			pid : person[0]
    		}
    	}).then(function successCallback(response) {
    		console.log(response);
    		//person = response.data;
    		$scope.people[i][7].gender = response.data.gender;
    		$scope.people[i][7].address = response.data.address;
    		$scope.people[i][7].ename = response.data.ename;
    		$scope.people[i][7].enumber = response.data.enumber;
    		$scope.people[i][7].phone = response.data.number;
        $scope.people[i][7].gcmToken = response.data.gcmToken;

    	}, function errorCallback(response){
    		console.log(response);
    	});
    }
    $scope.submit = function(obj, index, person){
      var time = [obj.hour, obj.minute, obj.ampm].join(":")
      // console.log($scope.people[index]);
      // console.log(obj.frequency);
      // console.log(time);
      // console.log(obj.date);
      // console.log(obj.message);
      obj.hour = parseInt(obj.hour) + 5;
      time = obj.hour + ':'+ obj.minute;
      // console.log(time);
      $http({
        'method' : 'POST',
        'url' : 'http://172.17.148.27:3000/notification',
        'header' : 'application/json',
        'data': {
          'gcmToken' : $scope.people[index][7].gcmToken, //people[index].gcmtoken
          'freq' : obj.frequency, //frequency
          't' : time, //time
          'd': obj.date, //date
          'message' : obj.message, //message
          'firstname' : $scope.people[index][1],
          'lastname' : $scope.people[index][2]
        }
      }).then(function successCallback(response) {
        console.log(response);
        if (response.data == "OK"){
          console.log(response.data);
        }
      }, function errorCallback(response) {
        console.log(response);
      });
      // console.log("SUBMIT " + index)
    }
  });
