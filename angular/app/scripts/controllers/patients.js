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
  	$scope.people = {};
  	$scope.isCollapsed = false;
  	$http({
    			'method': 'GET',
    			'url' : 'http://172.17.148.27:3000/getPatients',
    			'header' : 'application/json'
    		}).then(function successCallback(response) {
    			console.log(response);
    			$scope.people = response.data;

    		}, function errorCallback(response) {
    			console.log(response);
    		});
    $scope.info = function(person, $index){
    	$scope.activePosition = $scope.activePosition == $index ? -1 : $index;
    	$http({
    		'method' : 'POST',
    		'url' : 'http://172.17.148.27:3000/patient',
    		'header' : 'application/json',
    		'data' : {
    			pid : person.id
    		}
    	}).then(function successCallback(response) {
    		console.log(response);
    		person = response.data;

    	}, function errorCallback(response){
    		console.log(response);
    	});
    }
  });
