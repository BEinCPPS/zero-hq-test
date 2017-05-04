/**
 * Created by ascatox on 28/04/17.
 */
(function () {
    'use strict';
    angular
        .module('zerohqt.home')
        .factory('homeOrionService', homeOrionService);

    homeOrionService.$inject = ['$http', 'externalAppsService', 'ENV'];

    /* @ngInject */
    function homeOrionService($http, externalAppsService, ENV) {
        var service = {
            subscribe: subscribe
        };
        return service;

        function subscribe() {
            return $http.get(externalAppsService.getBackEndUrl() + 'subscribe');
        }

    }
})();

