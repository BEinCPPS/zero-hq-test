(function () {
    'use strict';

    angular
        .module('zerohqt.login')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$rootScope', 'loginService', '$state', '$ionicLoading', '$ionicHistory', 'ENV'];

    /* @ngInject */
    function LoginController($scope, $rootScope, loginService, $state, $ionicLoading, $ionicHistory, ENV) {

        $scope.login = function () {
            //This check is needed to test in browser env
            $ionicHistory.nextViewOptions({
                disableAnimate: true,
                disableBack: true
            });
            if (typeof window.plugins === 'undefined' && !ionic.Platform.isAndroid()) {
                $rootScope.user = {
                    uid: '0000000',
                    fullName: 'test',
                    email: 'test@mail.com'
                };
                loginService.logUserAccess($rootScope.user).then(function (req) {
                    console.log(req);
                }, function (error) {
                    console.log(error);
                });
                return $state.go('app.home');
            }

            $ionicLoading.show({
                template: 'Logging in...'
            });
            window.plugins.googleplus.login(
                {
                    'webClientId': ENV.googleAppId,
                    'offline': true //TODO
                },
                function (userData) {
                    console.log("Login Google oAuth executed for user:  " + userData);
                    // For the purpose of this example I will store user data on local storage
                    $rootScope.user = userData;
                    if (userData)
                        loginService.logUserAccess(userData).then(function (req) {
                            console.log(req);
                        }, function (error) {
                            console.log(error);
                        });
                    $ionicLoading.hide();
                    $state.go('app.home');
                },
                function (msg) {
                    console.log("Error login with Google oAuth: " + msg);
                    $ionicLoading.hide();
                }
            );
        };


    }
})();
