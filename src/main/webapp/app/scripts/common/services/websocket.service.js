(function () {
    'use strict';

    angular
        .module('zerohqt.common')
        .factory('websocketService', websocketService);

    websocketService.$inject = ['$rootScope'];
    /* @ngInject */
    var stompClient = null;
    function websocketService($rootScope) {
        var sockjs = new SockJS('http://localhost:8080/websocket'); //TODO
        var service = {
            connect: connect
        };
        return service;

        function connect() {
            if (stompClient != null) return;
            stompClient = Stomp.over(sockjs);
            var onMessage = function (message) {
                console.log("Message from WebSocket: " + message);
                var messageObj = JSON.parse(message.body);
                $rootScope.$broadcast('wSockMessage', messageObj);
            }
            var connectCallback = function () {
                console.log('Connected!!!');
                stompClient.subscribe('/topic', onMessage);
            }
            var errorCallback = function (error) {
                console.log(error);
            }
            stompClient.connect({}, connectCallback, errorCallback);
            return onMessage;
        }
    }
})();
