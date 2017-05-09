(function () {
    'use strict';

    angular
        .module('zerohqt.login')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$rootScope', 'loginService', '$location', '$ionicLoading'];

    /* @ngInject */
    function LoginController($scope, $rootScope, loginService, $location, $ionicLoading) {

        $scope.login = function () {
            $ionicLoading.show({
                template: 'Logging in...'
            });
            window.plugins.googleplus.login(
                {
                    'webClientId': '931186243837-df1tccvkpa5jqpeeq8avpdugdtp7f2mg.apps.googleusercontent.com',
                    'offline': true
                },
                function (userData) {
                    console.log("Sono dentro userData " + userData);
                    // For the purpose of this example I will store user data on local storage
                    $rootScope.user = userData;
                    if (userData)
                        loginService.logUserAccess().then(function (req) {
                            console.log(req);
                        }, function (error) {
                            console.log(error);
                        });
                    $ionicLoading.hide();
                    //$state.go('app.home');
                    $location.path('/home');
                },
                function (msg) {
                    console.log("Sono qui dentro" + msg);
                    $ionicLoading.hide();
                }
            );
        };


    }
})();
