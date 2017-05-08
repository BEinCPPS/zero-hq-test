(function () {
    'use strict';

    angular
        .module('zerohqt.login')
        .factory('loginService', loginService);

    loginService.$inject = ['$rootScope'];

    /* @ngInject */
    function loginService($rootScope) {
        var service = {
            login: login,
            logout: logout
        };
        return service;

        function login() {
            console.log('Sono dentro il service');
            //return $cordovaGooglePlus({}).login();
        }

        function logout() {
           // return $cordovaGooglePlus.logout();
        }
    }
})();
