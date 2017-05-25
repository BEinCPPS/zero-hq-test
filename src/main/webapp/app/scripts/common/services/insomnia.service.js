(function () {
    'use strict';
    angular
        .module('zerohqt.common')
        .factory('insomniaService', insomniaService);

    insomniaService.$inject = [];
    /* @ngInject */
    function insomniaService() {
        var service = {
            keepAwake: keepAwake,
            allowSleepAgain: allowSleepAgain
        };
        return service;

        function keepAwake() {
            if (window.plugins && window.plugins.insomnia) {
                window.plugins.insomnia.keepAwake();
                console.log('Insomnia plugin activate caffeine ++ !!!');
            } else {
                console.log('Insomnia plugin not found!!!');
            }
        }

        function allowSleepAgain() {
            if (window.plugins && window.plugins.insomnia) {
                window.plugins.insomnia.allowSleepAgain();
                console.log('Insomnia plugin deactivate I can sleep again!!!');
            } else {
                console.log('Insomnia plugin not found!!!');
            }
        }
    }
})();
