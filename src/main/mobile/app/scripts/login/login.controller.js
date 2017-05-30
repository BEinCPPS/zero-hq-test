(function () {
    'use strict';

    angular
        .module('zerohqt.login')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$rootScope', 'loginService', '$state', '$ionicLoading', '$ionicHistory', 'ENV', 'websocketService', 'insomniaService'];

    /* @ngInject */
    function createUserAccess(userData) {
        if (userData) {
            return {
                uid: userData.userId,
                fullName: userData.displayName,
                email: userData.email
            };
        }
    }

    function LoginController($scope, $rootScope, loginService, $state, $ionicLoading, $ionicHistory, ENV, websocketService, insomniaService) {
        $scope.login = function () {
            //This check is needed to test in browser env
            $ionicHistory.nextViewOptions({
                disableAnimate: true,
                disableBack: true
            });
            if ((typeof window.plugins === 'undefined' && !ionic.Platform.isAndroid()) || ENV.mockSignin) {
                $rootScope.user = {
                    uid: '0000000',
                    fullName: 'test',
                    email: 'test@mail.com'
                };
                loginService.logUserAccess($rootScope.user).then(function (req) {
                    console.log(req);
                    insomniaService.keepAwake();
                    $scope.connect();
                    return $state.go('app.home');
                }, function (error) {
                    console.log(JSON.stringify(error));
                    $ionicLoading.show({template: 'Error accessing application!', noBackdrop: true, duration: 2000});
                });

            } else {
                $ionicLoading.show({
                    template: 'Logging in...'
                });
                window.plugins.googleplus.login(
                    {
                        'webClientId': ENV.googleAppId,
                        'offline': false
                    },
                    function (userData) {
                        console.log("Login Google oAuth executed for user:  " + userData);
                        // For the purpose of this example I will store user data on local storage
                        $rootScope.user = userData;
                        /**
                         * email,
                         * idToken,
                         * displayName,
                         * familyName,
                         * givenName,
                         * imageUrl,
                         * accessToken
                         */
                        if (userData)
                            loginService.logUserAccess(createUserAccess(userData)).then(function (req) {
                                console.log(req);
                                localStorage.setItem('token', userData.accessToken);
                                insomniaService.keepAwake();
                                $scope.connect();
                            }, function (error) {
                                console.log(error);
                                $ionicLoading.show({
                                    template: 'Error accessing application!',
                                    noBackdrop: true,
                                    duration: 2000
                                });
                            });
                        $ionicLoading.hide();
                        $state.go('app.home');
                    },
                    function (msg) {
                        console.log("Error login with Google oAuth: " + msg);
                        $ionicLoading.hide();
                    }
                );
            }
        }


        $scope.connect = function () {
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
