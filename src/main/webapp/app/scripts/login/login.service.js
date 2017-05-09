(function () {
    'use strict';

    angular
        .module('zerohqt.login')
        .factory('loginService', loginService);

    loginService.$inject = ['$rootScope'];

    /* @ngInject */
    function loginService($rootScope) {
        var service = {
            logUserAccess: logUserAccess
        };
        return service;

        function logUserAccess(userData) {
             $http.post(externalAppsService.getBackEndUrl() + 'logUserAccess', userData);
        }


    }
})();
