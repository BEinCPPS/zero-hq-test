(function () {
    'use strict';

    angular
        .module('zerohqt.login', [
            'ionic',
            'ngCordova',
            'ionic.native'
        ])
        .config(function ($stateProvider) {
            $stateProvider
                .state('app.login', {
                    url: '/login',
                    views: {
                        'menuContent': {
                            templateUrl: 'scripts/login/login.html',
                            controller: 'LoginController'
                        }
                    }
                })
        });
})();
