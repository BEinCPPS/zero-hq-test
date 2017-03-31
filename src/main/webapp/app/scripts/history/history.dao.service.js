/**
 * Created by ascatox on 09/03/17.
 */
(function () {
    'use strict';
    angular
        .module('zerohqt.common')
        .factory('daoService', daoService);

    daoService.$inject = ['$http', 'ENV'];

    /* @ngInject */
    function daoService($http, ENV) {
        var service = {
            fullHistory: fullHistory,
            fullStationsBays: fullStationsBays,
            stationBayHistory: stationBayHistory,
            nextHistory: nextHistory
        };
        return service;

        function fullHistory() {
            return $http.get(ENV.apiEndpoint + 'history');
        }
        function fullStationsBays() {
                    return $http.get(ENV.apiEndpoint + 'stationsBays');
        }
        function stationBayHistory(stationBay) {
            return $http.get(ENV.apiEndpoint + 'stationBayHistory?stationBay=' + stationBay);
        }
        function nextHistory(startPoint) {
            return $http.get(ENV.apiEndpoint + 'nexthistory?startPoint=' + startPoint + '&delta=' + Number(ENV.historyDelta));
        }


    }
})();

