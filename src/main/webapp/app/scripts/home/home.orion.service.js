/**
 * Created by ascatox on 28/04/17.
 */
(function () {
    'use strict';
    angular
        .module('zerohqt.home')
        .factory('orionService', orionService);

    orionService.$inject = ['$http', 'externalAppsService', 'ENV'];

    /* @ngInject */
    function orionService($http, externalAppsService, ENV) {
        var service = {
            subscribe: subscribe
        };
        return service;

        function subscribe() {
            return $http.get(externalAppsService.getBackEndUrl() + 'subscribe');
        }

    }
})();

