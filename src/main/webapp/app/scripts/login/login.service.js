(function () {
    'use strict';

    angular
        .module('zerohqt.login')
        .factory('loginService', loginService);

    loginService.$inject = ['$http', 'externalAppsService'];

    /* @ngInject */
    function loginService($http, externalAppsService) {

        var service = {
            logUserAccess: logUserAccess
        };
        return service;

        function logUserAccess(userData) {
            return $http.post(externalAppsService.getBackEndUrl() + 'userAccess', userData);
        }


    }
})();
