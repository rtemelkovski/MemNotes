'use strict';

/**
 * @ngdoc function
 * @name deltahacksApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the deltahacksApp
 */
angular.module('deltahacksApp')
  .controller('MainCtrl', function ($scope, $http) {
    $scope.login = false;
    $scope.submit = function(username, password){
    	if (!username || !password){
    		console.log("Empty username or password");
            $scope.login = true;
    	}
    	else{
    		console.log("Username: " + username);
    		console.log("Password: " + password);
    		$http({
    			'method': 'POST',
    			'url' : 'http://172.17.148.27:3000/login',
    			'header' : 'application/json',
    			'data' : {
    				usr : username,
    				pas : password
    			}
    		}).then(function successCallback(response) {
    			console.log(response);
    			if (response.data == "TRUE"){
                    $scope.login = false;
    				//go to the next thing
    				console.log("Login successful.");
    				console.log("Redirecting");
    				window.location.replace("#/patients")
    			}
                else{
                    $scope.login = true;
                }
    		}, function errorCallback(response) {
    			console.log(response);
                $scope.login = true;
    		});
    	}
    };
  });
