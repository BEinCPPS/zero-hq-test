(function () {
    'use strict';

    angular
        .module('zerohqt.login', [
            'ionic',
            'ngCordova'
        ])
        .config(function ($stateProvider, $urlRouterProvider) {
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
           // $urlRouterProvider.otherwise('/login');
        });
})();
