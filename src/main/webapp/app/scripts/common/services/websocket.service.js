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
            if (stompClient != null && isConnected) return;
            stompClient = Stomp.over(sockjs);

            var onMessage = function (message) {
                console.log("Message from WebSocket Bay: " + message);
                var messageObj = JSON.parse(message.body);
                $rootScope.$broadcast('wsMessage', messageObj);
            }
            return new Promise(function (resolve, reject) {
                var connectCallback = function () {
                    console.log('Connected!!!');
                    stompClient.subscribe('/topic', onMessage);
                    return resolve(true); //isConnected
                }
                var errorCallback = function (error) {
                    console.log(error);
                    return reject(error);
                }
                stompClient.connect({}, connectCallback, errorCallback);
            });
        }
    }
})();
