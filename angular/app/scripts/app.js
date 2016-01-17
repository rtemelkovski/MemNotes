'use strict';

/**
 * @ngdoc overview
 * @name deltahacksApp
 * @description
 * # deltahacksApp
 *
 * Main module of the application.
 */
angular
  .module('deltahacksApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/patients', {
        templateUrl: 'views/patients.html',
        controller: 'PatientsCtrl',
        controllerAs: 'patients'
      })
      .when('/newpatients', {
        templateUrl: 'views/newpatients.html',
        controller: 'NewPatientsCtrl',
        controllerAs: 'newpatients'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
