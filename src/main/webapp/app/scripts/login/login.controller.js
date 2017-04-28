(function () {
    'use strict';

    angular
        .module('zerohqt.login')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$http', '$location', 'externalAppsService', 'ENV'];

    /* @ngInject */
    function LoginController($scope, $http, $location, externalAppsService, ENV) {

        $http.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

        var clientId = ENV.googleAppId;
        var clientSecret = ENV.googleAppSecret;
        var redirectUrl = 'http://localhost/callback';

        $scope.login = function () {
            var ref = window.open('https://accounts.google.com/o/oauth2/auth?client_id='
                + clientId + '&redirect_uri=' + redirectUrl + '&scope=https://www.googleapis.com/auth/urlshortener&approval_prompt=force&response_type=code&access_type=offline', '_blank', 'location=no');
            ref.addEventListener('loadstart', function (event) {
                if ((event.url).startsWith(redirectUrl)) {
                    $scope.requestToken = (event.url).split("code=")[1];
                    $http({
                        method: "post",
                        url: "https://accounts.google.com/o/oauth2/token",
                        data: "client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri=" + redirectUrl + "&grant_type=authorization_code" + "&code=" + requestToken
                    })
                        .success(function (data) {
                            $scope.accessToken = data.access_token;
                            $location.path("/app/home");
                        })
                        .error(function (data, status) {
                            console.log("ERROR: " + data);
                        });
                    ref.close();
                }
            });
        }
    }
})();
