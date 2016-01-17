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
  		people = response.data;
  	}, function errorCallback(response){
  		console.log(response);
  	});
  });
