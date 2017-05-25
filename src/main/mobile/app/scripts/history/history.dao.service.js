/**
 * Created by ascatox on 09/03/17.
 */
(function () {
    'use strict';
    angular
        .module('zerohqt.common')
        .factory('daoService', daoService);

    daoService.$inject = ['$http', 'externalAppsService', 'ENV'];

    /* @ngInject */
    function daoService($http, externalAppsService, ENV) {
        var service = {
            fullHistory: fullHistory,
            fullStationsBays: fullStationsBays,
            stationBayHistory: stationBayHistory,
            nextHistory: nextHistory,
            fullHistoryByAck:fullHistoryByAck
        };
        return service;

        function fullHistory() {
            return $http.get(externalAppsService.getBackEndUrl() + 'history');
        }

        function fullHistoryByAck(ackType) {
            return $http.get(externalAppsService.getBackEndUrl() + 'historyByAck?ackType=' + ackType);
        }

        function fullStationsBays() {
            return $http.get(externalAppsService.getBackEndUrl() + 'stationsBays');
        }

        function stationBayHistory(stationBay) {
            return $http.get(externalAppsService.getBackEndUrl() + 'stationBayHistory?stationBay=' + stationBay);
        }

        function nextHistory(startPoint) {
            return $http.get(externalAppsService.getBackEndUrl() + 'nexthistory?startPoint=' + startPoint + '&delta=' +
                Number(ENV.historyDelta));
        }
    }
})();

