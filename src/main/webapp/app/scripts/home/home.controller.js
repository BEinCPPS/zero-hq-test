(function () {
    'use strict';

    angular
        .module('zerohqt.home')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['websocketService', '$scope', 'homeOrionService'];

    /* @ngInject */
    function HomeController(websocketService, $scope, homeOrionService) {
        $scope.entries = {};
        // $scope.isWsConnected = false;
        $scope.MAX_NR_BAYS = 4;

        $scope.$on('wsMessage', function (event, informationBay) {
            console.log(informationBay); // 'Broadcast!'
            aggregateData(informationBay);
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


        $scope.$on('$ionicView.loaded', function (viewInfo, state) {
            homeOrionService.subscribe().then(function (req) {
                console.log('Subscriptions created: ' + JSON.stringify(req.data));
            }, function (error) {
                console.log('Error creating subscriptions: ' + JSON.stringify(error));
            });
        });


        $scope.getBackgroundColor = function (stateType) {
            if (stateType === 'normal')  return 'button icon ion-android-checkmark-circle green-button';
            else if (stateType === 'warning') return 'button icon ion-android-warning yellow-button';
            else if (stateType === 'error') return 'button icon ion-android-alert red-button';

        }
        /*
         $scope.$on('$ionicView.loaded', function (viewInfo, state) {});

         $scope.$on('$ionicView.enter', function (viewInfo, state) {});

         $scope.$on('$ionicView.afterLeave', function (viewInfo, state) {});
         */

    }
})();
