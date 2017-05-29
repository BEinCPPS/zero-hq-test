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
                setBackground();
            } else {
                console.log('Insomnia plugin not found!!!');
            }
        }

        function allowSleepAgain() {
            if (window.plugins && window.plugins.insomnia) {
                window.plugins.insomnia.allowSleepAgain();
                console.log('Insomnia plugin deactivate I can sleep again!!!');
                disableBackground();
            } else {
                console.log('Insomnia plugin not found!!!');
            }
        }

        function setBackground() {
            if (cordova.plugins && cordova.plugins.backgroundMode) {
                if (isAppInBackground()) return;
                console.log('Background mode plugin activated!');
                cordova.plugins.backgroundMode.enable();
            }
        }

        function disableBackground() {
            if (cordova.plugins && cordova.plugins.backgroundMode) {
                console.log('Background mode plugin deactivated!');
                cordova.plugins.backgroundMode.disable();
            }
        }

        function isAppInBackground() {
            if (cordova.plugins && cordova.plugins.backgroundMode) {
                return cordova.plugins.backgroundMode.isActive();
            }
            return false;
        }
    }
})();
