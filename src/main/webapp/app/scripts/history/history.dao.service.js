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
            nextHistory: nextHistory
        };
        return service;

        function fullHistory() {
            return $http.get(ENV.apiEndpoint + 'history');
        }

        function nextHistory(startPoint) {
            return $http.get(ENV.apiEndpoint + 'nexthistory?startPoint=' + startPoint + '&delta=' + Number(ENV.historyDelta));
        }


    }
})();

