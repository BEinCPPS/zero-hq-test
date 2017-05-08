(function () {
    'use strict';

    angular
        .module('zerohqt.login')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$rootScope', 'loginService', '$state', '$ionicLoading'];

    /* @ngInject */
    function LoginController($scope, $rootScope, loginService, $state, $ionicLoading) {

        $scope.login = function () {
            $ionicLoading.show({
                template: 'Logging in...'
            });
            window.plugins.googleplus.login(
                {
                    'webClientId': '931186243837-oghoeuqmelr9ehrads24vuifje5qo8b3.apps.googleusercontent.com',
                    'offline': true
                },
                function (userData) {
                    console.log("Sono dentro userData " + userData);
                    // For the purpose of this example I will store user data on local storage
                    $rootScope.user = userData;
                    $ionicLoading.hide();
                    $state.go('app.home');
                },
                function (msg) {
                    console.log("Sono qui dentro" + msg);
                    $ionicLoading.hide();
                }
            );
        };


    }
})();
