(function () {
    'use strict';

    angular
        .module('zerohqt.common')
        .factory('websocketService', websocketService);

    websocketService.$inject = ['$rootScope'];
    /* @ngInject */
    var stompClient = null;
    function websocketService($rootScope) {
        var sockjs = new SockJS('http://localhost:8080/websocket'); //TODO in Env
        var service = {
            connect: connect
        };
        return service;

        function connect() {
            if (stompClient != null) return;
            stompClient = Stomp.over(sockjs);
            var onMessageBay = function (message) {
                console.log("Message from WebSocket Bay: " + message);
                var messageObj = JSON.parse(message.body);
                $rootScope.$broadcast('wsMessageBay', messageObj);
            }
            var onMessageAck = function (message) {
                console.log("Message from WebSocket Ack: " + message);
                var messageObj = JSON.parse(message.body);
                $rootScope.$broadcast('wsMessageAck', messageObj);
            }
            var connectCallback = function () {
                console.log('Connected!!!');
                stompClient.subscribe('/informationBay', onMessageBay);
                stompClient.subscribe('/acknowledge', onMessageAck);
            }
            var errorCallback = function (error) {
                console.log(error);
            }
            stompClient.connect({}, connectCallback, errorCallback);
            return onMessage;
        }
    }
})();
