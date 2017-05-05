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
        var isWsConnected = false;
        $rootScope.informationBays = [];
        $rootScope.feedbacks = [];
        $rootScope.acknowledges = [];
        var service = {
            connect: connect,
            isWsConnected: isWsConnected,
        };
        return service;

        function connect() {
            if (stompClient != null && isWsConnected) return;
            try {
                stompClient = Stomp.client(externalAppsService.getWebSocketUrl());
            } catch (error) {
                console.log('Error in creating Stomp over SockJs', error);
            }
            var onMessageInformationBay = function (message) {
                console.log("Message Information Bay from WebSocket Bay: " + message);
                var messageObj = JSON.parse(message.body);
                $rootScope.$broadcast('wsMessage', messageObj);
                $rootScope.informationBays.push(messageObj);
            }
            var onMessageAck = function (message) {
                console.log("Message Acknowledge from WebSocket Bay: " + message);
                var messageObj = JSON.parse(message.body);
                $rootScope.$broadcast('wsMessageAck', messageObj);
                $rootScope.acknowledges.push(messageObj);
            }
            var onMessageFeedback = function (message) {
                console.log("Message Feedback from WebSocket Bay: " + message);
                var messageObj = JSON.parse(message.body);
                $rootScope.$broadcast('wsMessageFeedback', messageObj);
                $rootScope.feedbacks.push(messageObj);
            }


            return new Promise(function (resolve, reject) {
                var connectCallback = function () {
                    console.log('Connected!!!');
                    stompClient.subscribe('/topic/informationBay', onMessageInformationBay);
                    stompClient.subscribe('/topic/acknowledge', onMessageAck);
                    stompClient.subscribe('/topic/feedback', onMessageFeedback);
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
