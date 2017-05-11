(function () {
    'use strict';

    angular
        .module('zerohqt.menu')
        .controller('MenuController', MenuController);

    MenuController.$inject = ['$scope', 'websocketService', 'loginService', '$state', '$ionicLoading', '$rootScope', '$ionicHistory'];

    /* @ngInject */
    function MenuController($scope, websocketService, loginService, $state, $ionicLoading, $rootScope, $ionicHistory) {

        $scope.logout = function () {
            $ionicHistory.nextViewOptions({
                disableAnimate: true,
                disableBack: true
            });
            if (typeof window.plugins === 'undefined' && !ionic.Platform.isAndroid()) {
                $rootScope.user = {};
                websocketService.disconnect();
                return $state.go('app.login');
            }
            $ionicLoading.show({
                template: 'Logging out...'
            });
            window.plugins.googleplus.logout(
                function (msg) {
                    console.log(msg);
                    $ionicLoading.hide();
                    websocketService.disconnect();
                    $state.go('app.login');
                },
                function (fail) {
                    console.log(fail);
                }
            );
        }
    }
})();
