(function () {
    'use strict';

    angular
        .module('zerohqt.common')
        .factory('websocketService', websocketService);

    websocketService.$inject = ['$rootScope', '$ionicPlatform', 'externalAppsService'];
    /* @ngInject */
    var stompClient = null;

    function websocketService($rootScope, $ionicPlatform, externalAppsService) {
        console.log('Starting Sockjs.......' + externalAppsService.getBackEndUrl());
        /* var wsUrl = externalAppsService.getBackEndUrl() + 'websocket';
         var sockjs = new SockJS(wsUrl); //TODO in Env
         console.log('SockJS built');
         sockjs.onopen = function() {
         console.log('SockJS is opened');
         };
         sockjs.onmessage = function(e) {
         console.log('message!!!!!!!!!!', e.data);
         }
         */
        var isWsConnected = false;
        var service = {
            connect: connect,
            isWsConnected: isWsConnected
        };
        return service;

        function connect() {
            if (stompClient != null && isWsConnected) return;
            try {
                stompClient = Stomp.client(externalAppsService.getWebSocketUrl());
            } catch (error) {
                console.log('Error in creating Stomp over SockJs', error);
            }
            var onMessage = function (message) {
               // console.log("Message from WebSocket Bay: " + message);
                var messageObj = JSON.parse(message.body);
                $rootScope.$broadcast('wsMessage', messageObj);
            }
            return new Promise(function (resolve, reject) {
                var connectCallback = function () {
                    console.log('Connected!!!');
                    stompClient.subscribe('/topic', onMessage);
                    isWsConnected = true;
                    return resolve(true); //isConnected
                }
                var errorCallback = function (error) {
                    console.log("Error in connecting STOMP over WS " + JSON.stringify(error));
                    isWsConnected = false;
                    $rootScope.$broadcast('wsError', error);
                    return reject(error);
                }
                stompClient.connect({}, connectCallback, errorCallback);
            });
        }
    }
})();
