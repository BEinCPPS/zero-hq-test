(function () {
    'use strict';

    angular
        .module('zerohqt.inbox')
        .controller('InboxController', InboxController);

    InboxController.$inject = ['$scope', '$ionicLoading', '$ionicPopup'];

    /* @ngInject */
    function InboxController($scope, $ionicLoading, $ionicPopup) {
        $scope.notificationsMap = {};
        $scope.shouldShowDelete = false;
        $scope.listCanSwipe = true;

        $scope.$on('wsMessage', function (event, informationBay) {
            if (typeof informationBay.acknowledge !== 'undefined' && informationBay.acknowledge !== null) {
                var acknowledge = informationBay.acknowledge;
                $scope.notificationsMap[acknowledge.id] = acknowledge;
                $scope.$apply(); //Apply changes to the page
            }
        });

        $scope.loadMore = function () {
        }

        $scope.$on('$ionicView.loaded', function (viewInfo, state) {
            $scope.isRemoveAllEnabled = false;
            var notifs = JSON.parse(localStorage.getItem('notifications'));
            if (notifs) {
                $scope.notificationsMap = notifs;
            }
        });

        $scope.$on('$ionicView.enter', function (viewInfo, state) {
            $scope.isRemoveAllEnabled = false;
        });

        $scope.deleteAcknowledge = function (id) {
            delete $scope.notificationsMap[id];
        }

        $scope.enableRemoveAll = function () {
            if(angular.equals($scope.notificationsMap, {})) return;
            $scope.isRemoveAllEnabled = true;
        }

        $scope.removeAll = function () {
            $scope.notificationsMap = {};
            localStorage.clear();
            $scope.isRemoveAllEnabled = false;
        }

        $scope.$on('$ionicView.afterLeave', function (viewInfo, state) {
            try {
                localStorage.clear();
                localStorage.setItem('notifications', JSON.stringify($scope.notificationsMap)); //TODO check Mb of data
            } catch (error) {
                console.log('Error encountered saving notifications in localStorage: ' + error);
            }
            console.log('Saved notifications!!!');

        });

    }
})();
