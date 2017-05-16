(function () {
    'use strict';

    angular
        .module('zerohqt.home')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$rootScope', 'imageService', '$ionicLoading'];

    /* @ngInject */
    function HomeController($scope, $rootScope, imageService, $ionicLoading) {
        $scope.entries = {};
        $scope.MAX_NR_BAYS = 4;
        $scope.isDataArrived = false;

        $scope.$on('wsMessage', function (event, informationBay) {
            console.log(informationBay); // 'Broadcast!'
            aggregateData(informationBay);
            $scope.isDataArrived = true;
            $scope.$apply(); //Apply changes to the page
        });

        function aggregateData(informationBay) {
            var stationName = informationBay.stationName;
            var bays = $scope.entries[stationName] ? $scope.entries[stationName] : [];
            initBaysArray(bays);
            var bayNumber = parseInt(informationBay.bayNumber);
            bayNumber--;
            bays[bayNumber] = informationBay; //Station bays numbers starts from 1
            $scope.entries[stationName] = bays;
        }

        function initBaysArray(bays) {
            for (var i = 0; i < $scope.MAX_NR_BAYS; i++) {
                if (bays[i]) continue;
                bays[i] = {};
            }
        }

        $scope.getBackgroundColor = function (stateType) {
            if (stateType === 'normal')  return 'icon ion-checkmark-circled green';
            else if (stateType === 'warning') return 'icon ion-android-warning orange';
            else if (stateType === 'error') return 'icon ion-android-alert red';

        }

        $scope.$on('$ionicView.loaded', function (viewInfo, state) {
            $scope.entries = {};
        });

        $scope.$on('logout', function (viewInfo, state) {
            $scope.entries = {};
            $scope.isDataArrived = false;
        });

        $scope.$on('$ionicView.enter', function (viewInfo, state) {
            if ($rootScope.informationBays) {
                angular.forEach($rootScope.informationBays, function (value) {
                    aggregateData(value);
                    $scope.$apply();
                })
            }
        });
        $scope.$on('$ionicView.afterLeave', function (viewInfo, state) {
            $rootScope.informationBays = [];
        });


    }
})();
