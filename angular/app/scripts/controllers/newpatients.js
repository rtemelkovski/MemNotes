'use strict';

/**
 * @ngdoc function
 * @name deltahacksApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the deltahacksApp
 */
angular.module('deltahacksApp')
  .controller('NewPatientsCtrl', function ($scope, $http) {
  	$scope.people = [];
  	$http({
  		'method' : 'GET',
  		'url' : 'http://172.17.148.27:3000/patientsVerify',
  		'header' : 'application/json'
  	}).then(function succesfulCallback(response){
  		console.log(response);
  		$scope.people = response.data;
  	}, function errorCallback(response){
  		console.log(response);
  	});
  	$scope.verify = function(room, allergies, medication, notes, $index){
  		console.log(room);
  		console.log(allergies);
  		console.log(medication);
  		console.log(notes);
  		console.log($index);
  		console.log($scope.people[$index]);
  		$http({
  			'method' : 'POST',
  			'url' : 'http://172.17.148.27:3000/verifyPatient',
    		'header' : 'application/json',
    		'data' : {
    			'roomnumber' : room,
    			'allergy' : allergies,
    			'medication' : medication,
    			'notes' : notes,
    			'pid' : $scope.people[$index]._id
    		}
  		}).then(function succesfulCallback(response) {
  			console.log(response);
  			$scope.room = "";
  			$scope.allergies = "";
  			$scope.medication = "";
  			$scope.notes = "";
  			  	$http({
				  		'method' : 'GET',
				  		'url' : 'http://172.17.148.27:3000/patientsVerify',
				  		'header' : 'application/json'
				  	}).then(function succesfulCallback(response){
				  		console.log(response);
				  		$scope.people = response.data;
				  	}, function errorCallback(response){
				  		console.log(response);
				  	});
 		}, function errorCallback(response) {
 			console.log(response);
 		});
  	}
  });
