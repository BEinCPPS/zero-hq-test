(function() {
    'use strict';

    angular
        .module('zerohqt.dashboard', [
            'ionic',
            'ngCordova'
        ])
        .config(function($stateProvider) {
            $stateProvider
                .state('app.dashboard', {
                    url: '/dashboard',
                    views: {
                        'menuContent': {
                            templateUrl: 'scripts/dashboard/dashboard.html',
                            controller: 'DashboardController'
                        }
                    }
                })
        });
})();
