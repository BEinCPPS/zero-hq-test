(function () {
    'use strict';

    angular
        .module('zerohqt.menu')
        .controller('MenuController', MenuController);

    MenuController.$inject = ['$scope', 'websocketService'];

    /* @ngInject */
    function MenuController($scope, websocketService) {
        $scope.isWsConnected = false;

        $scope.$on('wsError', function (error) {
            $scope.isWsConnected = false;
            console.log('Sono nel menu con error ws');
            $scope.$apply(); //Apply changes to the page
        });


        $scope.connect = function () {
            if ($scope.isWsConnected) return;
            websocketService.connect().then(function (isConnected) {
                $scope.isWsConnected = isConnected;
                console.log("Connection to web socket: " + isConnected);
                $scope.$apply(); //Apply changes to the page
            }, function (error) {
                console.log('Error encountered ' + error);
            });
        }

        $scope.$on('$ionicView.loaded', function (viewInfo, state) {
            console.log('Trying to conenct!!!!');
            $scope.connect();
        });

    }
})();
