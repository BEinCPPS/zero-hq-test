(function () {
    'use strict';

    angular
        .module('zerohqt.inbox')
        .controller('InboxController', InboxController);

    InboxController.$inject = ['$scope', '$ionicLoading'];

    /* @ngInject */
    function InboxController($scope, $ionicLoading) {
        var notificationsInbox = [];
        var vm = angular.extend(this, {});

       /* $scope.loadMore = function () {
            //$scope.show($ionicLoading);
            daoService.fullInbox().then(function (req) {
                $scope.notificationsInbox = req.data;
                //$scope.$broadcast('scroll.infiniteScrollComplete');
                //$scope.hide($ionicLoading);
            }, function (err) {
                console.log(err);
                //$scope.hide($ionicLoading);
            });
        }*/
        $scope.show = function () {
            $ionicLoading.show({
                template: '<p>Loading...</p><ion-spinner></ion-spinner>'
            });
        };

        $scope.hide = function () {
            $ionicLoading.hide();
        };
        $scope.$on('wsMessage', function (event, informationBay) {
            notificationsInbox.push(informationBay);
            $scope.$apply(); //Apply changes to the page
        });

        $scope.$on('$stateChangeSuccess', function () {
            $scope.loadMore();
        });
    }
})();
