(function () {
    'use strict';

    angular
        .module('zerohqt.common')
        .factory('externalAppsService', externalAppsService);

    externalAppsService.$inject = ['$window', 'ENV'];

    /* @ngInject */
    function externalAppsService($window, ENV) {
        var service = {
            openMapsApp: openMapsApp,
            openExternalUrl: openExternalUrl,
            getBackEndUrl: getBackEndUrl,
            getWebSocketUrl: getWebSocketUrl,
            openVnc: openVncApp
        };
        return service;

        // ******************************************************

        function openMapsApp(coords) {
            var q;
            if (ionic.Platform.isAndroid()) {
                q = 'geo:' + coords;
            } else {
                q = 'maps://maps.apple.com/?q=' + coords;
            }
            $window.location.href = q;
        }

        function openExternalUrl(url) {
            $window.open(url, '_system', 'location=yes');
            return false;
        }

        function getBackEndUrl() {
            if (ionic.Platform.isAndroid()) {
                console.log(ENV.apiEndpointHostMobile);
                return ENV.apiEndPointDefaultProtocol + '://' + ENV.apiEndpointHostMobile + ':' + ENV.apiEndPointPortMobile + '/';
            } else {
                console.log(ENV.apiEndpointHost);
                return ENV.apiEndPointDefaultProtocol + '://' + ENV.apiEndpointHost + ':' + ENV.apiEndPointPort + '/';
            }
        }

        function getWebSocketUrl() {
            if (ionic.Platform.isAndroid()) {
                return 'ws://' + ENV.apiEndpointHostMobile + ':' + ENV.apiEndPointPortMobile + '/websocket';
            } else {
                return 'ws://' + ENV.apiEndpointHost + ':' + ENV.apiEndPointPort + '/websocket';
            }
        }

        function openVncApp(ipAddress) {
            if (ipAddress) {
                console.log('Opening vnc ipAddress: ' + ipAddress);
                if (ionic.Platform.isAndroid()) {
                    if (startApp) {
                        console.log('Im opening startApp');
                        startApp.set({
                            "action": "ACTION_VIEW",
                            "uri": "kvm://" + ipAddress
                        }).start();
                    }
                } else {
                    $window.location.href = 'vnc://' + ipAddress;
                }
            }
        }
    }
})();
