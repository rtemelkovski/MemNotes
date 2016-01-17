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

    	}, function errorCallback(response){
    		console.log(response);
    	});
    }
  });
