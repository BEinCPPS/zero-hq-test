(function () {
    'use strict';

    angular
        .module('zerohqt.common')
        .factory('externalAppsService', externalAppsService);

    externalAppsService.$inject = ['$window', 'ENV', '$ionicLoading'];

    /* @ngInject */
    function externalAppsService($window, ENV, $ionicLoading) {
        var sApp = null;
        if (typeof startApp !== 'undefined' && startApp !== null) {
            sApp = startApp.set({
                /* params */
                "action": "ACTION_RUN",
                "category": "CATEGORY_ALTERNATIVE",
                "package": "com.realvnc.viewer.android",
                "intentstart": "startActivity"
            }, {
                /* extras */
                //"ipAddress": ipAddress
            });
        }
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
                    if (cordova.plugins && cordova.plugins.clipboard) {
                        console.log('Im copying to clipboard');
                        cordova.plugins.clipboard.copy(ipAddress);
                    }
                    console.log('Im opening startApp');
                    if (sApp !== null) {
                        sApp.check(function (values) { /* success */
                            console.log('Application vnc is present with: ' + values)
                            sApp.start(function () { /* success */
                                console.log('Application vnc viewer started with: ' + values)
                            }, function (error) { /* fail */
                                console.log('Application not started with error: ' + error);
                                $ionicLoading.show({
                                    template: 'Application vnc impossible to start!',
                                    noBackdrop: true,
                                    duration: 1000
                                });
                            });
                        }, function (error) { /* fail */
                            console.log('Application presence error: ' + error);
                            $ionicLoading.show({
                                template: 'Application vnc not available currently!',
                                noBackdrop: true,
                                duration: 1000
                            });
                        });
                    }
                } else {
                    $window.location.href = 'vnc://' + ipAddress;
                }
            }
        }
    }
})();
