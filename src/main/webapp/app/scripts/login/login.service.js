(function () {
    'use strict';

    angular
        .module('zerohqt.login')
        .factory('loginService', loginService);

    loginService.$inject = ['$rootScope', '$ionicPlatform'];

    /* @ngInject */
    function loginService($rootScope, $ionicPlatform) {
        var service = {};
        return service;
    }
})();
