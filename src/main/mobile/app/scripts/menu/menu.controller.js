(function () {
    'use strict';

    angular
        .module('zerohqt.menu')
        .controller('MenuController', MenuController);

    MenuController.$inject = ['$scope', 'websocketService', '$state', '$ionicLoading', '$rootScope', '$ionicHistory', 'ENV', 'insomniaService', '$ionicScrollDelegate'];

    /* @ngInject */
    function MenuController($scope, websocketService, $state, $ionicLoading, $rootScope, $ionicHistory, ENV, insomniaService, $ionicScrollDelegate) {

        $scope.scrollTop = function () {
            $ionicScrollDelegate.scrollTop();
        };

        $scope.logout = function () {
            $ionicHistory.nextViewOptions({
                disableAnimate: true,
                disableBack: true
            });
            if ((typeof window.plugins === 'undefined' && !ionic.Platform.isAndroid()) || ENV.mockSignin) {
                $rootScope.user = {};
                websocketService.disconnect();
                insomniaService.allowSleepAgain();
                return $state.go('app.login');
            } else {
                $ionicLoading.show({
                    template: 'Logging out...'
                });
                window.plugins.googleplus.logout(
                    function (msg) {
                        console.log(msg);
                        $ionicLoading.hide();
                        websocketService.disconnect();
                        insomniaService.allowSleepAgain();
                        $state.go('app.login');
                    },
                    function (fail) {
                        console.log(fail);
                        $state.go('app.login');
                    }
                );
            }
            insomniaService.allowSleepAgain();
        }


        $scope.connect = function () {
            if ($rootScope.isWsConnected) return;
            websocketService.connect().then(function (isConnected) {
                //$scope.isWsConnected = isConnected;
                console.log("Connection to web socket: " + isConnected);
                $scope.$apply(); //Apply changes to the page
            }, function (error) {
                console.log('Error encountered ' + error);
            });
        }


    }
})();
