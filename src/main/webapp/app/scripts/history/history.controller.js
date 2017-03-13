(function () {
    'use strict';

    angular
        .module('zerohqt.history')
        .controller('HistoryController', HistoryController);

    HistoryController.$inject = ['$scope', 'daoService', '$ionicLoading'];

    /* @ngInject */
    function HistoryController($scope, daoService, $ionicLoading) {
        var notifications = [];
        var vm = angular.extend(this, {});

        $scope.loadMore = function () {
            //$scope.show($ionicLoading);
            daoService.fullHistory().then(function (req) {
                $scope.notifications = req.data;
                //$scope.$broadcast('scroll.infiniteScrollComplete');
                //$scope.hide($ionicLoading);
            }, function (err) {
                console.log(err);
                //$scope.hide($ionicLoading);
            });
        }
        $scope.show = function () {
            $ionicLoading.show({
                template: '<p>Loading...</p><ion-spinner></ion-spinner>'
            });
        };

        $scope.hide = function () {
            $ionicLoading.hide();
        };
        $scope.$on('wsMessage', function (event, informationBay) {
            notifications.push(informationBay);
            $scope.$apply(); //Apply changes to the page
        });

        $scope.$on('$stateChangeSuccess', function () {
            $scope.loadMore();
        });
    }
})();
