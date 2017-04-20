(function () {
    'use strict';

    angular
        .module('zerohqt.inbox')
        .controller('InboxController', InboxController);

    InboxController.$inject = ['$scope', '$ionicLoading'];

    /* @ngInject */
    function InboxController($scope, $ionicLoading) {
        var notificationsMap = new HashMap();
        var vm = angular.extend(this, {
            notifications: notificationsMap.values()
        });

        $scope.$on('wsMessage', function (event, informationBay) {
            if (typeof informationBay.acknowledge !== 'undefined' && informationBay.acknowledge !== null) {
                var acknowledge = informationBay.acknowledge;
                notificationsMap.set(acknowledge.id, acknowledge);
                $scope.$apply(); //Apply changes to the page
            }
        });

        $scope.loadMore = function () {
           // vm.notifications = vm.notifications.concat(notificationsMap);
           // $scope.$broadcast('scroll.infiniteScrollComplete');
        }

        $scope.$on('$ionicView.loaded', function (viewInfo, state) {
            var notifs = localStorage.getItem('notifications');
            if (notifs) {
                notificationsMap = notifs;
            }
        });

        /* $scope.$on('$ionicView.enter', function (viewInfo, state) {
         console.log(JSON.stringify(localStorage.getItem('notifications')));
         });
         */
        $scope.$on('$ionicView.afterLeave', function (viewInfo, state) {
            try {
                localStorage.setItem('notifications', notificationsMap);
            } catch (error) {
                console.log('Error eoncontered saving notifications in localstorage: ' + error);
            }
            console.log('Saved notifications!!!');

        });

    }
})();
