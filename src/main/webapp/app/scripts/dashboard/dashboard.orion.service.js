/**
 * Created by ascatox on 28/04/17.
 */
(function () {
    'use strict';
    angular
        .module('zerohqt.dashboard')
        .factory('dashboardOrionService', dashboardOrionService);

    dashboardOrionService.$inject = ['$http', 'externalAppsService', 'ENV'];

    /* @ngInject */
    function dashboardOrionService($http, externalAppsService, ENV) {
        var service = {
            getFeedbackScale: getFeedbackScale
        };
        return service;

        function getFeedbackScale() {
            return $http.get(externalAppsService.getBackEndUrl() + 'feedbackScale');
        }
    }
})();

