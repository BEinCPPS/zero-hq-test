(function () {
    'use strict';

    angular
        .module('zerohqt.menu')
        .controller('MenuController', MenuController);

    MenuController.$inject = ['$scope', 'websocketService', 'loginService', '$location', '$ionicLoading'];

    /* @ngInject */
    function MenuController($scope, websocketService, loginService, $location, $ionicLoading) {
        $scope.isWsConnected = false;

        $scope.$on('wsError', function (error) {
            $scope.isWsConnected = false;
            console.log('Sono nel menu con error ws');
            $scope.$apply(); //Apply changes to the page
        });

        $scope.logout = function () {
            $ionicLoading.show({
                template: 'Logging out...'
            });
            window.plugins.googleplus.logout(
                function (msg) {
                    console.log(msg);
                    $ionicLoading.hide();
                    $location.path('/login');
                },
                function (fail) {
                    console.log(fail);
                }
            );
        }

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
            console.log('Trying to connect!!!!');
            $scope.connect();
        });

    }
})();
